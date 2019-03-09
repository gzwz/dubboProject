package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 根据店铺id和用户id查询优惠券
 * @author lrj
 *
 */
@Data
public class GetCouponByShopIdAndUserIdParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 发行者(模板中的使用范围，是店铺，则提供店铺id;使用范围为平台的时候为0)
	 */
	@NotNull(message = "发行者不能为空")
	private Long publisher;
	
	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	private Long userId;
}
