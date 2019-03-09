package cn.qumiandan.pricecalculate.api;

import java.math.BigDecimal;

import cn.qumiandan.order.vo.OrderAddVO;

public interface IOrderPriceCalculateService {

	/**
	 * 计算订单 - 优惠券 后的价格（其他优惠信息不参与计算，如需要，请调用相关计算）
	 * @param orderId 订单id
	 * @return 计算完毕后 = 订单应付金额
	 */
	BigDecimal orderCalculateByCoupon(OrderAddVO orderVO);
}
