package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 修改优惠券排除的商品集合参数
 * @author lrj
 *
 */
@Data
public class UpdateCouponProductParams implements Serializable{

private static final long serialVersionUID = 1L;
	
	/**
	 * 优惠券id
	 */
	@NotNull(message = "优惠券id不能为空")
	private Long couponId;
	
	/**
	 * 优惠券排除的商品集合
	 */
	List<Long> excludeProductIds;
}
