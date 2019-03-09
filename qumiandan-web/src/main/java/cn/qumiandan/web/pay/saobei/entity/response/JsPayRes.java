package cn.qumiandan.web.pay.saobei.entity.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 公众号预支付（统一下单）返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月19日 下午5:45:33
 */
@Data
public class JsPayRes implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 流水id 前端轮询请求此条支付信息是否成功*/
	private String tradeId;
	
	/** 微信公众号支付返回字段，公众号id*/
	private String appId;
	
	/** 微信公众号支付返回字段，时间戳，示例：1414561699，标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。*/
	private String timeStamp;
	
	/** 微信公众号支付返回字段，随机字符串*/
	private String nonceStr;
	
	/** 微信公众号支付返回字段，订单详情扩展字符串，示例：prepay_id=123456789，统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=*/
	private String packageStr;
	
	/** 微信公众号支付返回字段，签名方式，示例：MD5,RSA*/
	private String signType;
	
	/** 微信公众号支付返回字段，签名*/
	private String paySign;
	
	/** 支付宝JSAPI支付返回字段用于调起支付宝JSAPI*/
	private String aliTradeNo;
	
	/** qq钱包公众号支付*/
	private String tokenId;
	
}
