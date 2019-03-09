package cn.qumiandan.web.frontServer.role.controller;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.role.api.IRolePermissionService;
import cn.qumiandan.role.vo.AddRolePermissionVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.role.entity.request.AddRolePermissionParams;

/**
 * 角色管理
 * @author lrj
 * @version 创建时间：2018年11月20日 10:30
 */

@RestController
@RequestMapping("/rolePermission/")
public class RolePermissionController {
	@Reference()
	private IRolePermissionService permissionService;
	
//	addRolePermission
	/**
	 * 添加角色菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addRolePermission")
	public Result<Integer> addRolePermission(@Valid @RequestBody(required = false) AddRolePermissionParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		AddRolePermissionVO addRolePermissionVO = new AddRolePermissionVO();
		BeanUtils.copyProperties(addRolePermissionVO, params);
		permissionService.addRolePermission(addRolePermissionVO);	
		return ResultUtils.success("添加角色菜单成功");
	}
	
	/**
	 * 更新角色菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateRolePermission")
	public Result<Integer> updateRolePermission(@Valid @RequestBody(required = false) AddRolePermissionParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AddRolePermissionVO addRolePermissionVO = new AddRolePermissionVO();
		BeanUtils.copyProperties(addRolePermissionVO, params);
		permissionService.updateRolePermission(addRolePermissionVO);	
		return ResultUtils.success("更新角色菜单成功");
	}
	
}
