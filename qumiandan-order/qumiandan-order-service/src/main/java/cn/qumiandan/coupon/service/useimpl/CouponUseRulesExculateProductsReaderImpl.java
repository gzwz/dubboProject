package cn.qumiandan.coupon.service.useimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.OrErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.coupon.service.ICouponUseRulesReader;
import cn.qumiandan.coupon.service.beanname.UseRuleBeanNames;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.vo.OrderProductVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取优惠券使用规则: 排除查看当前订单中的商品是否包含在排除
 */
@Slf4j
@Component("SomeProductCanNotUse")
public class CouponUseRulesExculateProductsReaderImpl implements ICouponUseRulesReader {

	@Reference
	private IOrderService orderSerice;
	
	@Override
	public BigDecimal readRules(CouponVO coupon, BigDecimal price) {
		AssertUtil.isNull(price, "入参价格不能为空");
		AssertUtil.isNull(coupon, "入参优惠券信息不能为空");
		// 如果当前没有使用规则，则直接返回当前价格
		if (coupon.getUseRule() == null) {
			return price;
		}
		// 如果没有包含满减指定商品不能使用，则直接返回当前价格
		if (!coupon.getUseRule().contains(UseRuleBeanNames.SomeProductCanNotUse.name())) {
			return price;
		}
		if (ObjectUtils.isEmpty(coupon.getProductList())) {
			log.info("计算优惠券排除商品的服务中,该笔订单信息中商品列表为空。。。此处不应为空");
			return price;
		}
		//取出商品ids
		List<Long> productIds = new ArrayList<Long>();
		for (OrderProductVO op : coupon.getProductList()) {
			productIds.add(op.getProductId());
		}
		// 查询当前订单的商品列表，和当前优惠券的排除商品id 是否有匹配的，匹配
		if (ObjectUtils.isEmpty(coupon.getExcludeProductIds())) {
			return price;
		}
		for (Long t : coupon.getExcludeProductIds()) {
			if (productIds.contains(t)) {
				throw new QmdException(OrErrorCode.OR1021);
			}
		}
		coupon.getExcludeProductIds().forEach(O->{
			if (productIds.contains(O)) {
				throw new QmdException(OrErrorCode.OR1021.getMsg()+" 商品编号是："+O);
			}
		});
		return price;
		/*
		 * UMoneyOffDTO obj = new Gson().fromJson(coupon.getUseRule(),
		 * UMoneyOffDTO.class); if (null == obj) { throw new
		 * QmdException(OrErrorCode.OR1004); } if (obj.getUMoneyOff() == null) { throw
		 * new QmdException(OrErrorCode.OR1004); }
		 */
		// 当前金额大于等于满减金额，此处金额只能单独使用，防止其他优惠将价格减到满的条件之下
		// 例如，元金额是100,此处优惠券是满100减10,但是提前被其他优惠信息将原价减到100一下，此处边无法生效
		// 所以一次只能使用一张优惠券
		/*
		 * if (price.compareTo(obj.getUMoneyOff().getMax()) >= 0) { // 当前价格-直减优惠券的价格
		 * price = price.subtract(obj.getUMoneyOff().getCut()); }
		 */
	}
}
