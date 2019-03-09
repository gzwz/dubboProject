package cn.qumiandan.system.ali.service;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;

/**
 * 阿里支付
 * @author yuleidian
 * @version 创建时间：2018年12月24日 下午9:07:33
 */
public interface IAliPayService {


	/**
	 * 获取授权信息
	 * @param authCode
	 */
	AlipaySystemOauthTokenResponse getAuthInfo(String authCode);
	
}
