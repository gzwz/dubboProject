package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.util.Date;

import cn.qumiandan.coupon.constant.CouponEnum;
import lombok.Data;


@Data
public class CouponTakeRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/** 领取者id */
	private Long userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/** 生成优惠券的id */
	private Long couponId;
	
	/** 领取时间 */
	private Date createDate;
	
	/**状态（1.未使用，2.已使用，3.已过期，4.销毁；0：已删除）
	 * {@link CouponEnum}
	 */
	private Byte status;
	

}
