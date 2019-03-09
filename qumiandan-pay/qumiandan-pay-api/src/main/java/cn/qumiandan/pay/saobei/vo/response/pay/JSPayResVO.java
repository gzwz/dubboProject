package cn.qumiandan.pay.saobei.vo.response.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * 扫呗支付请求返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午5:20:55
 */
@Getter
@Setter
public class JSPayResVO extends BaseSaoBeiPayResVO {

	private static final long serialVersionUID = 1L;

	/**请求类型，010微信，020支付宝，060qq钱包，090口碑，100翼支付*/
	@Expose
	@SerializedName("pay_type")
	private String payType;
	
	/** 商户名称*/
	@Expose
	@SerializedName("merchant_name")
	private String merchantName;
	
	/** 商户号*/
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;
	
	/** 响应码：01成功 ，02失败，请求成功不代表业务处理成功*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;
	
	/** 终端流水号，商户系统的订单号，扫呗系统原样返回*/
	@Expose
	@SerializedName("terminal_trace")
	private String terminalRrace;
	
	/** 终端交易时间，yyyyMMddHHmmss，全局统一时间格式*/
	@Expose
	@SerializedName("terminal_time")
	private String terminalTime;
	
	/** 金额，单位分*/
	@Expose
	@SerializedName("total_fee")
	private String totalFee;
	
	/** 利楚唯一订单号*/
	@Expose
	@SerializedName("out_trade_no")
	private String outTradeNo;
	
	//------------------------------------------------------以下字段是result_code 为01时返回----------------------------------------------------
	/** 微信公众号支付返回字段，公众号id*/
	@Expose
	@SerializedName("appId")
	private String appId;
	
	/** 微信公众号支付返回字段，时间戳，示例：1414561699，标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。*/
	@Expose
	@SerializedName("timeStamp")
	private String timeStamp;
	
	/** 微信公众号支付返回字段，随机字符串*/
	@Expose
	@SerializedName("nonceStr")
	private String nonceStr;
	
	/** 微信公众号支付返回字段，订单详情扩展字符串，示例：prepay_id=123456789，统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=*/
	@Expose
	@SerializedName("package_str")
	private String packageStr;
	
	/** 微信公众号支付返回字段，签名方式，示例：MD5,RSA*/
	@Expose
	@SerializedName("signType")
	private String signType;
	
	/** 微信公众号支付返回字段，签名*/
	@Expose
	@SerializedName("paySign")
	private String paySign;
	
	/** 支付宝JSAPI支付返回字段用于调起支付宝JSAPI*/
	@Expose
	@SerializedName("ali_trade_no")
	private String aliTradeNo;
	
	/** qq钱包公众号支付*/
	@Expose
	@SerializedName("token_id")
	private String tokenId;
	
	/** 流水id*/
	private Long tradeId;
	
}
