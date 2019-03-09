package cn.qumiandan.web.merchantServer.coupon.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.coupon.api.ICouponService;
import cn.qumiandan.coupon.api.ICouponTempleteService;
import cn.qumiandan.coupon.constant.CouponEnum;
import cn.qumiandan.coupon.vo.CouponTakeRecordVO;
import cn.qumiandan.coupon.vo.CouponTempleteVO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.coupon.vo.GetCouponResponseParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponTakeRecordParamsVO;
import cn.qumiandan.coupon.vo.QueryUsefulCouponParamsVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.merchantServer.coupon.entity.request.BackstageQueryCounponParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.CreateCouponParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.DeleteCouponByCouponIdParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.GetCouponByShopIdAndUserIdParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.GetCouponTakeRecordParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.GetPlatformCouponParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.IncreaseCouponNumParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.QueryCouponParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.QueryUsefulCouponParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.TakeCouponForUserParams;
import cn.qumiandan.web.merchantServer.coupon.entity.request.UpdateCouponProductParams;

@RestController
@RequestMapping("/coupon/")
public class CouponController {

	@Reference
	private ICouponTempleteService templeteService;
	
	@Reference
	private ICouponService couponService;
	
	/**
	 * 根据优惠券id查询优惠券
	 * @param id 店铺id
	 * @return
	 */
	@RequestMapping("queryCouponById")
	Result<CouponVO> queryCouponById(@Valid @RequestBody(required = false) QueryCouponParams params,
			BindingResult bindingResult){
		AssertUtil.isNull(params.getCouponId(), "优惠券id不能为空");
		CouponVO couponVO = couponService.queryCouponById(params.getCouponId());
		Result<CouponVO> result = ResultUtils.success();
		result.setData(couponVO);
		result.setSuccess(true);
		return result;
	}
	
	
	/**
	 * 店铺查询优惠券列表
	 * @param id 店铺id
	 * @return
	 */
	@RequestMapping("queryCouponForShopId")
	Result<List<CouponVO>> queryCouponForShop(@Valid @RequestBody(required = false) QueryCouponParams params,
			BindingResult bindingResult){
		AssertUtil.isNull(params.getShopId(), "店铺编号不能为空");
		List<CouponVO> queryCouponForShop = couponService.queryCouponForShop(params.getShopId());
		Result<List<CouponVO>> result = ResultUtils.success();
		result.setData(queryCouponForShop);
		result.setSuccess(true);
		return result;
	}
	
	
	@RequestMapping("createCouponForShop")
	public Result<CreateCouponParams> createCouponForShop(@Valid @RequestBody(required = false)CreateCouponParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		Result<CreateCouponParams> result = ResultUtils.success();
		params.setId(null);
		CouponVO vo = CopyBeanUtil.copyBean(params, CouponVO.class);
		vo = couponService.createCoupon(vo);
		params.setId(vo.getId());
		result.setData(params);
		return result;
	}
	
	/**
	 * 店铺获取可使用的优惠券模板
	 */
	@RequestMapping("getCouponTempleteForShop")
	public Result<List<CouponTempleteVO>> getCouponTempleteForShop() {
		List<CouponTempleteVO> templeteForShop = templeteService.getTempleteForShop();
		Result<List<CouponTempleteVO>> result = ResultUtils.success();
		result.setData(templeteForShop);
		return result;
	}
	
	/**
	 * 平台获取可使用的优惠券模板
	 */
	@RequestMapping("getCouponTempleteForAll")
	public Result<List<CouponTempleteVO>> getCouponTempleteForAll() {
		List<CouponTempleteVO> templeteForShop = templeteService.getTempleteForAll();
		Result<List<CouponTempleteVO>> result = ResultUtils.success();
		result.setData(templeteForShop);
		return result;
	}
	
