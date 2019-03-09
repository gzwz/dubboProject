package cn.qumiandan.web.frontServer.shop.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shop.api.IShopGameSwitchService;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopExtendParams;

@RestController
@RequestMapping(value="/shopExtend")
public class ShopGameSwitchController {
	@Reference
	private IShopGameSwitchService shopGameSwitchService;
	
	/**
	 * 游戏开关
	 * @param shopId
	 * @param gameSwitch
	 * @return
	 */
	@RequestMapping(value="/gameSwitch")
	public Result<Integer> gameSwitch(@Valid @RequestBody(required = false) ShopExtendParams params,BindingResult bindingResult)throws Exception{
		if(bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		shopGameSwitchService.gameSwitch( params.getShopId(), params.getGameSwitch());
		return ResultUtils.success("修改成功");
	}
	/**
	 * 检查游戏开关是否开启
	 * @param extendVO
	 * @return
	 */
	/*
	 * @RequestMapping(value="/checkSwitch") public boolean checkSwitch(ShopExtendVO
	 * extendVO) { boolean b = shopGameSwitchService.checkSwitch(extendVO); return
	 * b; }
	 */
}
