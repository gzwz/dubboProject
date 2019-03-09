package cn.qumiandan.web.pay.lianlian.entity.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 连连回调 提现结果
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午3:20:51
 */
@Data
public class LianLianNoticeParams implements Serializable {

	private static final long serialVersionUID = -821204118733047614L;

	/** 商户编号 . */
	@JsonProperty("oid_partner")
	private String oidPartner;

	/** 签名方式 . */
	@JsonProperty("sign_type")
	private String signType;

	/** 签名串 . */
	@JsonProperty("sign")
	private String sign;

	/** 商户付款流水号即订单号. */
	@JsonProperty("no_order")
	private String noOrder;

	/** 商户付款请求时间. */
	@JsonProperty("dt_order")
	private String dtOrder;

	/** 金额. */
	@JsonProperty("money_order")
	private String moneyOrder;

	/** 连连支付单. */
	@JsonProperty("oid_paybill")
	private String oidPaybill;

	/** 商户付款请求时间. */
	@JsonProperty("info_order")
	private String infoOrder;

	/** 订单状态，付款结果以订单状态为判断依据. */
	@JsonProperty("result_pay")
	private String resultPay;

	/** 清算时间. */
	@JsonProperty("settle_date")
	private String settleDate;
}
