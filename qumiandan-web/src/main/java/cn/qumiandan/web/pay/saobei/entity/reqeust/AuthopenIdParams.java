package cn.qumiandan.web.pay.saobei.entity.reqeust;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 微信公众号JSAPI支付授权请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月18日 下午5:08:07
 */
@Data
public class AuthopenIdParams implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 跳转地址*/
	@NotBlank(message = "回调地址不能为空")
	private String redirectUri;
}
