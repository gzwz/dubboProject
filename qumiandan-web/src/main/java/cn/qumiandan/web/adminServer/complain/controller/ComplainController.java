package cn.qumiandan.web.adminServer.complain.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.complain.api.IComplainService;
import cn.qumiandan.complain.api.IComplainTypeService;
import cn.qumiandan.complain.vo.ComplainTypeVO;
import cn.qumiandan.complain.vo.ComplainVO;
import cn.qumiandan.complain.vo.QueryComplainVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.adminServer.complain.entity.request.ComplainParams;
import cn.qumiandan.web.adminServer.complain.entity.request.QueryComplainParams;

@RestController
@RequestMapping("/complain/")
public class ComplainController {
	@Reference
	private IComplainService complainService;
	@Reference
	private IComplainTypeService complainTypeService;
	
	
	/**
	 * 查询所有举报类型
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAllComplain")
	public Result<List<ComplainTypeVO>> getAllComplain() {
		List<ComplainTypeVO> allComplain = complainTypeService.getAllComplain();
		Result<List<ComplainTypeVO>> result = ResultUtils.success();
		result.setData(allComplain);
		return result ;
	}
	
	/**
	 * 普通人员举报
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addComplain")
	public Result<Void> addComplain(@Valid @RequestBody(required = false)
	ComplainParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ComplainVO complainVO = CopyBeanUtil.copyBean(params, ComplainVO.class);
		complainService.addComplain(complainVO);
		Result<Void> result = ResultUtils.success();
		return result ;
	}
	
	/**
	 * 根据id查询举报信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getComplainById")
	public Result<ComplainVO> getComplainById(@Valid @RequestBody(required = false)
	ComplainParams params, BindingResult bindingResult) {
		if (params == null || params.getId() == null) {
			throw new QmdException("查询id不能为空"); 
		}
		ComplainVO complainVO = complainService.getComplainById(params.getId());
		Result<ComplainVO> result = ResultUtils.success();
		result.setData(complainVO);
		return result ;
	}
	
	/**
	 * 根据id查询举报信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("queryComplainPage")
	public Result<PageInfo<ComplainVO>> queryComplainPage(@Valid @RequestBody(required = false)
	QueryComplainParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		QueryComplainVO paramsVO = CopyBeanUtil.copyBean(params, QueryComplainVO.class);
		PageInfo<ComplainVO> queryComplain = complainService.queryComplain(paramsVO);
		Result<PageInfo<ComplainVO>> result = ResultUtils.success();
		result.setData(queryComplain);
		return result ;
	}
}
