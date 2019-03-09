package cn.qumiandan.coupon.service.useimpl;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.qumiandan.common.exception.OrErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.coupon.service.ICouponUseRulesReader;
import cn.qumiandan.coupon.service.beanname.UseRuleBeanNames;
import cn.qumiandan.coupon.service.dto.UDirectCutDTO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.utils.AssertUtil;

/**
 * 读取优惠券使用规则:直减
 * 如果该规则不存在，则直接返回当前价格
 * json: UDirectCutCash:10000 (分)
 * {"UDirectCutCash":10,"UMoneyOff":{"man":100,"cut":"10"}}
 */

@Component("UDirectCutCash")
public class CouponUseRulesDirectCutReaderImpl implements ICouponUseRulesReader {

	@Override
	public BigDecimal readRules(CouponVO coupon, BigDecimal price) {
		AssertUtil.isNull(price, "入参价格不能为空");
		AssertUtil.isNull(coupon, "入参优惠券信息不能为空");
		// 读取规则内容
		// 如果当前没有直减规则，则直接返回当前价格
		if (coupon.getUseRule() == null) {
			return price;
		}
		// 如果没有包含立减规则，则直接返回当前价格
		if (!coupon.getUseRule().contains(UseRuleBeanNames.UDirectCutCash.name())) {
			return price;
		}
		// 有该规则，但是tojson转换错误
		UDirectCutDTO jsonObject = new Gson().fromJson(coupon.getUseRule(), UDirectCutDTO.class);
		if (jsonObject == null) {
			throw new QmdException(OrErrorCode.OR1004);
		}
		BigDecimal directCutCash = jsonObject.getUDirectCutCash();
		if (null == directCutCash) {
			throw new QmdException(OrErrorCode.OR1004);
		}
		// 当前价格-直减优惠券的价格
		price = price.subtract(directCutCash);
		return price;
	}
	
 
	
}
