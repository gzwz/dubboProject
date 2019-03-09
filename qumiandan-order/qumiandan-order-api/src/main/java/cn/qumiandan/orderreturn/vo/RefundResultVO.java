package cn.qumiandan.orderreturn.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 退款结果VO
 * @author yuleidian
 * @version 创建时间：2018年12月30日 下午2:50:01
 */
@Data
public class RefundResultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 退款申请id*/
	private Long applyId;
	
	/** 订单id*/
	private String orderId;
	
	/** 
	 * 退款是否成功 
	 * true : 成功
	 * false : 失败 
	 */
	private Boolean flag;
	
	/** 退款时间*/
	private Date refundDate;
	
	/**
	 * 退单打款第三方流水号id
	 */
	private String outTrandNo;
	/**
	 * 退款金额
	 */
	private BigDecimal refundFee;
	/**
	 * 退款单号
	 */
	private String outRefundNo; 
}
