package cn.qumiandan.coupon.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.qumiandan.coupon.constant.CouponEnum;
import lombok.Data;

@Data
@TableName("qmd_coupon_take_record")
public class CouponTakeRecord {

	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 领取者id */
	private Long userId;
	
	/** 生成优惠券的id */
	private Long couponId;
	
	/** 领取时间 */
	private Date createDate;
	
	/**状态（1.未使用，2.已使用，3.已过期，4.销毁；0：已删除）
	 * {@link CouponEnum}
	 */
	private Byte status;
	
}
