package cn.qumiandan.system.weixin.entity;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;
/**
 * 微信返回信息基础
 * @author yuleidian
 * @version 创建时间：2018年11月6日 上午10:20:34
 */
@Getter
@Setter
public class WechatBase {

	@Expose
	private String errcode;			// 微信返回的错误信息代码
	@Expose
	private String errmsg;			// 微信返回错误信息

	@Override
	public String toString() {
		return "WechatBase [errcode=" + errcode + ", errmsg=" + errmsg + "]";
	}
	
}
