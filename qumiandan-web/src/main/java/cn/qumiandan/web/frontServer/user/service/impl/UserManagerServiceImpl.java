package cn.qumiandan.web.frontServer.user.service.impl;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.enums.LoginAgentType;
import cn.qumiandan.enums.LoginOrigin;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.system.ali.service.IAliPayService;
import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.shiro.WebLoginToken;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.system.weixin.entity.OALoginToken;
import cn.qumiandan.system.weixin.entity.WechatUserInfo;
import cn.qumiandan.system.weixin.service.WechatService;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserInfoVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.LogPrintUtils;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.web.frontServer.user.entity.request.UserMobileRegisterParams;
import cn.qumiandan.web.frontServer.user.entity.request.VerificationCodeParams;
import cn.qumiandan.web.frontServer.user.entity.response.AccountLoginResult;
import cn.qumiandan.web.frontServer.user.entity.response.AliPayAuthInfoResult;
import cn.qumiandan.web.frontServer.user.entity.response.LoginToken;
import cn.qumiandan.web.frontServer.user.entity.response.UserMobileRegisterResult;
import cn.qumiandan.web.frontServer.user.entity.response.UserRegisterResult;
import cn.qumiandan.web.frontServer.user.entity.response.VeriCodeLoginResult;
import cn.qumiandan.web.frontServer.user.entity.response.WechatLoginResult;
import cn.qumiandan.web.frontServer.user.service.IUserManagerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("loginServiceImpl")
public class UserManagerServiceImpl implements IUserManagerService {

	@Autowired
	private WechatService wechatService;
	
	@Reference()
	private IUserService userService;
	
	@Reference()
	private IVerifyCodeService verifyCodeService;
	
	@Autowired
	private IAliPayService aliPayService;
	
	@Reference
	private IShopUserService shopUserService;
	
	@Reference
	private ISalemanService salemanService;
	
	@Reference
	private ISysPropertiesService sysPropertiesService;
	
