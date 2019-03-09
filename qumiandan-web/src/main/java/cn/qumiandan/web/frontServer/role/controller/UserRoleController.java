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

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.permission.api.IPermissionService;
import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.role.api.IRolePermissionService;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.api.IUserRoleService;
import cn.qumiandan.role.vo.BatchAddRolePermissionVO;
import cn.qumiandan.role.vo.BatchAddUserRoleVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserUpdateVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.role.entity.request.AddUserRoleParams;
import cn.qumiandan.web.frontServer.role.entity.request.BatchOperateRolePermissionParams;
import cn.qumiandan.web.frontServer.role.entity.request.BatchOperateUserRoleParams;
import cn.qumiandan.web.frontServer.role.entity.request.GetCurrentRolePermAndAllPerParams;
import cn.qumiandan.web.frontServer.role.entity.request.GetCurrentUserRoleAndAllRoleParams;
import cn.qumiandan.web.frontServer.role.entity.response.GetCurrentRolePermissionAndAllPermissionResult;
import cn.qumiandan.web.frontServer.role.entity.response.GetCurrentUserRoleAndAllRoleResult;

/**
 * 用户角色管理
 * @author lrj
 * @version 创建时间：2018年11月23日 14:30
 */

@RestController
@RequestMapping("/userRole/")
public class UserRoleController {

	@Reference()
	private IUserService userService;
	
	@Reference
	private IUserRoleService userRoleService;
	
	@Reference
	private IRoleService roleService;
	
	@Reference
	private IPermissionService permissionService;
	
	@Reference
	private IRolePermissionService rolePermissionService;
	/**
	 * 给用户添加角色
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addUserRole")
	public Result<Integer> addUserRole(@Valid @RequestBody(required = false) AddUserRoleParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UserUpdateVO userUpdateVO=new UserUpdateVO();
		BeanUtils.copyProperties(userUpdateVO, params);
		userService.updateUserRolesByUserId(userUpdateVO);
		return ResultUtils.success();
	}
	
	/**
	 * 根据用户查询持有的角色和 所有角色信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllRoleAndUserRoleInfo")
	public Result<GetCurrentUserRoleAndAllRoleResult> getAllRoleAndUserRoleInfo(@Valid @RequestBody(required = false) GetCurrentUserRoleAndAllRoleParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		GetCurrentUserRoleAndAllRoleResult resultData = new GetCurrentUserRoleAndAllRoleResult();
		resultData.setAllRoles(roleService.getAllRole(null));
		resultData.setUserRoles(userRoleService.getUserRolesByUserId(params.getUserId()));
		return ResultUtils.success(resultData);
	}
	
	/**
	 * 根据用户批量操作该用户的所有角色
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchOperateUserRole")
	public Result<GetCurrentUserRoleAndAllRoleResult> batchOperateUserRole(@Valid @RequestBody(required = false) BatchOperateUserRoleParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if (Objects.nonNull(params)) {
			userRoleService.batchOperateUserRole(CopyBeanUtil.copyBean(params, BatchAddUserRoleVO.class));
		}
		return ResultUtils.success();
	}
	
 //  ----------------------------------	
	
	/**
	 * 根据角色查询所有权限信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllRolePermissionInfo")
	public Result<GetCurrentRolePermissionAndAllPermissionResult> getAllRolePermissionInfo(@Valid @RequestBody(required = false) 
	GetCurrentRolePermAndAllPerParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		GetCurrentRolePermissionAndAllPermissionResult resultData = new GetCurrentRolePermissionAndAllPermissionResult();
		List<PermissionVO> allPermission = permissionService.getAllPermission(null);
		resultData.setAllPermission(allPermission);
		List<PermissionVO> permission = roleService.getPermissionByRoleId(params.getRoleId());
		resultData.setRolesPermission(permission);
		return ResultUtils.success(resultData);
	}
	
	/**
	 * 根据角色批量操作该角色的所有菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchOperateRolePermission")
	public Result<GetCurrentUserRoleAndAllRoleResult> batchOperateRolePermission(@Valid @RequestBody(required = false) 
	BatchOperateRolePermissionParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if (Objects.nonNull(params)) {
			BatchAddRolePermissionVO vo = CopyBeanUtil.copyBean(params, BatchAddRolePermissionVO.class);
			rolePermissionService.batchOperateRolePermission(vo);
		}
		return ResultUtils.success();
	}
}
