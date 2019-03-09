package cn.qumiandan.web.frontServer.order.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.order.entity.request.OrderDetailParams;

@RestController
@RequestMapping("/gameOrder/")
public class GameOrderController {

	@Reference()
	private IGameOrderService gameOrderService;
	
	/**
	 * 根据订单id查询游戏订单
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGameExtendByOrderId")
	public Result<List<GameExtendVO>> getGameExtendByOrderId(@Valid @RequestBody(required = false) OrderDetailParams params,
			BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<GameExtendVO> gameExtendByOrderId = gameOrderService.getGameExtendByOrderId(params.getOrderId());
		return ResultUtils.success(gameExtendByOrderId);
	}
}
