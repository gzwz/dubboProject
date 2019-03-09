package cn.qumiandan.system.ali.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * 阿里支付配置
 * @author yuleidian
 * @version 创建时间：2018年12月25日 上午9:48:38
 */
@Data
@Configuration
@PropertySource("classpath:alipay.properties")
public class AliPayConfig {

	@Value("${alipay.appId}")
	private String appId;
	
	@Value("${alipay.getway}")
	private String getway;
	
	@Value("${alipay.alipayPublic}")
	private String alipayPublic;
	
	@Value("${alipay.appPrivate}")
	private String appPrivate;
	
}