	@Reference
	private IRoleService roleService;
	
	
	/**
	 * 账号密码登陆
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result<AccountLoginResult> accountLogin(WebLoginToken token) {
		Result<AccountLoginResult> result = ResultUtils.error();
		UserVO user = userService.getUserByUsername(token.getUsername());
		if (null == user) {
			result.setMessage("用户不存在！");
			return result;
		}
		if (user != null && StringUtils.isBlank(user.getPassword())) {
			log.warn("user:" + user.getUserName() + "密码不存在！");
			result.setMessage("用户名或者密码错误！");
			return result;
		}
		if (!checkAccountLoginOrigin(user, token.getOrigin())) {
			result.setMessage("用户名或密码错误");
			return result;
		}
		token.setUser(user);
		result.setData(new AccountLoginResult(user));
		shiroLogin(token, result);
		return result;
	}
	
	/**
	 * 验证账号登录来源
	 * @param user
	 * @param origin
	 * @return
	 */
	private boolean checkAccountLoginOrigin(UserVO user, LoginOrigin origin) {
		// 验证各端登录
		switch (origin) {
		case Merchant:
			if (!shopUserService.memberExist(user.getId())) {
				return false;
			}
			break;
		case Samleman: {
			Boolean exist = salemanService.existSaleman(user.getId(), SalemanTypeEnums.Saleman.getCode());
			if (!exist) {
				return false;
			}
		}
			break;
		case Agent: {
			Boolean exist = salemanService.existSaleman(user.getId(), 
					SalemanTypeEnums.CityAgent.getCode(),
					SalemanTypeEnums.CountryAgent.getCode(),
					SalemanTypeEnums.ProAgent.getCode());
			if (!exist) {
				return false;
			}
		}
			break;
		case Background: {
			SysPropertiesVO properties = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.BackgroundRoleId.getId());
			if (Objects.nonNull(properties)) {
				List<RoleVO> roleList = roleService.getRoleListByUserId(user.getId());
				if (!CollectionUtils.isEmpty(roleList)) {
					if (!roleList.stream().filter(role -> Objects.nonNull(role)).map(role -> role.getId())
							.collect(Collectors.toSet()).contains(Long.parseLong(properties.getValue()))) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
			break;
		default:
			break;
		}
		return true;
	}
	
	/**
	 * 微信授权登录
	 * @param token
	 * @return
	 */
	@Override
	public Result<WechatLoginResult> wechatLogin(WebLoginToken token){
		Result<WechatLoginResult> result = ResultUtils.error();
		if (!checkWechatLoginOrigin(token.getOrigin())) {
			result.setMessage("不支持微信登录");
			return result;
		}
		//1.通过code请求微信接口获取access_token
		OALoginToken oaToken = wechatService.getAccessTokenByCode(token.getCode());
		if (oaToken != null && StringUtils.isBlank(oaToken.getErrcode())) {
			//2.通过access_token请求微信接口获取userInfo
			WechatUserInfo userInfo = wechatService.getWeiXinUserInfo(oaToken);
			if (userInfo != null && StringUtils.isBlank(userInfo.getErrcode())) {
				WechatLoginResult lr = new WechatLoginResult();
				lr.setWechatUserInfo(userInfo);
				// 3.判断这个用户是否存在
				UserVO user = userService.getUserByUnionidOrOpenId(null, userInfo.getOpenid());
				if (user != null && user.getId() != null) {
					// 用户注册过
					token.setUsername(user.getUserName());
					token.setUser(user);
					// loginResult.setUserVO(user);
					result.setData(new WechatLoginResult(user));
					// 执行登录
					shiroLogin(token, result);
				} else {
					// 返回微信的所有信息到前端
					result.setData(lr);
					result.getData().setToken("");
					result.setResult(GwErrorCode.GW0001);
				}
			} else {
				log.error(LogPrintUtils.getLogPrint(GwErrorCode.GW8002, userInfo.toString()));
			}
		} else {
			log.error(LogPrintUtils.getLogPrint(GwErrorCode.GW8001, oaToken.toString()));
			result.setMessage("微信授权错误");
		}
		return result;
	}
	
	
	/**
	 * 验证微信登录账号登录来源
	 * @param user
	 * @param origin
	 * @return
	 */
	private boolean checkWechatLoginOrigin(LoginOrigin origin) {
		// 验证各端登录
		switch (origin) {
			case User:
				return true;
			default:
				return false;
		}
	}
	
	
	@Override
	public Result<VeriCodeLoginResult> verificationCodeLogin(WebLoginToken token, VerificationCodeParams params)  {
		Result<VeriCodeLoginResult> result = ResultUtils.error();
		// 效验验证码
		verifyCodeService.validationVerifyCodeAndMobile(token.getUsername(), token.getCode());
		UserVO user = userService.getUserByUsername(token.getUsername());
		// 用户不能存在的时候走注册.
		if (Objects.isNull(user)) {
			if (!LoginOrigin.User.getCode().equals(token.getOrigin().getCode())) {
				throw new QmdException("用户名不存在");
			}
			UserAddVO userVo = CopyBeanUtil.copyBean(params, UserAddVO.class);
			userVo.setUserName(token.getUsername());
			Result<UserRegisterResult> userRegister = loginOrRegister(userVo);
			UserRegisterResult registerResult = userRegister.getData();
			VeriCodeLoginResult data = CopyBeanUtil.copyBean(registerResult, VeriCodeLoginResult.class);
			result = ResultUtils.success(GwErrorCode.GW0001);
			result.setData(data);
		} else {
			if (!checkVerificationCodeLoginOrigin(user, token.getOrigin())) {
				result.setMessage("用户名或密码错误");
				return result;
			}
			token.setUser(user);
			result.setData(new VeriCodeLoginResult(user));
			shiroLogin(token, result);
		}
		return result;
	}
	
	
	/**
	 * 验证验证码来源登录
	 * @param user
	 * @param origin
	 * @return
	 */
	private boolean checkVerificationCodeLoginOrigin(UserVO user, LoginOrigin origin) {
		// 验证各端登录
		switch (origin) {
		case Merchant: {
			if (!shopUserService.memberExist(user.getId())) {
				return false;
			}
		}
			break;
		case Samleman: {
			Boolean exist = salemanService.existSaleman(user.getId(), SalemanTypeEnums.Saleman.getCode());
			if (!exist) {
				return false;
			}
		}
			break;
		case Agent: {
			return false;
		}
		case Background: {
			return false;
		}
		default:
			break;
		}
		return true;
	}

	@Override
	public Result<UserMobileRegisterResult> userBindMobileRegister(UserAddVO user, UserMobileRegisterParams params)  {
		Result<UserMobileRegisterResult> result = ResultUtils.error(GwErrorCode.GW0001);
		// 1.效验验证码
		verifyCodeService.validationVerifyCodeAndMobile(params.getMobile(),params.getCode());
		// 2.添加用户
		UserAddVO addUser = userService.addUser(user);
		if (addUser != null && addUser.getId() != null) {
			WebLoginToken token = new WebLoginToken();
			UserVO userVO = new UserVO();
			try {
				BeanUtils.copyProperties(userVO, addUser);
			} catch (Exception e) {
				log.error("",e);
			}
			token.setUser(userVO);
			token.setUsername(user.getUserName());
			token.setAgentType(LoginAgentType.VERIFICATIONCODE);
			token.setOrigin(LoginOrigin.User);
			// 3.执行登录
			result.setData(new UserMobileRegisterResult(addUser));
			shiroLogin(token, result);
		}
		return result;
	}

	@Override
	public Result<UserRegisterResult> userRegisterByPwd(UserAddVO user)  {
		Result<UserRegisterResult> result = ResultUtils.error();
		// 2.添加用户
		UserAddVO addUser = userService.addUser(user);
		if (addUser != null && addUser.getId() != null) {
			WebLoginToken token = new WebLoginToken();
			token.setUsername(user.getUserName());
			if (StringUtils.isNotBlank(user.getPassword())) {
				token.setPassword(user.getPassword().toCharArray());
			}
			token.setAgentType(LoginAgentType.ACCOUNT);
			// 3.执行登录
			result.setData(new UserRegisterResult(addUser));
			shiroLogin(token, result);
		}
		return result;
	}

	/**
	 * shiro执行登录
	 * @param token
	 * @param result
	 * @throws Exception
	 */
	private <T extends LoginToken> void shiroLogin(WebLoginToken token, Result<T> result)  {
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			if (subject.isAuthenticated()) {
				String tokenId = subject.getSession().getId().toString();
				result.getData().setToken(tokenId);
				result.setResult(GwErrorCode.GW0001);
				
				// 更新用户token
				UserInfoVO vo = new UserInfoVO();
				vo.setUserId(token.getUser().getId());
				vo.setToken(tokenId);
				userService.updateUserInfo(vo);
			} else {
				result.setMessage("登录异常");
				log.info(LogPrintUtils.getLogPrint(GwErrorCode.GW9001, "用户" + token.getUsername()));
			}
		} catch (IncorrectCredentialsException | UnknownAccountException e) {
			log.info(e.toString());
			throw new QmdException("该用户不存在或账号密码错误");
		} catch (LockedAccountException e) {
			log.info(e.toString());
			throw new QmdException("此账号已被冻结,无法使用");
		} catch (Exception e) {
			log.info(e.toString());
			throw new QmdException(GwErrorCode.GW9999);
		}
	}

	
	/**
	 * 绑定微信
	 * @param Code
	 * @return
	 */
	@Override
	public Result<UserVO> bindWeChat(String Code) {
		OALoginToken tokenResult = wechatService.getAccessTokenByCode(Code);
		if(tokenResult==null) {
			log.info("请求微信access_tocken失败");
			throw new QmdException("请求微信access_tocken失败");
		}
		WechatUserInfo userInfo = wechatService.getWeiXinUserInfo(tokenResult);
		if(userInfo==null) {
			log.info("请求微信用户数据失败");
			throw new QmdException("请求微信用户数据失败");
		}
		UserAddVO user = new UserAddVO();
		user.setId(ShiroUtils.getUserId());
		user.setOpenId(userInfo.getOpenid());
		user.setUnionId(userInfo.getUnionid());
		user.setSex(StringUtils.isNotBlank(userInfo.getSex()) ? Integer.parseInt(userInfo.getSex()) : null);
		if (StringUtils.isNotBlank(userInfo.getNickname())) {
			user.setNickName(userInfo.getNickname());
		}
		if (StringUtils.isNotBlank(userInfo.getHeadimgurl())) {
			user.setPortrait(userInfo.getHeadimgurl());
		}
		userService.updateUserInfoByUserId(user);
		UserVO userVO = userService.getUserByUnionidOrOpenId(userInfo.getUnionid(),userInfo.getOpenid());
		Result<UserVO> result =  ResultUtils.success("绑定成功");
		result.setData(userVO);
		return result;
	}

	@Override
	public Result<AliPayAuthInfoResult> getAliPayAuthInfo(String authCode) {
		AssertUtil.isNull(authCode, "UserManagerServiceImpl|getAliPayAuthInfo|传入参数authCode为空");
		AlipaySystemOauthTokenResponse authInfo = aliPayService.getAuthInfo(authCode);
		if (Objects.nonNull(authInfo) && StringUtils.isNotBlank(authInfo.getUserId())) {
			/*// 更新用户信息
			UserVO user = userService.getUserById(ShiroUtils.getUserId());
			if (Objects.nonNull(user)) {
				user.setAlipayId(authInfo.getUserId());
				UpdateUserVO vo = CopyBeanUtil.copyBean(user, UpdateUserVO.class);
				vo.setUpdateDate(new Date());
				vo.setUpdateId(user.getId());
				userService.updateUser(vo);
			}*/
			return ResultUtils.success(CopyBeanUtil.copyBean(authInfo, AliPayAuthInfoResult.class));
		}
		return ResultUtils.error(GwErrorCode.GW1000, "支付宝授权信息获取失败");
	}

	@Override
	public Result<UserRegisterResult> loginOrRegister(UserAddVO user) {
		Result<UserRegisterResult> result = ResultUtils.error();
		// 2.添加用户
		UserAddVO addUser = userService.addUser(user);
		if (addUser != null && addUser.getId() != null) {
			WebLoginToken token = new WebLoginToken();
			UserVO newUser = CopyBeanUtil.copyBean(addUser, UserVO.class);
			token.setUser(newUser);
			token.setUsername(user.getUserName());
			if (StringUtils.isNotBlank(user.getPassword())) {
				token.setPassword(user.getPassword().toCharArray());
			}
			token.setAgentType(LoginAgentType.VERIFICATIONCODE);
			token.setOrigin(LoginOrigin.User);
			
			// 3.执行登录
			result = ResultUtils.success();
			result.setData(new UserRegisterResult(addUser));
			shiroLogin(token, result);
		}
		return result;
	}

}
