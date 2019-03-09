package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;

import lombok.Data;
/**
 * 获取平台优惠券
 * @author lrj
 *
 */
@Data
public class GetPlatformCouponParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;
}
