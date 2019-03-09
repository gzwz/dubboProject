package cn.qumiandan.web.merchantServer.coupon.entity.request;

import lombok.Data;

@Data
public class QueryCouponParams {

	private Long shopId;
	
	private Long couponId;
}
