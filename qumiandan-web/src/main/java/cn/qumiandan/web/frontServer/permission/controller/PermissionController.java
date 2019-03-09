package cn.qumiandan.web.frontServer.permission.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.permission.api.IPermissionService;
import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.permission.entity.request.AddPermissionParams;
import cn.qumiandan.web.frontServer.permission.entity.request.QueryPermissionParams;
import cn.qumiandan.web.frontServer.permission.entity.request.UpdatePermissionParams;

/**
 * 菜单管理
 * @author lrj
 *	创建时间：2018-11-20 10:30
 */

@RestController
@RequestMapping("/permission/")
public class PermissionController {
	@Reference()
	private IPermissionService  permissionService;
	
	/**
	 * 添加菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addPermission")
	public Result<Integer> addPermission(@Valid @RequestBody(required = false) AddPermissionParams params,  BindingResult bindingResult) throws Exception  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		PermissionVO permissionVO = new PermissionVO();
		BeanUtils.copyProperties(permissionVO, params);
		//postMan传过来pId无法接收
		permissionVO.setPId(params.getParentId());
		permissionService.addPermission(permissionVO);
		return ResultUtils.success("添加菜单成功");
	}
	
	
	/**
	 * 更新菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updatePermission")
	public Result<Integer> updatePermission(@Valid @RequestBody(required = false) UpdatePermissionParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		PermissionVO permissionVO = new PermissionVO();
		BeanUtils.copyProperties(permissionVO, params);
		//postMan传过来pId无法接收
		permissionVO.setPId(params.getParentId());
		permissionService.updatePermission(permissionVO);	
		return ResultUtils.success("更新菜单成功");
	}
	

	/**
	 * 查询所有菜单
	 * @return
	 */
	@RequestMapping("getAllPermission")
	public Result<List<PermissionVO>> getAllPermission(@Valid @RequestBody(required = false) 
	QueryPermissionParams params, BindingResult bindingResult){
		List<PermissionVO> permissionVOList = permissionService.getAllPermission(params.getType());
		Result<List<PermissionVO>> result = ResultUtils.success();
		result.setData(permissionVOList);
		return result;
	}
}
