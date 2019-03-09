package cn.qumiandan.enums;

import cn.qumiandan.common.interfaces.IStringEnum;

/**
 * 用户登录请求来源
 * @author yuleidian
 * @version 创建时间：2018年11月5日 下午2:03:19
 */
public enum LoginAgentType implements IStringEnum {
	
	/** 微信授权登录*/
	WECHAT("1", "微信授权登录"),
	/** 账号密码登陆*/
	ACCOUNT("2", "账号密码登陆"),
	/** 手机验证码登录*/
	VERIFICATIONCODE("3", "手机验证码登录"),
	;
	private String code;
	
	private String name;
	
	LoginAgentType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