	/**
	 * 修改优惠券数量
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("increaseCouponNum")
	public Result<CouponVO> increaseCouponNum(@Valid @RequestBody(required = false)IncreaseCouponNumParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 

		CouponVO vo = CopyBeanUtil.copyBean(params, CouponVO.class);
		vo = couponService.increaseCouponNum(params.getCouponId(),params.getNumber());
		Result<CouponVO> result = ResultUtils.success("增加优惠券数量成功");
		result.setData(vo);
		return result;
	}
	
	/**
	 * 修改优惠券排除的商品集合
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateCouponProduct")
	public Result<CouponVO> updateCouponProduct(@Valid @RequestBody(required = false)UpdateCouponProductParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		CouponVO vo = CopyBeanUtil.copyBean(params, CouponVO.class);
		vo = couponService.updateCouponProduct(params.getCouponId(),params.getExcludeProductIds());
		Result<CouponVO> result = ResultUtils.success("修改优惠券排除的商品集合成功");
		result.setData(vo);
		return result;
	}
	
	/**
	 * 删除优惠券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("deleteCouponByCouponId")
	public Result<CouponVO> deleteCouponByCouponId(@Valid @RequestBody(required = false)DeleteCouponByCouponIdParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		couponService.deleteCouponByCouponId(params.getCouponId(),params.getPublisher());
		return ResultUtils.success("删除优惠券成功");
	}
	
	/**
	 * 根据店铺id和用户id查询优惠券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getCouponByPublisherAndUserId")
	public Result<List<GetCouponResponseParamsVO>> getCouponByPublisherAndUserId(@Valid @RequestBody(required = false)GetCouponByShopIdAndUserIdParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<GetCouponResponseParamsVO> list = couponService.getCouponByShopIdAndUserId(params.getPublisher(),params.getUserId());
		Result<List<GetCouponResponseParamsVO>> result = ResultUtils.success();
		result.setData(list);
		return result;
	}
	
	/**
	 * 根据店铺id和用户id查询优惠券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getPlatformCoupon")
	public Result<List<GetCouponResponseParamsVO>> getPlatformCoupon(@Valid @RequestBody(required = false)GetPlatformCouponParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<GetCouponResponseParamsVO> list = couponService.getCouponByShopIdAndUserId(0L,params.getUserId());
		Result<List<GetCouponResponseParamsVO>> result = ResultUtils.success();
		result.setData(list);
		return result;
	}

	/**
	 * 优惠券领取
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("takeCouponForUser")
	public Result<CouponTakeRecordVO> takeCouponForUser(@Valid @RequestBody(required = false)TakeCouponForUserParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		CouponTakeRecordVO takeRecordVO = couponService.takeCouponForUser(params.getCouponId(),params.getUserId());
		Result<CouponTakeRecordVO> result = ResultUtils.success();
		result.setData(takeRecordVO);
		return result;
	}
	
	/**
	 * 根据条件查询领取记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getCouponTakeRecord")
	public Result<PageInfo<CouponTakeRecordVO>> getCouponTakeRecord(@Valid @RequestBody(required = false)GetCouponTakeRecordParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		QueryCouponTakeRecordParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryCouponTakeRecordParamsVO.class);
		PageInfo<CouponTakeRecordVO> takeRecordVOs = couponService.getCouponTakeRecord(paramsVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<CouponTakeRecordVO>> result = ResultUtils.success();
		result.setData(takeRecordVOs);
		return result;
	}
	
	/**
	 * 总后台查询优惠券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("backstageQueryCoupon")
	public Result<PageInfo<CouponVO>> backstageQueryCoupon(@Valid @RequestBody(required = false)BackstageQueryCounponParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		QueryCouponParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryCouponParamsVO.class);
		PageInfo<CouponVO> pageInfo = couponService.backstageQueryCoupon(paramsVO);
		Result<PageInfo<CouponVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
	}
	
	/**
	 * 查询可用优惠券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("queryUsefulCoupon")
	public Result<PageInfo<CouponVO>> queryUsefulCoupon(@Valid @RequestBody(required = false)QueryUsefulCouponParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		QueryUsefulCouponParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryUsefulCouponParamsVO.class);
		if(params.getShopId() != null ) {
			List<Long> publisherList = Lists.newArrayList(params.getShopId(),Long.parseLong(CouponEnum.All.getCode().toString()));
			paramsVO.setPublisherList(publisherList);
		}
		paramsVO.setDate(new Date());
		paramsVO.setUserId(ShiroUtils.getUserId());
		PageInfo<CouponVO> pageInfo = couponService.queryUsefulCoupon(paramsVO);
		Result<PageInfo<CouponVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
	}
	
	
}
