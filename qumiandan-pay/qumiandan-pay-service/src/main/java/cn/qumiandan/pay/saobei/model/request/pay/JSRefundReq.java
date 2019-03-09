package cn.qumiandan.pay.saobei.model.request.pay;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.utils.DateUtil;
import lombok.Getter;

/**
 * 申请退款请求
 * @author yuleidian
 * @version 创建时间：2018年12月29日 下午3:35:57
 */
@Getter
public class JSRefundReq extends BaseSaoBeiPayReqDefinition {
	
	private static final long serialVersionUID = 1L;
	
	/** 版本号，当前版本100*/
	@Expose
	@SerializedName("pay_ver")
	private String payVer; 
	/** 请求类型，010微信，020 支付宝，040 现金，060qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000自动识别类型 */
	@Expose
	@SerializedName("pay_type")
	private String payType;
	/** 接口类型，当前类型030*/
	@Expose
	@SerializedName("service_id")
	private String serviceId;
	/** 终端号*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;
	/** 终端退款流水号，填写商户系统的退款流水号*/
	@Expose
	@SerializedName("terminal_trace")
	private String  terminalTrace;
	/** 终端退款时间，yyyyMMddHHmmss，全局统一时间格式*/
	@Expose
	@SerializedName("terminal_time")
	private String  terminalTime;
	/** 退款金额，单位分*/
	@Expose
	@SerializedName("refund_fee")
	private String  refundFee;
	/** 订单号，查询凭据，利楚订单号、微信订单号、支付宝订单号任意一个*/
	@Expose
	@SerializedName("out_trade_no")
	private String  outTradeNo;
	
	public JSRefundReq() {
		this.payVer = "100";
		this.serviceId = "030";
		this.terminalTime = DateUtil.parseDateToStr(new Date(), "yyyyMMddHHmmss");
	}
	
	@Override
	public String getSign() {
		if (StringUtils.isBlank(keySign)) {
			createResolveMap();
			doGetSign();
		}
		return keySign;
	}

	@Override
	protected void doGetSign() {
		StringBuilder sign = new StringBuilder();
		if (StringUtils.isNotBlank(payVer)) {
			sign.append("pay_ver").append(EQ).append(payVer);
		}
		if (StringUtils.isNotBlank(payType)) {
			sign.append(AND).append("pay_type").append(EQ).append(payType);
		}
		if (StringUtils.isNotBlank(serviceId)) {
			sign.append(AND).append("service_id").append(EQ).append(serviceId);
		}
		if (StringUtils.isNotBlank(getMerchantNo())) {
			sign.append(AND).append("merchant_no").append(EQ).append(getMerchantNo());
		}
		if (StringUtils.isNotBlank(terminalId)) {
			sign.append(AND).append("terminal_id").append(EQ).append(terminalId);
		}
		if (StringUtils.isNotBlank(terminalTrace)) {
			sign.append(AND).append("terminal_trace").append(EQ).append(terminalTrace);
		}
		if (StringUtils.isNotBlank(terminalTime)) {
			sign.append(AND).append("terminal_time").append(EQ).append(terminalTime);
		}
		if (StringUtils.isNotBlank(refundFee)) {
			sign.append(AND).append("refund_fee").append(EQ).append(refundFee);
		}
		if (StringUtils.isNotBlank(outTradeNo)) {
			sign.append(AND).append("out_trade_no").append(EQ).append(outTradeNo);
		}
		if (StringUtils.isNotBlank(accessToken)) {
			sign.append(AND).append("access_token").append(EQ).append(accessToken);
		}
		this.keySign = encrypt(sign.toString());
		reqCondition.put("key_sign", keySign);
	}

	public JSRefundReq setPayVer(String payVer) {
		this.payVer = payVer;
		return this;
	}

	public JSRefundReq setPayType(String payType) {
		this.payType = payType;
		return this;
	}

	public JSRefundReq setServiceId(String serviceId) {
		this.serviceId = serviceId;
		return this;
	}

	public JSRefundReq setTerminalId(String terminalId) {
		this.terminalId = terminalId;
		return this;
	}

	public JSRefundReq setTerminalTrace(String terminalTrace) {
		this.terminalTrace = terminalTrace;
		return this;
	}

	public JSRefundReq setTerminalTime(String terminalTime) {
		this.terminalTime = terminalTime;
		return this;
	}

	public JSRefundReq setRefundFee(String refundFee) {
		this.refundFee = refundFee;
		return this;
	}

	public JSRefundReq setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
		return this;
	}
}
