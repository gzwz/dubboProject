package cn.qumiandan.pricecalculate.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.OrErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.coupon.api.ICouponService;
import cn.qumiandan.coupon.service.ICouponUseRulesReader;
import cn.qumiandan.coupon.service.beanname.UseRuleBeanNames;
import cn.qumiandan.coupon.vo.CouponTakeRecordVO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderProductVO;
import cn.qumiandan.pricecalculate.api.IOrderPriceCalculateService;
import cn.qumiandan.product.api.IProductService;
import cn.qumiandan.product.vo.ProductBasicVO;
import cn.qumiandan.shopmember.api.IShopMemberService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IOrderPriceCalculateService.class)
public class OrderPriceCalculateImpl implements IOrderPriceCalculateService {

	@Autowired
	private ICouponService couponservice;
	
	@Reference
	private IProductService productService;
	
	@Reference
	private IShopMemberService shopMemberService;
	
	@Override
	public BigDecimal orderCalculateByCoupon(OrderAddVO orderVO) {
		AssertUtil.isNull(orderVO,AssertUtil.ParamCanNotNULL);
		List<OrderProductVO> productList = orderVO.getOrderProductList();
		// 商品总价
		BigDecimal price = new BigDecimal(0);
		//此处判断 当前用户是否是会员，如果商家
		BigDecimal discount = null;
		// 计算出商品总价
		for (OrderProductVO product : productList) {
			BigDecimal onetypeProdect = new BigDecimal(product.getNumber());  
			// 此处调用商品查询接口，查询商品价格  商品  原价*商品现价
			ProductBasicVO productvo = productService.getProductBasicById(product.getProductId());
			if (productvo == null) {
				throw new QmdException("商品不存在");
			}
			//此处判断 当前用户是否是会员，如果商家
			discount = shopMemberService.getShopMemberDiscount(orderVO.getShopId(), orderVO.getUserId());
			
			BigDecimal productPrice = productvo.getPresentPrice();
			if (discount != null) {
				log.info("当前用户是该店铺的会员，折扣率是："+discount.doubleValue());
				// 会员折扣  千分之。。。
				productPrice = productPrice.multiply(discount.divide(new BigDecimal(1000)));
			}
			product.setDiscountRate(discount);
			product.setDiscountAmount(productvo.getPresentPrice().subtract(productPrice));
			// 商品总价
			BigDecimal multiply = onetypeProdect.multiply(productPrice);
			price = price.add(multiply);
		}
		orderVO.setProductAmountTotal(price);
		// 设置会员折扣率
		orderVO.setDiscountRate(discount);
		//判断是否使用了优惠券
		if (orderVO.getCouponId() != null) {
			Long couponId = orderVO.getCouponId();
			Long userId = orderVO.getUserId();
			
			// 领取之前判断是否已经领取
			List<CouponTakeRecordVO> record = couponservice.queryTakeCouponRecord(couponId, userId);
			if (record == null || record.size() < 1) {
				log.error(OrErrorCode.OR1003.getMsg());
				throw new QmdException(OrErrorCode.OR1003);
			}
			CouponVO coupon = couponservice.queryCouponById(couponId);
			if (coupon == null) {
				throw new QmdException(OrErrorCode.OR1003);
			}
			// 方便后期计算优惠券中是否排除该订单中包含的商品
			coupon.setProductList(productList);
			// 以后添加所有优惠券规则都在此遍历
			// ------------ 遍历所有的规则 --------------
			for (UseRuleBeanNames ruleName : UseRuleBeanNames.values()) {
				ICouponUseRulesReader crReader = SpringContextUtils.getBean(ruleName.name());
				price = crReader.readRules(coupon, price);
			}
		}
		return price.setScale(0, BigDecimal.ROUND_DOWN);
	}


}
