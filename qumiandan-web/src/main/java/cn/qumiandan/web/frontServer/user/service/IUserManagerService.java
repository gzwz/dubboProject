package cn.qumiandan.web.frontServer.user.service;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.system.shiro.WebLoginToken;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.web.frontServer.user.entity.request.UserMobileRegisterParams;
import cn.qumiandan.web.frontServer.user.entity.request.VerificationCodeParams;
import cn.qumiandan.web.frontServer.user.entity.response.AccountLoginResult;
import cn.qumiandan.web.frontServer.user.entity.response.AliPayAuthInfoResult;
import cn.qumiandan.web.frontServer.user.entity.response.UserMobileRegisterResult;
import cn.qumiandan.web.frontServer.user.entity.response.UserRegisterResult;
import cn.qumiandan.web.frontServer.user.entity.response.VeriCodeLoginResult;
import cn.qumiandan.web.frontServer.user.entity.response.WechatLoginResult;

/**
 * 用户登录service
 * @author yuleidian
 * @version 创建时间：2018年11月5日 下午1:58:21
 */
public interface IUserManagerService {

	/**
	 * 只允许 用户端微信登录
	 * 微信授权登陆
	 * @return
	 */
	public Result<WechatLoginResult> wechatLogin(WebLoginToken token);
	
	/**
	 * 账号密码登陆
	 * @return
	 * @throws Exception
	 */
	public Result<AccountLoginResult> accountLogin(WebLoginToken token);
	
	/**
	 * 手机验证码登录
	 * @param token
	 * @param params 
	 * @return
	 * @throws Exception
	 */
	public Result<VeriCodeLoginResult> verificationCodeLogin(WebLoginToken token, VerificationCodeParams params);
	
	/**
	 * 手机绑定注册
	 * @param params 
	 * @return
	 * @throws Exception
	 */
	public Result<UserMobileRegisterResult> userBindMobileRegister(UserAddVO user, UserMobileRegisterParams params);
	
	
	/**
	 * 手机验证码登陆没有用户并就绑定注册
	 * 如果有微信信息，就绑定
	 * @param params 
	 * @return
	 * @throws Exception
	 */
	public Result<UserRegisterResult> loginOrRegister(UserAddVO user);
	
	/**
	 * 账号密码注册
	 * @return
	 * @throws Exception
	 */
	public Result<UserRegisterResult> userRegisterByPwd(UserAddVO user);
	
	/**
	 * 绑定微信
	 * @param Code
	 * @return
	 */
	public Result<UserVO> bindWeChat(String Code);
	
	/**
	 * 获取阿里授权信息
	 * @param authCode
	 * @return
	 */
	public Result<AliPayAuthInfoResult> getAliPayAuthInfo(String authCode);
	
}
