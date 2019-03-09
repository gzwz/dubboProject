package cn.qumiandan.coupon.service;
import cn.qumiandan.coupon.entity.Coupon;

/**
 * 读取优惠券领取规则
 * @author WLZ
 * 2018年12月20日
 */
public interface ICouponTakeRulesReader {
	
	/**
	 * 读取优惠券领取规则
	 * 如果该规则不存在，且符合领取规则,直接返回（断言模式）
	 * 如果该规则限定了该用户不能领取，则直接抛出异常，阻断领取
	 * @param CouponVO 优惠券信息
	 * @param userId 当前领取的用户的Id
	 */
	void readRules(Coupon coupon,Long userId);

}
