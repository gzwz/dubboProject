package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 获取阿里支付授权信息
 * @author yuleidian
 * @version 创建时间：2018年12月25日 上午10:23:07
 */
@Data
public class AliPayAuthInfoParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "授权code不能为空")
	private String authCode;
	
}
