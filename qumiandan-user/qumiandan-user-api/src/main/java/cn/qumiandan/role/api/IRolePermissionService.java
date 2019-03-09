package cn.qumiandan.role.api;

import java.util.List;
import java.util.Set;

import cn.qumiandan.role.vo.AddRolePermissionVO;
import cn.qumiandan.role.vo.AddRolePermissionVO2;
import cn.qumiandan.role.vo.BatchAddRolePermissionVO;
import cn.qumiandan.role.vo.RolePermissionVO;

/**
 * 角色菜单联合信息管理类
 * @author lrj
 *
 */
public interface IRolePermissionService {

	
	/**
	 * 添加角色-菜单联合信息
	 * @param addRolePermissionVO
	 * @return
	 */
	int addRolePermission(AddRolePermissionVO addRolePermissionVO);
	
	/**
	 * 修改角色—菜单联合信息
	 * @param addRolePermissionVO
	 * @return
	 */
	int updateRolePermission(AddRolePermissionVO addRolePermissionVO);
	
	/**
	 * 添加一条角色权限关联
	 * @param addRolePermissionVO2
	 * @return
	 */
	AddRolePermissionVO2 addOneRolePermission(AddRolePermissionVO2 addRolePermissionVO2);
	
	/**
	 * 根据角色id查询对应的权限列表
	 * @param addRolePermissionVO2
	 * @return
	 */
	List<RolePermissionVO> queryRolePermissionList(Long roleId);
	/**
	 * 根据角色id查询对应的权限列表
	 * @param addRolePermissionVO2
	 * @return
	 */
	void batchDeleteByRoleIdAndPermissIds(Long roleId, Set<Long> permissIds);

	/**
	 * 批量操作角色权限关联信息
	 * @param vo
	 */
	void batchOperateRolePermission(BatchAddRolePermissionVO vo);

}
