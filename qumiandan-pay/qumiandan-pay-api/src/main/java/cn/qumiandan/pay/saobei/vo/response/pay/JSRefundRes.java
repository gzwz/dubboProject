package cn.qumiandan.pay.saobei.vo.response.pay;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * 退款申请返回信息
 * @author yuleidian
 * @version 创建时间：2018年12月29日 下午4:01:41
 */
@Getter
@Setter
public class JSRefundRes extends BaseSaoBeiPayResVO {

	private static final long serialVersionUID = 1L;
	
	/** 请求类型，010微信，020 支付宝，060qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码*/
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
	private String merchantMo;
	/** 终端号*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;
	/** 终端流水号，商户系统的退款流水号，扫呗系统原样返回*/
	@Expose
	@SerializedName("terminal_trace")
	private String terminalTrace;	
	/** 终端退款时间，yyyyMMddHHmmss，全局统一时间格式*/
	@Expose
	@SerializedName("terminal_time")
	private String terminalTime;
	/** 退款金额，单位分*/
	@Expose
	@SerializedName("refund_fee")
	private String refundFee;
	/** 退款完成时间，yyyyMMddHHmmss，全局统一时间格式*/
	@Expose
	@SerializedName("end_time")
	private String endTime;
	/** 利楚唯一订单号*/
	@Expose
	@SerializedName("out_trade_no")
	private String outTradeNo;
	/** 利楚唯一退款单号*/
	@Expose
	@SerializedName("out_refund_no")
	private String outRefundNo;
	
	
	
	
	/**
	 * 获取支付完成时间
	 * @return
	 */
	public Date getRefundEndTime() {
		if (StringUtils.isNotBlank(endTime)) {
			try {
				return DateUtils.parseDate(endTime, "yyyyMMddHHmmss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}




	@Override
	public String toString() {
		return "JSRefundRes [payType=" + payType + ", merchantName=" + merchantName + ", merchantMo=" + merchantMo
				+ ", terminalId=" + terminalId + ", terminalTrace=" + terminalTrace + ", terminalTime=" + terminalTime
				+ ", refundFee=" + refundFee + ", endTime=" + endTime + ", outTradeNo=" + outTradeNo + ", outRefundNo="
				+ outRefundNo + "]";
	}
}
