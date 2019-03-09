package cn.qumiandan.web.frontServer.ratecode.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.ratecode.api.IRateCodeService;
import cn.qumiandan.ratecode.vo.RateCodeVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.ratecode.entity.request.AddRateCodeParams;
import cn.qumiandan.web.frontServer.ratecode.entity.request.DeleteRateCodeParams;
import cn.qumiandan.web.frontServer.ratecode.entity.request.GetAllRateCodeParams;
import cn.qumiandan.web.frontServer.ratecode.entity.request.GetCodeParams;
import cn.qumiandan.web.frontServer.ratecode.entity.request.UpdateRateCodeParams;

/**
 * 扫呗费率表管理
 * @author lrj
 * @version 创建时间：2018年12月4日 13:30
 */
@RestController
@RequestMapping("/rateCode/")
public class RateCodeController {

	
	@Reference()
	private IRateCodeService  rateCodeService;
	
	/**
	 * 根据code查询费率详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getRateCode")
	public Result<RateCodeVO> getRateCode(@Valid @RequestBody(required = false) GetCodeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		RateCodeVO rateCodeVO = rateCodeService.getRateCode(params.getCode());
		Result<RateCodeVO>	 result =ResultUtils.success();
		result.setData(rateCodeVO);
		return result;
	}
	
	/**
	 * 查询扫呗所有费率信息
	 * @return
	 */
	@RequestMapping("getAllRateCode")
	public Result<List<RateCodeVO>> getAllRateCode(@Valid @RequestBody(required = false) GetAllRateCodeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<RateCodeVO> rateCodeVOList = rateCodeService.getAllRateCode(params.getTypeList());
		Result<List<RateCodeVO>>	 result =ResultUtils.success();
		result.setData(rateCodeVOList);
		return result;
	}
	
	/**
	 * 添加费率
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addRateCode")
	public Result<Integer> addRateCode(@Valid @RequestBody(required = false) AddRateCodeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		RateCodeVO rateCodeVO = CopyBeanUtil.copyBean(params, RateCodeVO.class);
		rateCodeService.addRateCode(rateCodeVO);
		return ResultUtils.success("添加费率成功");
	}
	
	/**
	 * 修改费率
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateRateCode")
	public Result<Integer> updateRateCode(@Valid @RequestBody(required = false) UpdateRateCodeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		RateCodeVO rateCodeVO = CopyBeanUtil.copyBean(params, RateCodeVO.class);
		rateCodeService.updateRateCode(rateCodeVO);
		return ResultUtils.success("修改费率成功");
	}
	
	@RequestMapping("deleteRateCode")
	public Result<Integer> deleteRateCode(@Valid @RequestBody(required = false) DeleteRateCodeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		rateCodeService.deleteRateCode(params.getId());
		return ResultUtils.success("删除费率成功");
	}
}
