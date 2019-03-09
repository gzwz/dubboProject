package cn.qumiandan.web.wechat.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.config.WechatConfig;
import cn.qumiandan.httprequest.api.IHttpRequestService;
import cn.qumiandan.utils.DateUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.Sha1Util;
import cn.qumiandan.web.wechat.entity.request.WeChatSignatureParams;
import lombok.Data;

@RestController
@RequestMapping("/wechat/")
public class WechatController {
	
	@Autowired
	private WechatConfig wechatConfig;
	
	@Reference
	private IHttpRequestService http;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private String wechat_access_token = "wechat_access_token";
	private String jsapi_ticket  = "jsapi_ticket";
	
	@RequestMapping("getWechtAccessToken")
	public Result<WeChatSignatureParams> getWechtAccessToken(@Valid @RequestBody(required = false) 
	WeChatSignatureParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		// 获取access_token
		WechatAccessTokenAndTicket accessToken = cacheAccessTokenToRedis();
		// 获取jsapi_ticket
		WechatAccessTokenAndTicket ticket = cacheTicketToRedis(accessToken.getAccess_token());
		long timeMillis = Long.valueOf(String.valueOf(System.currentTimeMillis()).substring(0, 10));
		// 签名
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		String signature = signature(ticket.getTicket(), params.getUrl(),timeMillis,nonceStr);
		Result<WeChatSignatureParams> result = ResultUtils.success();
		
		WeChatSignatureParams data = new WeChatSignatureParams();
		data.setAppId(wechatConfig.getAppId());
		data.setSignatue(signature);
		data.setTimestamp(timeMillis);
		data.setUrl(params.getUrl());
		data.setNonceStr(nonceStr);
		result.setData(data);
		return result;
	}
	
	private String signature(String ticket,String url,Long time,String nonceStr) {
		WechatSignature signature = new WechatSignature();
		signature.setJsapi_ticket(ticket);
		signature.setNoncestr(nonceStr);
		signature.setTimestamp(time);
		signature.setUrl(url);
		Map<String, Object> map = signature.sort();
		StringBuilder builder = new StringBuilder();
		map.forEach((key, value)->{
			builder.append(key+"="+value+"&");
		});
		builder.deleteCharAt(builder.length()-1);
		String signatureStr = Sha1Util.signature(builder.toString());
		return signatureStr;
	}
	
	/**
	 * 缓存 access_token 到redis 7200
	 * @return
	 */
	private WechatAccessTokenAndTicket cacheTicketToRedis(String access_token) {
		BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(jsapi_ticket);
		String val = valueOps.get();
		WechatAccessTokenAndTicket accessToken = null;
		if (null == val) { // 设置改值
			accessToken = getTicket(access_token);
			valueOps.set(new Gson().toJson(accessToken));
		}else {
			accessToken = new Gson().fromJson(val, WechatAccessTokenAndTicket.class);
			// 过期时间小于10分钟，则重新获取
			if ((accessToken.getThenDate().getTime() - System.currentTimeMillis()) < 600000 ) {
				accessToken = getTicket(access_token);
				valueOps.set(new Gson().toJson(accessToken));
			}
		}
		return accessToken;
	}
	
	/**
	 * 缓存 access_token 到redis 7200
	 * @return
	 */
	private WechatAccessTokenAndTicket cacheAccessTokenToRedis() {
		BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(wechat_access_token);
		String val = valueOps.get();
		WechatAccessTokenAndTicket accessToken = null;
		if (null == val) { // 设置改值
			accessToken = getAccessToken();
			valueOps.set(new Gson().toJson(accessToken));
		}else {
			accessToken = new Gson().fromJson(val, WechatAccessTokenAndTicket.class);
			// 过期时间小于10分钟，则重新获取
			if ((accessToken.getThenDate().getTime() - System.currentTimeMillis()) < 600000 ) {
				accessToken = getAccessToken();
				valueOps.set(new Gson().toJson(accessToken));
			}
		}
		return accessToken;
	}
	
	// http请求 access_token
	private WechatAccessTokenAndTicket getAccessToken() {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("grant_type", "client_credential");
		param.put("appid", wechatConfig.getAppId());
		param.put("secret", wechatConfig.getAppSecret());
		String json = http.doGetForJson(wechatConfig.getAccessTokenUrl() , param );
		WechatAccessTokenAndTicket fromJson = new Gson().fromJson(json, WechatAccessTokenAndTicket.class);
		if (fromJson.getErrcode() != 0) {
			throw new QmdException("微信获取AccessToken 错误"+ fromJson);
		}
		fromJson.setThenDate(DateUtil.addSecond(new Date(), fromJson.getExpires_in()));
		return fromJson;
	}
	
	// http请求 tiken
	private WechatAccessTokenAndTicket getTicket(String access_token) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", access_token);
		param.put("type", "jsapi");
		String json = http.doGetForJson(wechatConfig.getJsApiTicketUrl() , param);
		WechatAccessTokenAndTicket fromJson = new Gson().fromJson(json, WechatAccessTokenAndTicket.class);
		if (fromJson.getErrcode() != 0) {
			throw new QmdException("微信获取jsapiTicket 错误"+ fromJson);
		}
		fromJson.setThenDate(DateUtil.addSecond(new Date(), fromJson.getExpires_in()));
		return fromJson;
	}
	
	@Data
	private class WechatAccessTokenAndTicket{
		// ---------- access_token -----------
		private String access_token;
		private int expires_in;
		// 获取到 token 的当时 时间+ 过期时间
		private Date thenDate;
		
		// ---------- ticket ----------
		private int errcode;
		private String errmsg;
		private String ticket;
	}
	
	@Data
	private class WechatSignature{
		// 随机字符串
		private String noncestr;//=Wm3WZYTPz0wzccnW
		private String jsapi_ticket;//=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
		private long timestamp; //1414587457
		private String url; //http://mp.weixin.qq.com?params=value
		
		public Map<String, Object> sort() {
			TreeMap<String, Object> map = new TreeMap<String, Object>();
			map.put("noncestr", this.noncestr);
			map.put("jsapi_ticket", this.jsapi_ticket);
			map.put("timestamp", this.timestamp);
			map.put("url", this.url);
			return map;
		}
	}
}
