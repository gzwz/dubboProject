package cn.qumiandan.system.ali.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.system.ali.config.AliPayConfig;
import cn.qumiandan.system.ali.service.IAliPayService;

@Service("aliPayServiceImpl")
public class AliPayServiceImpl implements IAliPayService {

	@Resource
	private AliPayConfig aliPayConfig;
	
	@Override
	public AlipaySystemOauthTokenResponse getAuthInfo(String authCode) {
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getGetway(), aliPayConfig.getAppId(), aliPayConfig.getAppPrivate(), "json", "UTF-8", aliPayConfig.getAlipayPublic(), "RSA2"); 
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(authCode);
		request.setGrantType("authorization_code");
		try {
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
		    return oauthTokenResponse;
		} catch (AlipayApiException e) {
			throw new QmdException("调用阿里支付接口失败");
		}
	}
}
