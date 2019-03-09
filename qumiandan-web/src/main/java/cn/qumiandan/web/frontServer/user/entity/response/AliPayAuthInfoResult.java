package cn.qumiandan.web.frontServer.user.entity.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 支付宝授权返回结构
 * @author yuleidian
 * @version 创建时间：2018年12月25日 上午11:13:42
 */
@Data
public class AliPayAuthInfoResult implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 支付宝token
	 */
	private String accessToken;
	
	/**
	 * 
	 */
	private String alipayUserId;

	private String authTokenType;

	private String expiresIn;

	private String reExpiresIn;

	private String refreshToken;

	private String userId;
}
