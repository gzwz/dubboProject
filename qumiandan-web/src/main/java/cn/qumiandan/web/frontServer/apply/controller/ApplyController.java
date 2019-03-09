package cn.qumiandan.web.frontServer.apply.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.apply.api.IApplyService;
import cn.qumiandan.apply.vo.ApplyVO;
import cn.qumiandan.apply.vo.QueryApplyVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.apply.entity.request.AddApplyParams;
import cn.qumiandan.web.frontServer.apply.entity.request.DealByIdParams;
import cn.qumiandan.web.frontServer.apply.entity.request.QueryApplyParams;
import cn.qumiandan.web.frontServer.apply.entity.request.QueryByIdParams;

@RestController
@RequestMapping("/apply/")
public class ApplyController {
	
	@Reference
	private IApplyService applyService;
	
	
	/**
	 * 申请开店
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addApply")
	 public Result<ApplyVO> addApply(@Valid @RequestBody(required = false) AddApplyParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ApplyVO ApplyVO = CopyBeanUtil.copyBean(params, ApplyVO.class);
		ApplyVO = applyService.addApply(ApplyVO);
		Result<ApplyVO> result = ResultUtils.success("申请开店成功");
		result.setData(ApplyVO);
		return result;
	 }
	
	
	/**
	 * 处理开店申请
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("dealApplyById")
	 public Result<ApplyVO> dealApplyById(@Valid @RequestBody(required = false) DealByIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ApplyVO ApplyVO = CopyBeanUtil.copyBean(params, ApplyVO.class);
		ApplyVO = applyService.dealApplyById(params.getId(),params.getStatus());
		Result<ApplyVO> result = ResultUtils.success();
		switch (params.getStatus()) {
		case 1:
			result.setMessage("处理开店申请成功；处理结果：处理完成");
			break;
		case 2:
			result.setMessage("处理开店申请成功；处理结果：拒绝处理");
			break;
		default:
			return ResultUtils.error("处理结果参数错误");
		}
		result.setData(ApplyVO);
		return result;
	 }
	
	/**
	 * 根据主键查询开店申请
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("queryApplyById")
	 public Result<ApplyVO> queryApplyById(@Valid @RequestBody(required = false) QueryByIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ApplyVO ApplyVO = CopyBeanUtil.copyBean(params, ApplyVO.class);
		ApplyVO = applyService.queryApplyById(params.getId());
		return ResultUtils.success(ApplyVO);
	 }
	
	/**
	 * 总后台查询开店申请
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("queryApply")
	 public Result<PageInfo<ApplyVO>> queryApply(@Valid @RequestBody(required = false) QueryApplyParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		QueryApplyVO queryApplyVO = CopyBeanUtil.copyBean(params, QueryApplyVO.class);
		PageInfo<ApplyVO> queryApplyOpenShop = applyService.queryApply(queryApplyVO);
		return ResultUtils.success(queryApplyOpenShop);
	 }
	
}
