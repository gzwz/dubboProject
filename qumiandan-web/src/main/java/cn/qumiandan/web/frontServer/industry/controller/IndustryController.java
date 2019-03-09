package cn.qumiandan.web.frontServer.industry.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.industry.api.IIndustryService;
import cn.qumiandan.industry.vo.GetAllIndustryVO;
import cn.qumiandan.industry.vo.IndustryVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.industry.entity.request.AddIndustryParams;
import cn.qumiandan.web.frontServer.industry.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.industry.entity.request.GetParentIdParams;
import cn.qumiandan.web.frontServer.industry.entity.request.UpdateIndustryParams;

/**
 * 行业管理
 * @author lrj
 * @version 创建时间：2018年11月20日 10:30
 */
@RestController
@RequestMapping("/industry/")
public class IndustryController {
	@Reference()
	private IIndustryService  industryService;

	/**
	 * 添加行业信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addIndustry")
	public Result<Integer> addIndustry(@Valid @RequestBody(required = false) AddIndustryParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		IndustryVO industryVO = new IndustryVO();
		BeanUtils.copyProperties(industryVO, params);
		industryService.addIndustry(industryVO);	
		return ResultUtils.success("添加行业信息成功");
	}
	
	
	/**
	 * 更新行业信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateIndustry")
	public Result<Integer> updateIndustry(@Valid @RequestBody(required = false) UpdateIndustryParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		IndustryVO industryVO = new IndustryVO();
		BeanUtils.copyProperties(industryVO, params);
		industryService.updateIndustry(industryVO);		
		return ResultUtils.success("修改行业信息成功");
	}
	
	/**
	 * 删除行业信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException 
	 */
	@RequestMapping("deleteIndustry")
	public Result<Integer> deleteIndustry(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		industryService.deleteIndustry(params.getId());	
		return ResultUtils.success("删除行业信息成功");
	}
	
	/**
	 * 查询所有行业信息
	 * @return
	 */
	@RequestMapping("getAllIndustry")
	public Result<List<GetAllIndustryVO>> getAllIndustry() {
		List<GetAllIndustryVO> list = industryService.getAllIndustry();
		Result<List<GetAllIndustryVO>> result=ResultUtils.success();
		result.setData(list);
		return result;
	}
	
		
	/**
	 * 根据主键查询行业信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("getIndustryById")
	public Result<IndustryVO> getIndustryById(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		IndustryVO industryVO=industryService.getIndustryVOById(params.getId());
		Result<IndustryVO> result=ResultUtils.success();
		result.setData(industryVO);
		return result;
	}
	
	/**
	 * 根据父级id查询行业信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("getIndustryByLevelAndParentId")
	public Result<List<IndustryVO>> getIndustryByLevelAndParentId(@Valid @RequestBody(required = false) GetParentIdParams params,  BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<IndustryVO> list=industryService.getIndustryByLevelAndParentId(params.getParentId());
		Result<List<IndustryVO>> result=ResultUtils.success();
		result.setData(list);
		return result;
	}
	
}
