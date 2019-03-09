package cn.qumiandan.coupon.api;

import cn.qumiandan.coupon.vo.CouponUseRecordVO;

/**
 * 优惠券使用记录管理接口
 * @author lrj
 *
 */
public interface ICouponUseRecordService {
	
	/**
	 * 根据订单id查询优惠券使用记录
	 * @param orderId
	 * @return
	 */
	CouponUseRecordVO getCouponUseRecordByOrderId(String orderId);
}
