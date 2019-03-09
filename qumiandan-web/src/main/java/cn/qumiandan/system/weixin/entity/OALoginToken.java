package cn.qumiandan.system.weixin.entity;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信认证登录token实体
 * @author yuleidian
 * @version 创建时间：2018年11月3日 下午7:20:29
 * 
 * 微信字段和系统字段不统一，请使用set copy 内容
 */
@Getter
@Setter
public class OALoginToken extends WechatBase {

	@Expose
	private String access_token;				// 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	@Expose
	private Long expires_in;					// access_token接口调用凭证超时时间，单位（秒）
	@Expose
	private String refresh_token;				// 用户刷新access_token
	@Expose
	private String openid;						// 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
	@Expose
	private String scope;						// 用户授权的作用域，使用逗号（,）分隔
	
	public OALoginToken() {
		
	}

	@Override
	public String toString() {
		return "WechatBase [errcode=" + super.getErrcode() + ", errmsg=" + super.getErrmsg() + "] + OALoginToken [access_token=" + access_token + ", expires_in=" + expires_in + ", refresh_token="
				+ refresh_token + ", openid=" + openid + ", scope=" + scope + "]";
	}
}
