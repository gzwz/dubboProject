package cn.qumiandan.system.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import cn.qumiandan.enums.LoginAgentType;
import cn.qumiandan.enums.LoginOrigin;
import cn.qumiandan.user.vo.UserVO;

/**
 * 自定义登录token
 * 
 * 因为有面密码登录请求
 * @author yuleidian
 * @version 创建时间：2018年11月5日 下午1:50:19
 */
public class WebLoginToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 149361560473126140L;
	
	private String code;						// 微信授权登录会携带用户授权code
	
	private LoginAgentType agentType;			// 请求登录类型

	private UserVO user;						// 用户
	
	private LoginOrigin origin;					// 请求登录来源
	
	
	/** 微信授权登录会携带用户授权code*/
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 请求登录类型 1.微信授权登录 2.其他登录方式
	 */
	public LoginAgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(LoginAgentType agentType) {
		this.agentType = agentType;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public LoginOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(LoginOrigin origin) {
		this.origin = origin;
	}
}
