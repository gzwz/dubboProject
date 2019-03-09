package cn.qumiandan.web.pay.saobei.entity.reqeust;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 平台公众号支付回调结果请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:38:12
 */
@Data
public class JSPayCallbackParams implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 响应码：01成功 ，02失败，响应码仅代表通信状态，不代表业务结果*/
	@NotBlank(message = "return_code不能为空")
	@JsonProperty("return_code")
	private String returnCode;
	
	/** 返回信息提示，“签名失败”，“参数格式校验错误"等*/
	@JsonProperty("return_msg")
	private String returnMsg;
	
	/** 业务结果：01成功 ，02失败*/
	@JsonProperty("result_code")
	private String resultCode;
	
	/** 请求类型，010微信，020 支付宝，060qq钱包，080京东钱包，090口碑，100翼支付*/
	@JsonProperty("pay_type")
	private String payType;
	
	/** 付款方用户id，“微信openid”、“支付宝账户”、“qq号”等*/
	@JsonProperty("user_id")
	private String userId;
	
	/** 商户名称*/
	@JsonProperty("merchant_name")
	private String merchantName;
	
	/** 商户号*/
	@JsonProperty("merchant_no")
	private String merchantNo;
	
	/** 终端号*/
	@JsonProperty("terminal_id")
	private String terminalId;
	
	/** 终端流水号，此处传商户发起预支付或公众号支付时所传入的交易流水号*/
	@JsonProperty("terminal_trace")
	private String terminalTrace;
	
	/** 终端交易时间，yyyyMMddHHmmss，全局统一时间格式（01时参与拼接）*/
	@JsonProperty("terminal_time")
	private String terminalTime;
	
	/** 当前支付终端流水号，与pay_time同时传递，返回时不参与签名*/
	@JsonProperty("pay_trace")
	private String payTrace;
	
	/** 当前支付终端交易时间，yyyyMMddHHmmss，全局统一时间格式，与pay_trace同时传递*/
	@JsonProperty("pay_time")
	private String payTime;
	
	/** 金额，单位分*/
	@JsonProperty("total_fee")
	private String totalFee;
	
	/** 支付完成时间，yyyyMMddHHmmss，全局统一时间格式*/
	@JsonProperty("end_time")
	private String endTime;
	
	/** 利楚唯一订单号*/
	@JsonProperty("out_trade_no")
	private String outTradeNo;
	
	/** 通道订单号，微信订单号、支付宝订单号等*/
	@JsonProperty("channel_trade_no")
	private String channelTradeNo;
	
	/** 附加数据，原样返回*/
	@JsonProperty("attach")
	private String attach;
	
	/** 口碑实收金额，pay_type为090时必填*/
	@JsonProperty("receipt_fee")
	private String receiptFee;
	
	/** 签名字符串,拼装所有必传参数+令牌，32位md5加密转换*/
	@JsonProperty("key_sign")
	private String keySign;
}
