package cn.qumiandan.coupon.service;
import java.math.BigDecimal;

import cn.qumiandan.coupon.vo.CouponVO;

/**
 * 读取优惠券规则
 * @author WLZ
 * 2018年12月7日
 */
public interface ICouponUseRulesReader {
	
	/**
	 * 读取优惠券使用规则
	 * 如果该规则不存在，则直接返回当前价格
	 * @param CouponVO 优惠券信息
	 * @param price 需要计算的当前价格
	 * @return 计算好后的价格
	 */
	BigDecimal readRules(CouponVO coupon,BigDecimal price);

}
