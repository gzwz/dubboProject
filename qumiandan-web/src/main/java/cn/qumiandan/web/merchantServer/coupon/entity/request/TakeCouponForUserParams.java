package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 优惠券领取参数
 * @author lrj
 *
 */
@Data
public class TakeCouponForUserParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券id
	 */
	@NotNull(message = "优惠券id不能为空")
	private Long couponId;
	
	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	private Long userId;
}
