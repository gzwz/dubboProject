package cn.qumiandan.pay.saobei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * 扫呗配置信息
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午5:17:42
 */
@Data
@Configuration
@PropertySource("classpath:saobeipay.properties")
@ConfigurationProperties
public class SaoBeiConfig {

	// ————————————————————————————————基础配置信息———————————————————————————————————————————————————————————————————
	/** 机构号*/
	private String instNo;
	/** 令牌*/
	private String key;
	// ————————————————————————————————商户操作url———————————————————————————————————————————————————————————————————
	/** 验证商户名是否唯一*/
	private String checkMerchantNamelUrl;
	
	/** 创建商户信息url*/
	private String createMerchantUrl;
	
	/** 更新商户信息url*/
	private String updateMerchantUrl;
	
	/** 创建终端url*/
	private String createTerminalUrl;
	
	/** 查询终端url*/
	private String queryTerminalUrl;
	
	// ————————————————————————————————支付操作url———————————————————————————————————————————————————————————————————
	/** 微信公众号JSAPI支付授权*/
	private String authopenidUrl;
	
	/**  公众号预支付（统一下单）*/
	private String jsPayUrl;
	
	/** 退款申请*/
	private String jsRefundUrl;
	
	// ————————————————————————————————回调地址———————————————————————————————————————————————————————————————————
	/** 创建商户回调地址*/
	private String createMerchantCallbackUrl;
	
	/** 扫呗公众号支付回调普通订单处理*/
	private String jsPayCallbackUrl;
	
	/** 扫呗公众号支付回调游戏订单处理*/
	private String jsPayGameCallbackUrl;
}
