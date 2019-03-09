package cn.qumiandan.web.frontServer.shop.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.shopprofit.vo.ShopProfitDetailVO;
import cn.qumiandan.shopprofit.vo.ShopProfitVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopIdParams;
import cn.qumiandan.web.frontServer.shop.entity.request.UpdateShopProfitParams;

/**
 * 店铺分润管理
 * @author lrj
 * @version 创建时间：2018年12月3日 16:07
 */
@RestController
@RequestMapping("/shopProfit/")
public class ShopProfitController {
	@Reference()
	private IShopProfitService shopProfitService;
	
	/**
	 * 更新店铺分润信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateShopProfit")
	public Result<Void> updateShopProfit(@Valid @RequestBody(required = false) UpdateShopProfitParams params, BindingResult bindingResult) throws Exception  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopProfitVO shopProfitVO =
		CopyBeanUtil.copyBean(params, ShopProfitVO.class);
		shopProfitService.updateShopProfit(shopProfitVO);
		return ResultUtils.success("更新店铺分润信息成功");
	}
	
	/**
	 * 查询店铺分润信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getShopProfitByShopId")
	public Result<ShopProfitDetailVO> getShopProfitByShopId(@Valid @RequestBody(required = false) ShopIdParams params, BindingResult bindingResult) throws Exception  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopProfitDetailVO profitDetailVO = shopProfitService.getShopProfitByShopId(params.getId());
		Result<ShopProfitDetailVO> result = ResultUtils.success();
		result.setData(profitDetailVO);
		return result;
	}
	
	/**
	 * 删除电铺分润信息，删除后店铺费率以行业费率为主
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteShopProfit")
	public Result<Integer> deleteShopProfit(@Valid @RequestBody(required = false) ShopIdParams params, BindingResult bindingResult) throws Exception  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		shopProfitService.deleteShopProfit(params.getId());
		return ResultUtils.success("删除店铺分润信息成功");
	}
	
}
