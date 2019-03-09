package cn.qumiandan.web.commonServer.maintain.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.maintain.api.IMaintainRecordService;
import cn.qumiandan.maintain.vo.MaintainRecordVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.commonServer.maintain.entity.AddMaintainRecordParams;
import cn.qumiandan.web.commonServer.maintain.entity.MaintainRecordIdParams;
import cn.qumiandan.web.commonServer.maintain.entity.MaintainTypeParams;
import cn.qumiandan.web.commonServer.maintain.entity.QueryMaintainRecordParams;
import cn.qumiandan.web.commonServer.maintain.entity.UpdateMaintainRecordParams;

/**
 * 维护记录controller
 * @author lrj
 * @version 创建时间：2019年01月21日 10:30
 */
@RestController
@RequestMapping("/maintainRecord/")
public class MaintainRecordController {
	@Reference()
	private IMaintainRecordService recordService;


	/**
	 * 添加维护记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	 @RequestMapping("addMaintainRecord")
	 public Result<MaintainRecordVO> addMaintainRecord(@Valid
	 @RequestBody(required = false)AddMaintainRecordParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 MaintainRecordVO recordVO = CopyBeanUtil.copyBean(params, MaintainRecordVO.class);
	 MaintainRecordVO addMaintainRecord = recordService.addMaintainRecord(recordVO);
	 Result<MaintainRecordVO> result = ResultUtils.success("添加维护记录成功");
	 result.setData(addMaintainRecord);
	 return result;
	 }
	 
	 /**
	  * 修改维护记录
	  * @param params
	  * @param bindingResult
	  * @return
	  */
	 @RequestMapping("updateMaintainRecord")
	 public Result<MaintainRecordVO> updateMaintainRecord(@Valid
	 @RequestBody(required = false)UpdateMaintainRecordParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 MaintainRecordVO recordVO = CopyBeanUtil.copyBean(params, MaintainRecordVO.class);
	 recordService.updateMaintainRecord(recordVO);
	 return ResultUtils.success("修改维护记录成功");
	 }
	 
	 /**
	  * 删除维护记录
	  * @param params
	  * @param bindingResult
	  * @return
	  */
	 @RequestMapping("deleteMaintainRecord")
	 public Result<MaintainRecordVO> deleteMaintainRecord(@Valid
	 @RequestBody(required = false)MaintainRecordIdParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 recordService.deleteMaintainRecord(params.getMaintainRecordId());
	 return ResultUtils.success("删除维护记录成功");
	 }
	 
	 
	 /**
	  * 根据id查询维护记录信息
	  * @param params
	  * @param bindingResult
	  * @return
	  */
	 @RequestMapping("getMaintainRecordById")
	 public Result<MaintainRecordVO> getMaintainRecordById(@Valid
	 @RequestBody(required = false)MaintainRecordIdParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 MaintainRecordVO maintainRecordById = recordService.getMaintainRecordById(params.getMaintainRecordId());
	 Result<MaintainRecordVO> result = ResultUtils.success();
	 result.setData(maintainRecordById);
	 return result;
	 }
	 
	 /**
	  * 总后台查询维护信息
	  * @param params
	  * @param bindingResult
	  * @return
	  */
	 @RequestMapping("queryMaintainRecord")
	 public Result<PageInfo<MaintainRecordVO>> queryMaintainRecord(@Valid
	 @RequestBody(required = false)QueryMaintainRecordParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 PageInfo<MaintainRecordVO> queryMaintainRecord = recordService.queryMaintainRecord(params.getMaintainTypeId(), params.getPageNum(), params.getPageSize());
	 Result<PageInfo<MaintainRecordVO>> result = ResultUtils.success();
	 result.setData(queryMaintainRecord);
	 return result;
	 }
	 
	 /**
	  * 根据维护类型查询维护信息
	  * @param params
	  * @param bindingResult
	  * @return
	  */
	 @RequestMapping("getMaintainRecordByType")
	 public Result<MaintainRecordVO> getMaintainRecordByType(@Valid
	 @RequestBody(required = false)MaintainTypeParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 MaintainRecordVO maintainRecordByType = recordService.getMaintainRecordByType(params.getType());
	 Result<MaintainRecordVO> result = ResultUtils.success();
	 result.setData(maintainRecordByType);
	 return result;
	 }
}
