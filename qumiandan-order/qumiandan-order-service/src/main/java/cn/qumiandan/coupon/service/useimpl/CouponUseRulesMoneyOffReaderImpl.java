package cn.qumiandan.coupon.service.useimpl;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.qumiandan.common.exception.OrErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.coupon.service.ICouponUseRulesReader;
import cn.qumiandan.coupon.service.beanname.UseRuleBeanNames;
import cn.qumiandan.coupon.service.dto.UMoneyOffDTO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.utils.AssertUtil;

/**
 * 读取优惠券使用规则:满减
 * 如果该规则不存在，则直接返回当前价格
 * json: {"UMoneyOff":{"max":100,"cut":"10"}} (分)
 * 满100减10元
 * 
 * {"UDirectCutCash":10,"UMoneyOff":{"max":100,"cut":"10"}}
 */

@Component("UMoneyOff")
public class CouponUseRulesMoneyOffReaderImpl implements ICouponUseRulesReader {

	@Override
	public BigDecimal readRules(CouponVO coupon, BigDecimal price) {
		AssertUtil.isNull(price, "入参价格不能为空");
		AssertUtil.isNull(coupon, "入参优惠券信息不能为空");
		// 如果当前没有直减规则，则直接返回当前价格
		if (coupon.getUseRule() == null) {
			return price;
		}
		// 如果没有包含满减规则，则直接返回当前价格
		if (!coupon.getUseRule().contains(UseRuleBeanNames.UMoneyOff.name())) {
			return price;
		}
		UMoneyOffDTO obj = new Gson().fromJson(coupon.getUseRule(), UMoneyOffDTO.class);
		if (null == obj) {
			throw new QmdException(OrErrorCode.OR1004);
		}
		if (obj.getUMoneyOff() == null) {
			throw new QmdException(OrErrorCode.OR1004);
		}
		// 当前金额大于等于满减金额，此处金额只能单独使用，防止其他优惠将价格减到满的条件之下
		// 例如，元金额是100,此处优惠券是满100减10,但是提前被其他优惠信息将原价减到100一下，此处边无法生效
		// 所以一次只能使用一张优惠券
		if (price.compareTo(obj.getUMoneyOff().getMax()) >= 0) {
			// 当前价格-直减优惠券的价格
			price = price.subtract(obj.getUMoneyOff().getCut());
		}
		return price;
	}
	
}
