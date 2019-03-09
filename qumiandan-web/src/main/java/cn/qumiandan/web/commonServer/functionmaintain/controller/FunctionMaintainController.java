package cn.qumiandan.web.commonServer.functionmaintain.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能维护管理controller
 * @author lrj
 * @version 创建时间：2019年01月09日 18:00
 */
@RestController
@RequestMapping("/functionMaintain/")
public class FunctionMaintainController {

	/*@Reference()
	private IMaintainRecordService maintainService; 
	
	*//**
	 * 添加维护功能信息
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("addFunctionMaintain")
	 public Result<FunctionMaintainVO> addFunctionMaintain(@Valid @RequestBody(required = false) AddFunctionMaintainparams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		FunctionMaintainVO functionMaintainVO = CopyBeanUtil.copyBean(params, FunctionMaintainVO.class);
		functionMaintainVO = maintainService.addFunctionMaintain(functionMaintainVO);
		Result<FunctionMaintainVO> result = ResultUtils.success("添加维护功能成功");
		result.setData(functionMaintainVO);
		return result;
	 }
	
	*//**
	 * 修改维护功能
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("updateFunctionMaintain")
	 public Result<Void> updateFunctionMaintain(@Valid @RequestBody(required = false) UpdateFunctionMaintainParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		FunctionMaintainVO functionMaintainVO = CopyBeanUtil.copyBean(params, FunctionMaintainVO.class);
		maintainService.updateFunctionMaintain(functionMaintainVO);
		return ResultUtils.success("修改维护功能成功");
	 }
	
	*//**
	 * 删除维护功能
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("deleteFunctionMaintain")
	 public Result<Void> deleteFunctionMaintain(@Valid @RequestBody(required = false) GetFunctionMaintainIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		maintainService.deleteFunctionMaintain(params.getId());
		return ResultUtils.success("删除维护功能成功");
	 }
	
	*//**
	 * 根据主键查询维护功能
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("getFunctionMaintainById")
	 public Result<FunctionMaintainVO> getFunctionMaintainById(@Valid @RequestBody(required = false) GetFunctionMaintainIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		FunctionMaintainVO functionMaintainById = maintainService.getFunctionMaintainById(params.getId());
		Result<FunctionMaintainVO> result = ResultUtils.success();
		result.setData(functionMaintainById);
		return result;
	 }
	
	*//**
	 * 根据条件查询维护功能
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("queryFunctionMaintain")
	 public Result<List<FunctionMaintainVO>> queryFunctionMaintain(@Valid @RequestBody(required = false) QueryFunctionMaintainParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		FunctionMaintainVO functionMaintainVO = CopyBeanUtil.copyBean(params, FunctionMaintainVO.class);
		List<FunctionMaintainVO> queryFunctionMaintain = maintainService.queryFunctionMaintain(functionMaintainVO);
		Result<List<FunctionMaintainVO>> result = ResultUtils.success();
		result.setData(queryFunctionMaintain);
		return result;
	 }*/
}
