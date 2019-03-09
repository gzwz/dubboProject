package cn.qumiandan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:wechat.properties")
public class WechatConfig {

	@Value("${wechat.appId}")
	private String appId;
	
	@Value("${wechat.appSecret}")
	private String appSecret;

	@Value("${wechat.loginCodeUrl}")
	private String loginCodeUrl;
	
	@Value("${wechat.acGetUserInfoUrl}")
	private String acGetUserInfoUrl;
	
	@Value("${wechat.accessTokenUrl}")
	private String accessTokenUrl;
	
	@Value("${wechat.jsApiTicketUrl}")
	private String jsApiTicketUrl;
	
}
