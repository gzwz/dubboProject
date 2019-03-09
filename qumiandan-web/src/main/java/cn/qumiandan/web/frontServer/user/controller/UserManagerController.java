package cn.qumiandan.web.frontServer.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.BackStageQueryUserParamsVO;
import cn.qumiandan.user.vo.SetPasswordVO;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.web.frontServer.user.entity.request.AliPayAuthInfoParams;
import cn.qumiandan.web.frontServer.user.entity.request.BackStageQueryUserParams;
import cn.qumiandan.web.frontServer.user.entity.request.GetCodeParams;
import cn.qumiandan.web.frontServer.user.entity.request.GetUserNameParams;
import cn.qumiandan.web.frontServer.user.entity.request.MobileParams;
import cn.qumiandan.web.frontServer.user.entity.request.SetPasswordParams;
import cn.qumiandan.web.frontServer.user.entity.request.UpdateUserInfoParams;
import cn.qumiandan.web.frontServer.user.entity.request.UserMobileRegisterParams;
import cn.qumiandan.web.frontServer.user.entity.request.UserRegisterParams;
import cn.qumiandan.web.frontServer.user.entity.response.AliPayAuthInfoResult;
import cn.qumiandan.web.frontServer.user.entity.response.UserInfo;
import cn.qumiandan.web.frontServer.user.entity.response.UserMobileRegisterResult;
import cn.qumiandan.web.frontServer.user.service.IUserManagerService;

/**
 * 用户管理
 * @author yuleidian
 * @version 创建时间：2018年11月7日 下午3:26:09
 */
@RestController
@RequestMapping("/userManager/")
public class UserManagerController {

	@Autowired
	private IUserManagerService userManagerService;
	
	@Reference
	private IVerifyCodeService verifyCodeService;
	
	@Reference
	private IUserService userService;
	
	@Reference
	private IRoleService roleService;
	/**
	 * 用户账号注册接口
	 * @throws Exception
	 */
	@RequestMapping("userRegister")
	public Result<Void> userRegister(@Valid @RequestBody(required = false) UserRegisterParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UserAddVO user = new UserAddVO();
		BeanUtils.copyProperties(user, params);
		user.setUserName(user.getMobile());
		user.setPortrait(params.getPortrait());
		userService.addUser(user);
		return ResultUtils.success("注册成功");
	}
	
	/**
	 * 用户手机绑定注册
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userBindMobileRegister")
	public Result<UserMobileRegisterResult> userBindMobileRegister(@Valid @RequestBody(required = false) 
	UserMobileRegisterParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UserAddVO user = new UserAddVO();
		BeanUtils.copyProperties(user, params);
		user.setUserName(user.getMobile());
		user.setPortrait(params.getPortrait());
		return userManagerService.userBindMobileRegister(user,params);
	}
	
	
	/**
	 * 根据用户名获取角色信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRoleByUserName")
	public Result<List<RoleVO>> getRoleByUserName(@Valid @RequestBody(required = false) 
	GetUserNameParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<RoleVO> roleList=roleService.getRoleByUserName(params.getUserName());
		Result<List<RoleVO>> result=ResultUtils.success();
		result.setData(roleList);
		return result;
	}

	/**
	 * 校验用户手机号是否在平台注册
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("checkUserExists")
	public Result<UserVO> checkUserExists(@Valid @RequestBody(required = false) MobileParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UserVO userVO = userService.getUserByUsername(params.getMobile());
		if(userVO == null || userVO.getId() == null) {
			return ResultUtils.error("用户账号不存在");
		}
		return ResultUtils.success();

	}
	
	/**
	 * 更新用户信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateUserInfoByUserId")
	public Result<List<RoleVO>> updateUserInfoByUserId(@Valid @RequestBody(required = false) UpdateUserInfoParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UserAddVO userAddVO =
		CopyBeanUtil.copyBean(params, UserAddVO.class);
		userService.updateUserInfoByUserId(userAddVO);
		return ResultUtils.success("更新用户信息成功");
	}

	/**
	 * 设置密码
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setPassword")
	public Result<Void> setPassword(@Valid @RequestBody(required = false) SetPasswordParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		boolean flag = verifyCodeService.validationVerifyCodeAndMobile(params.getUserName(),params.getCode());
		if(!flag) {
			return ResultUtils.error("短信验证码错误");
		}
		SetPasswordVO setPasswordVO =
		CopyBeanUtil.copyBean(params, SetPasswordVO.class);
		userService.resetPassword(setPasswordVO);
		return ResultUtils.success("设置密码成功");
	}
	
	/**
	 * 忘记密码重置密码
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resetPassword")
	public Result<Void> resetPassword(@Valid @RequestBody(required = false) SetPasswordParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		boolean flag = verifyCodeService.validationVerifyCodeAndMobile(params.getUserName(),params.getCode());
		if(!flag) {
			return ResultUtils.error("短信验证码错误");
		}
		SetPasswordVO setPasswordVO =
		CopyBeanUtil.copyBean(params, SetPasswordVO.class);
		userService.resetPassword(setPasswordVO);
		return ResultUtils.success("设置密码成功");
	}

	/**
	 * 绑定微信
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("bindWeChat")
	public Result<UserVO> bindWeChat(@Valid @RequestBody(required = false) GetCodeParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		return userManagerService.bindWeChat(params.getCode());
	}
	
	/**
	 * 获取支付授权信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAuthInfo")
	public Result<AliPayAuthInfoResult> getAuthInfo(@RequestBody(required = false) AliPayAuthInfoParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		return userManagerService.getAliPayAuthInfo(params.getAuthCode());
	}
	
	/**
	 * 查询当前登录的用户信息
	 * @return
	 */
	@RequestMapping("getUserInfo")
	public Result<UserInfo> getUserInfo(){
		Long userId = ShiroUtils.getUserId();
		UserVO userVO = userService.getUserById(userId);
		UserInfo bean = CopyBeanUtil.copyBean(userVO, UserInfo.class);
		//判断该用户是否有密码，1是，0否
		if(userVO.getPassword() == null) {
			bean.setHasPassword(StatusEnum.FALSE.getCode());
		}else {
			bean.setHasPassword(StatusEnum.TRUE.getCode());
		}
		return ResultUtils.success(bean);
	}
	
	/**
	 * 总后台查询用户
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("backStageQueryUser")
	public Result<PageInfo<UserVO>> backStageQueryUser(@RequestBody(required = false) BackStageQueryUserParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		BackStageQueryUserParamsVO paramsVO = CopyBeanUtil.copyBean(params, BackStageQueryUserParamsVO.class);
		PageInfo<UserVO> backStageQueryUser = userService.backStageQueryUser(paramsVO);
		return ResultUtils.success(backStageQueryUser);
	}
}
