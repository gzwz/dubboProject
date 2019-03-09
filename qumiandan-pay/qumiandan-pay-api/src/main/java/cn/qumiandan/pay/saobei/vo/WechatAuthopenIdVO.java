package cn.qumiandan.pay.saobei.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 微信公众号JSAPI支付授权
 * @author yuleidian
 * @version 创建时间：2018年12月12日 上午9:46:05
 */
@Data
public class WechatAuthopenIdVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商户号*//*
	private String merchantNo;
	
	*//** 设备id*//*
	private String terminalNo;
	
	*//** 令牌*//*
	private String accessToken;*/
	
	/** 跳转地址*/
	private String redirectUri;
}
