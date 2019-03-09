package cn.qumiandan.web.frontServer.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.enums.LoginAgentType;
import cn.qumiandan.system.shiro.WebLoginToken;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.web.frontServer.user.entity.request.AccountUserParams;
import cn.qumiandan.web.frontServer.user.entity.request.VerificationCodeParams;
import cn.qumiandan.web.frontServer.user.entity.request.WechatCodeParams;
import cn.qumiandan.web.frontServer.user.entity.response.AccountLoginResult;
import cn.qumiandan.web.frontServer.user.entity.response.VeriCodeLoginResult;
import cn.qumiandan.web.frontServer.user.entity.response.WechatLoginResult;
import cn.qumiandan.web.frontServer.user.service.IUserManagerService;

/**
 * 所有端登录处理接口
 * @author yuleidian
 * @version 创建时间：2018年11月2日 下午2:28:05
 */
@RestController
@RequestMapping("/login/")
public class LoginController {

	@Autowired
	private IUserManagerService userManagerService;
	
	@Reference
	private IVerifyCodeService verifyCodeService;
	
	/**
	 * 账号密码登陆
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("accountLogin")
 	public Result<AccountLoginResult> accountLogin(@Valid @RequestBody(required = false) AccountUserParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if (params == null) {
			return ResultUtils.error("用户数据为空");
		}
		WebLoginToken token = new WebLoginToken();
		token.setUsername(params.getUsername());
		token.setPassword(params.getPassword().toCharArray());
		token.setAgentType(LoginAgentType.ACCOUNT);
		token.setOrigin(params.getOrigin());
		return userManagerService.accountLogin(token);
	}
	
	/**
	 * 微信端登陆
	 * @param code
	 * @return
	 */
	@RequestMapping("wechatLogin")
	public Result<WechatLoginResult> wechatLogin(@RequestBody(required = false) WechatCodeParams params) throws Exception {
		if (StringUtils.isBlank(params.getCode())) {
			return ResultUtils.paramsError("参数错误, 微信code必须提交");
		}
		WebLoginToken token = new WebLoginToken();
		token.setCode(params.getCode());
		token.setAgentType(LoginAgentType.WECHAT);
		token.setOrigin(params.getOrigin());
		return userManagerService.wechatLogin(token);
	}
	
	/**
	 * 手机验证码登录
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("verificationCodeLogin")
	public Result<VeriCodeLoginResult> verificationCodeLogin(@RequestBody(required = false)
	@Valid VerificationCodeParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		boolean flag = false;
		flag = verifyCodeService.validationVerifyCodeAndMobile(params.getMobile(), params.getCode());
		if (!flag) {
			return ResultUtils.error("未验证通过！");
		}
		WebLoginToken token = new WebLoginToken();
		token.setUsername(params.getMobile());
		token.setAgentType(LoginAgentType.VERIFICATIONCODE);
		token.setCode(params.getCode());
		token.setOrigin(params.getOrigin());
		return userManagerService.verificationCodeLogin(token,params);
	}
	
	/**
	 * 普通用户登录入口
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("consumer")
	public Result<LoginResult> consumer(WebLoginToken token, String agentType) throws Exception {
		OALoginToken accToken = weiXinService.getAccessTokenByCode(token.getCode());
		WechatUserInfo userInfo = weiXinService.getWeiXinUserInfo(accToken);
		System.out.println(accToken);
		System.out.println(userInfo);
		if (token.getAgentType() == null) {
			return ResultUtils.error(ResultStatusCode.REQUEST_PARAMS_ERROR);
		}
		if (token.getAgentType() == LoginAgentType.WECHAT) {
			if (StringUtils.isBlank(token.getCode())) {
				return ResultUtils.error(ResultStatusCode.REQUEST_PARAMS_ERROR);
			}
		}
		Result<LoginResult> result = LoginService.login(token);
        return result;
	}*/
	
	
	/**
	 * 管理员,店长等端登录
	 * @return
	 */
	@ResponseBody
	@RequestMapping("manager")
	public Result<Void> shopManager() {
		List<String> s = Lists.newArrayListWithCapacity(0);
		s.forEach(e -> {
			System.out.println(e.toString());
		});
		return null;
	}
	
	/**
	 * 后台管理人员登录
	 * @return
	 */
	public Result<Void> bgManager() {
		return null;
	}
}
