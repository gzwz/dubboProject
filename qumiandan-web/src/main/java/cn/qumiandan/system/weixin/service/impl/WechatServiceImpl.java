package cn.qumiandan.system.weixin.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;

import cn.qumiandan.config.WechatConfig;
import cn.qumiandan.system.weixin.entity.OALoginToken;
import cn.qumiandan.system.weixin.entity.WechatUserInfo;
import cn.qumiandan.system.weixin.service.WechatService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信请求实现
 * @author yuleidian
 * @version 创建时间：2018年11月3日 下午7:18:09
 */
@Slf4j
@Service("wechatServiceImpl")
public class WechatServiceImpl implements WechatService {
	
	@Autowired
	@Qualifier(value = "restTemplate")
	private RestTemplate restTemplate;
	
	@Autowired
	private WechatConfig wechatConfig;
	
	@Override
	public OALoginToken getAccessTokenByCode(String code) {
		Map<String, String> condition = Maps.newHashMap();
		condition.put("code", code);
		condition.put("appId", wechatConfig.getAppId());
		condition.put("appSecret", wechatConfig.getAppSecret());
		OALoginToken tokenResult = restTemplate.getForObject(wechatConfig.getLoginCodeUrl(), OALoginToken.class, condition);
		log.info("请求微信access_tocken 返回数据{}",tokenResult.toString());
		return tokenResult;
	}

	@Override
	public WechatUserInfo getWeiXinUserInfo(OALoginToken token) {
		Map<String, String> condition = Maps.newHashMap();
		condition.put("accessToken", token.getAccess_token());
		condition.put("openid", token.getOpenid());
		WechatUserInfo userInfo = restTemplate.getForObject(wechatConfig.getAcGetUserInfoUrl(), WechatUserInfo.class, condition);
		log.info("请求微信用户数据 返回数据：{}",userInfo.toString());
		return userInfo;
	}
}
