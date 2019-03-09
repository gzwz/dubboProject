package cn.qumiandan.pay.lianlian.confg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
/**
 * 连连支付配置文件
 * @author yuleidian
 * @version 创建时间：2019年1月4日 上午10:00:22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "lianlian")
@PropertySource("classpath:lianlianpay.properties")
public class LianLianConfig {

	/** 连连银通公钥（不需替换，这是连连公钥，用于报文加密和连连返回响应报文时验签，不是商户生成的公钥. */
	private String publicKeyOnline;
	/** 商户私钥 商户通过openssl工具生成公私钥，公钥通过商户站上传，私钥用于加签，替换下面的值 . */
	private String businessPrivateKey;
	/** 商户号（商户需要替换自己的商户号）. */
	private String oidPartner;
	/** 实时付款api版本. */
	private String apiVersion;
	/** 实时付款签名方式. */
	private String signType;
	/** 支付后回调地址*/
	private String callbackUrl;
	/** 支付请求地址*/
	private String paymentUrl;
	/** 查询支付结果请求地址*/
	private String queryPaymentUrl;
	/** 我们服务器ip*/
	private String ourIp;
	
}
