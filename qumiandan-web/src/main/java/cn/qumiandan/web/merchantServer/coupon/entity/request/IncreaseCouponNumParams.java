package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 添加优惠券数量参数
 * @author lrj
 *
 */
@Data
public class IncreaseCouponNumParams implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券id
	 */
	@NotNull(message = "优惠券id不能为空")
	private Long couponId;
	
	/**
	 * 增加数量
	 */
	@NotNull(message = "增加数量不能为空")
	private Long number;

}
