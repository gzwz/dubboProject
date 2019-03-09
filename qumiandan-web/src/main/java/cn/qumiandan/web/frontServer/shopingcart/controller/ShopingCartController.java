package cn.qumiandan.web.frontServer.shopingcart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shoppingcart.api.IShoppingCartService;
import cn.qumiandan.shoppingcart.vo.ShopingCartDetailVO;
import cn.qumiandan.shoppingcart.vo.ShoppingCartVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.shopingcart.entity.request.AddShopingCartrParams;
import cn.qumiandan.web.frontServer.shopingcart.entity.request.GetIdParams;

/**
 * 购物车管理
 * @author lrj
 * @version 创建时间：2018年11月28日 10:00
 */
@RestController
@RequestMapping("/shopingCart/")
public class ShopingCartController {
	@Reference()
	private IShoppingCartService shoppingCartService;
	
	/**
	 * 添加购物车
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addShopingCart")
	public Result<Integer> addShopingCart(@Valid @RequestBody(required = false) AddShopingCartrParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShoppingCartVO shoppingCartVO =
		CopyBeanUtil.copyBean(params, ShoppingCartVO.class);
		shoppingCartVO.setUserId(ShiroUtils.getUserId());
		shoppingCartService.addShopingCart(shoppingCartVO);
		return ResultUtils.success("添加购物车成功");
	}
	
	/**
	 * 删除购物车商品
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("deleteShopingCart")
	public Result<Integer> deleteShopingCart(@Valid @RequestBody(required = false) GetIdParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		shoppingCartService.deleteShopingCart(params.getId());
		return ResultUtils.success("删除购物车商品成功");
	}
	
	/**
	 * 根据用户id查询购物车商品
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopingCartByUserId")
	public Result<List<ShopingCartDetailVO>> getShopingCartByUserId(){
		List<ShopingCartDetailVO> shopingCartDetailVOList=shoppingCartService.getShopingCartByUserId(ShiroUtils.getUserId());
		Result<List<ShopingCartDetailVO>> result = ResultUtils.success();
		result.setData(shopingCartDetailVOList);
		return result;
	}
	
	
}
