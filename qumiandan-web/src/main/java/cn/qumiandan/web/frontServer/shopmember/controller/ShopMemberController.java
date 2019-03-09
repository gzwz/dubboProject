package cn.qumiandan.web.frontServer.shopmember.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shopmember.api.IShopMemberService;
import cn.qumiandan.shopmember.vo.ShopMemberVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.shopmember.entity.request.IsShopMemberParams;

@RestController
@RequestMapping("/shopMember/")
public class ShopMemberController {
	
	@Reference
	private IShopMemberService shopMemberService;
	
	/**
	 * 购买店铺会员 shopMemberService.purchaseShopMember(purchaseShopMemberVO);
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	/*
	 * @RequestMapping("purchaseShopMember") public Result<Integer>
	 * purchaseShopMember(@Valid @RequestBody(required =
	 * false)PurchaseShopMemberParams params, BindingResult bindingResult) { if
	 * (bindingResult.hasErrors()) { return ResultUtils.paramsError(bindingResult);
	 * } PurchaseShopMemberVO purchaseShopMemberVO = CopyBeanUtil.copyBean(params,
	 * PurchaseShopMemberVO.class);
	 * shopMemberService.purchaseShopMember(purchaseShopMemberVO); return
	 * ResultUtils.success("购买会员成功"); }
	 */
	
	
	/**
	 * 查询用户是否是店铺会员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("isShopMember")
	public Result<ShopMemberVO> isShopMember(@Valid @RequestBody(required = false)IsShopMemberParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopMemberVO shopMemberVO = shopMemberService.isShopMember(params.getShopId(), ShiroUtils.getUserId());
		Result<ShopMemberVO> result = ResultUtils.success();
		result.setData(shopMemberVO);
		return result;
	}
	
	
//	getShopMemberDiscount
	@RequestMapping("getShopMemberDiscount")
	public Result<BigDecimal> getShopMemberDiscount(@Valid @RequestBody(required = false)IsShopMemberParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		BigDecimal discount = shopMemberService.getShopMemberDiscount(params.getShopId(), ShiroUtils.getUserId());
		if(discount == null) {
			discount = new BigDecimal(1000);
		}
		Result<BigDecimal> result = ResultUtils.success();
		result.setData(discount);
		return result;
	}
}
