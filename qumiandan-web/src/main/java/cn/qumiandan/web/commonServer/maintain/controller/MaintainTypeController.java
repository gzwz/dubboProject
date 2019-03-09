package cn.qumiandan.web.commonServer.maintain.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.maintain.api.IMaintainTypeService;
import cn.qumiandan.maintain.vo.MaintainTypeVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.commonServer.maintain.entity.GetAllMaintainTypeParams;

/**
 * 维护类型controller
 * 
 * @author lrj
 * @version 创建时间：2019年01月21日 10:30
 */
@RestController
@RequestMapping("/maintainType/")
public class MaintainTypeController {

	@Reference()
	private IMaintainTypeService maintainTypeService;

	/**
	 * 查询所有维护类型
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	 @RequestMapping("getAllMaintainType")
	 public Result<List<MaintainTypeVO>> getAllMaintainType(@Valid
	 @RequestBody(required = false)GetAllMaintainTypeParams params,BindingResult
	 bindingResult) {
	 if (bindingResult.hasErrors()) {
	 return ResultUtils.paramsError(bindingResult);
	 }
	 List<MaintainTypeVO> allMaintainType = maintainTypeService.getAllMaintainType(params.getModuleType());
	 Result<List<MaintainTypeVO>> result = ResultUtils.success();
	 result.setData(allMaintainType);
	 return result;
	 }
}
