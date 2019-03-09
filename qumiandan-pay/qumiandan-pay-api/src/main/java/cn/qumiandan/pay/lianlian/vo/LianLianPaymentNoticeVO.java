package cn.qumiandan.pay.lianlian.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 连连支付回调结果
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午3:30:09
 */
@Data
public class LianLianPaymentNoticeVO implements Serializable {


	private static final long serialVersionUID = 1L;

	/** 商户编号 . */
	private String oidPartner;

	/** 签名方式 . */
	private String signType;

	/** 签名串 . */
	private String sign;

	/** 商户付款流水号即订单号. */
	private String noOrder;

	/** 商户付款请求时间. */
	private String dtOrder;

	/** 金额. */
	private String moneyOrder;

	/** 连连支付单. */
	private String oidPaybill;

	/** 商户付款请求时间. */
	private String infoOrder;

	/** 订单状态，付款结果以订单状态为判断依据. */
	private String resultPay;

	/** 清算时间. */
	private String settleDate;
}
