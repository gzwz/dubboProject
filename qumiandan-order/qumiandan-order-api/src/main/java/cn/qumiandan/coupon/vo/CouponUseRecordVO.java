package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class CouponUseRecordVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	/** 订单号 */
	private String orderId;
	
	/** 优惠券ID */
	private Long couponId;
	
	/** 优惠券使用规则 */
	private String couponUseRule;
	
	/** 扣减金额 */
	private BigDecimal cutAmount;
	
}
