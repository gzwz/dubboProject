package cn.qumiandan.web.frontServer.games.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.GameOrderVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.games.entity.request.AddShareGameOrderParams;
import cn.qumiandan.web.frontServer.games.entity.request.CalculateGamePriceParams;
import cn.qumiandan.web.frontServer.games.entity.request.QueryShareGameOrderParams;

@RestController
@RequestMapping("/game/")
public class ShareGameController {

	@Reference
	private IGameOrderService gameOrderService;
	
	@Reference
	private IOrderService orderService;
	
	@RequestMapping("calculateShareGameprice")
	public Result<BigDecimal> calculateShareGameprice(@Valid @RequestBody(required = false) 
	CalculateGamePriceParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderVO orderVO = orderService.getOrderById(params.getOrderId());
		if (orderVO == null) {
			throw new QmdException("该笔游戏对应的订单不存在");
		}
		BigDecimal gamePrice = gameOrderService.getGamePrice(orderVO.getNeedPayAmount());
		Result<BigDecimal> result = ResultUtils.success();
		result.setData(gamePrice);
		return result; 
	}
	
	@RequestMapping("queryGameOrder")
	public Result<GameExtendVO> queryGameOrder(@Valid @RequestBody(required = false)
	QueryShareGameOrderParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		GameExtendVO gameVO = gameOrderService.getGameOrderByGameId(params.getGameId());
		if (null == gameVO) {
			return ResultUtils.success();
		}
		Result<GameExtendVO> result = ResultUtils.success();
		result.setData(gameVO);
		return result; 
	}
	
	@RequestMapping("addGameOrder")
	public Result<GameOrderVO> addGameOrder(@Valid @RequestBody(required = false)
	AddShareGameOrderParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		GameOrderVO gameVO = gameOrderService.addGameOrder(params.getOrderId(), params.getPrice(), params.getTimes());
		if (null == gameVO) {
			return ResultUtils.error();
		}
		Result<GameOrderVO> result = ResultUtils.success();
		result.setData(gameVO);
		return result; 
	}
}
