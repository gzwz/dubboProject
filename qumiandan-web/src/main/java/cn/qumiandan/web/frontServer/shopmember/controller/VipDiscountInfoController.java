package cn.qumiandan.web.frontServer.shopmember.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shopmember.api.IVipDiscountInfoService;
import cn.qumiandan.shopmember.vo.VipDiscountInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shopmember.entity.request.AddVipDiscountInfoParams;
import cn.qumiandan.web.frontServer.shopmember.entity.request.GetVipDiscountInfoByShopIdParams;
import cn.qumiandan.web.frontServer.shopmember.entity.request.SetVipDiscountInfoStatusParams;
import cn.qumiandan.web.frontServer.shopmember.entity.request.UpdateVipDiscountInfoParams;

/**
 * 会员折扣管理
 * @author lrj
 *
 */
@RestController
@RequestMapping("/vipDiscountInfo/")
public class VipDiscountInfoController {
	
	@Reference()
	private IVipDiscountInfoService vipDiscountInfoService;
	
	/**
	 * 设置会员折扣信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("setVipDiscountInfo")
	public Result<VipDiscountInfoVO> setVipDiscountInfo(@Valid @RequestBody(required = false)AddVipDiscountInfoParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		VipDiscountInfoVO  discountInfoVO = CopyBeanUtil.copyBean(params, VipDiscountInfoVO.class);
		discountInfoVO = vipDiscountInfoService.addVipDiscountInfo(discountInfoVO);
		Result<VipDiscountInfoVO> result = ResultUtils.success("设置店铺会员折扣信息成功");
		result.setData(discountInfoVO);
		return result;
	}
	
	/**
	 * 设置会员折扣启用/禁用
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("setVipDiscountInfoStatus")
	public Result<VipDiscountInfoVO> setVipDiscountInfoStatus(@Valid @RequestBody(required = false)SetVipDiscountInfoStatusParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		VipDiscountInfoVO discountInfoVO = vipDiscountInfoService.setVipDiscountInfoStatus(params.getShopId(),params.getStatus());
		Result<VipDiscountInfoVO> result = ResultUtils.success("设置会员折扣状态成功");
		result.setData(discountInfoVO);
		return result;
	}
	
	/**
	 * 根据店铺id查询会员折扣信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getVipDiscountInfoByShopId")
	public Result<VipDiscountInfoVO> getVipDiscountInfoByShopId(@Valid @RequestBody(required = false)GetVipDiscountInfoByShopIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		VipDiscountInfoVO discountInfoVO = vipDiscountInfoService.getVipDiscountInfoByShopId(params.getShopId());
		Result<VipDiscountInfoVO> result = ResultUtils.success();
		result.setData(discountInfoVO);
		return result;
	}
	
	/**
	 * 修改店铺会员折扣
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateVipDiscountInfo")
	public Result<VipDiscountInfoVO> updateVipDiscountInfo(@Valid @RequestBody(required = false)UpdateVipDiscountInfoParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		VipDiscountInfoVO discountInfoVO = CopyBeanUtil.copyBean(params, VipDiscountInfoVO.class);
		discountInfoVO = vipDiscountInfoService.updateVipDiscountInfo(discountInfoVO);
		Result<VipDiscountInfoVO> result = ResultUtils.success("修改店铺会员折扣信息成功");
		result.setData(discountInfoVO);
		return result;
	}
}
