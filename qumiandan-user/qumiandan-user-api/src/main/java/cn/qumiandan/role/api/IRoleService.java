package cn.qumiandan.role.api;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.role.vo.RoleVO;

public interface IRoleService {
	
	/**
	 * 根据用户名查询用户角色
	 * @param userName
	 * @return
	 */
	List<RoleVO> getRoleByUserName(String userName);

    /**
     * 根据用id查询用户所有角色
     * @param userId
     * @return
     */
    List<RoleVO> getRoleListByUserId(Long userId);

    /**
     * 根据角色id查询该角色下的所有菜单
     * @param userId
     * @return
     */
    List<PermissionVO> getPermissionListByUserId(Long userId);

    /**
     * 根据用户id查询他的所有的角色别名
     * @param userId
     * @return
     */
    Set<String> getRoleEaliasSetByUserId(Long userId);

    /**
     * 根据用户id查询他的所有菜单
     * @param userId
     * @return
     */
    Set<String> getPermissionSetByUserId(Long userId);
    
    /**
     * 添加角色信息
     * @param roleVO
     * @return
     */
    int addRole(RoleVO roleVO);
    
    /**
     * 修改角色信息
     * @param roleVO
     * @return
     */
    int updateRole(RoleVO roleVO);
    
    /**
     * 删除角色信息（逻辑删除）
     * @param id
     * @return
     */
    int deleteRole(Long id);
    
    /**
     * 根据角色id查询菜单信息
     * @param roleId
     * @return
     */
    List<PermissionVO> getPermissionByRoleId(Long roleId);
    
    /**
     * 根据用户名查询用户所有菜单
     * @param userName
     * @param type
     * @return
     */
	List<PermissionVO> getPermissionByUserName(String userName, Byte type);
	
	
	/**
	 * 获取平台
	 * @return
	 */
	List<RoleVO> getPlatformOpenRolesList();
	
	/**
	 * 获取店铺管理员角色
	 * @return
	 */
	RoleVO getShopManagerRoleInfo();
	
	/**
	 * 根据角色名查角色
	 * @param name
	 * @return
	 */
	RoleVO getRoleByRoleName(String name);
	
	/**
	 * 根据用户id查询用户角色列表
	 * @param userId
	 * @return
	 */
	List<RoleVO> getRoleByUserId(Long userId);
	
	/**
	 * 总后台查询角色列表
	 * @param roleName
	 * @return
	 */
	List<RoleVO> getAllRole(String roleName);
	
	/**
	 * 分页查询所有行业
	 * @param roleName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<RoleVO> queryPageRole(String roleName , Integer pageNum, Integer pageSize);
}
