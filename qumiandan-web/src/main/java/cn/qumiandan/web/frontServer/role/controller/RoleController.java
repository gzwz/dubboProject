package cn.qumiandan.web.frontServer.role.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.role.entity.request.AddRoleParams;
import cn.qumiandan.web.frontServer.role.entity.request.GetAllRoleParams;
import cn.qumiandan.web.frontServer.role.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.role.entity.request.GetTypeParams;
import cn.qumiandan.web.frontServer.role.entity.request.UpdateRoleParams;

/**
 * 角色管理
 * @author lrj
 * @version 创建时间：2018年11月20日 10:30
 */
@RestController
@RequestMapping("/role/")
public class RoleController {
	@Reference()
	private IRoleService roleService;
	
	/**
	 * 添加角色
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addRole")
	public Result<Integer> addRole(@Valid @RequestBody(required = false) AddRoleParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		RoleVO roleVO=new RoleVO();
		BeanUtils.copyProperties(roleVO, params);
		roleService.addRole(roleVO);	
		return ResultUtils.success("添加角色成功");
	}
	
	/**
	 * 更新角色信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateRole")
	public Result<Integer> updateRole(@Valid @RequestBody(required = false) UpdateRoleParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		RoleVO roleVO=new RoleVO();
		BeanUtils.copyProperties(roleVO, params);
		roleService.updateRole(roleVO);	
		return ResultUtils.success("更新角色成功");
	}
	
	
	/**
	 * 删除角色信息（逻辑删除）
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("deleteRole")
	public Result<Integer> deleteRole(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		roleService.deleteRole(params.getId());	
		return ResultUtils.success("删除角色成功");
	}
		
	/**
	 * 根据角色id查询所有菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getPermissionByRoleId")
	public Result<List<PermissionVO>> getPermissionByRoleId(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<PermissionVO> permissionList=roleService.getPermissionByRoleId(params.getId());
		Result<List<PermissionVO>>	 result =ResultUtils.success();
		result.setData(permissionList);
		return result;
	}
	
	/**
	 * 用户查询自己的菜单
	 * @return
	 */
	@RequestMapping("getAllSelfPermission")
	public Result<List<PermissionVO>> getAllSelfPermission(@Valid @RequestBody(required = false) GetTypeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<PermissionVO> permissionList=roleService.getPermissionByUserName(ShiroUtils.getCurrentUser().getUserName(),params.getType());
		Result<List<PermissionVO>>	result =ResultUtils.success();
		result.setData(permissionList);
		return result;
	}
	
	/**
	 * 根据用户id查询角色列表
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getRoleByUserId")
	public Result<List<RoleVO>> getRoleByUserId(@Valid @RequestBody(required = false) GetTypeParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<RoleVO> roleByUserId = roleService.getRoleByUserId(ShiroUtils.getUserId());
		return ResultUtils.success(roleByUserId);
	}
	
	/**
	 * 查询所有角色
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAllRole")
	public Result<PageInfo<RoleVO>> getAllRole(@Valid @RequestBody(required = false) GetAllRoleParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		if (Objects.isNull(params)) {
			params = new GetAllRoleParams();
		}
		PageInfo<RoleVO> queryPageRole = roleService.queryPageRole(params.getRoleName(), params.getPageNum(), params.getPageSize());
		return ResultUtils.success(queryPageRole);
	}
	
}
