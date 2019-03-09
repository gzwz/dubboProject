package cn.qumiandan.web.frontServer.product.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.pricecalculate.api.IOrderPriceCalculateService;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.web.frontServer.order.entity.request.OrderAddParams;

@RestController
@RequestMapping("/calculatePrice/")
public class OrderPriceCalculateController {

	@Reference
	private IOrderPriceCalculateService calculateService;
	
	@RequestMapping("orderCalculatePrice")
	public BigDecimal orderCalculatePrice(@Valid @RequestBody(required = false) 
	OrderAddParams params, BindingResult bindingResult) {
		OrderAddVO orderVO = CopyBeanUtil.copyBean(params, OrderAddVO.class);
		BigDecimal price = calculateService.orderCalculateByCoupon(orderVO);
		return price;
	} 
}
