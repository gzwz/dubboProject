package cn.qumiandan.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName ("qmd_coupon_use_record")
public class CouponUseRecord {
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 订单号 */
	private String orderId;
	
	/** 优惠券ID */
	private Long couponId;
	
	/** 优惠券使用规则 */
	private String couponUseRule;
	
	/** 扣减金额 */
	private BigDecimal cutAmount;
	
	/** 创建时间  */
	private Date createDate;
	  
}
