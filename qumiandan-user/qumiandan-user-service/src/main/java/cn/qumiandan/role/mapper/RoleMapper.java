package cn.qumiandan.role.mapper;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.role.entity.Role;
import cn.qumiandan.role.vo.RoleVO;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

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
     * @param record
     * @return
     */
    int insertSelective(Role record);

    /**
     * 修改角色信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * 根据角色id查询菜单信息
     * @param roleId
     * @return
     */
	List<PermissionVO> getPermissionByRoleId(Long roleId);
	
	/**
	 * 根据角色名查询角色信息
	 * @param name
	 * @return
	 */
	Role getRoleByName(String name);
	
	/**
	 * 根据用户名查询用户所有菜单
	 * @param userName
	 * @return
	 */
	List<PermissionVO> getPermissionByUserName(@Param ("userName")String userName,@Param ("type")Byte type );
    
	/**
	 * 获取sort最大值
	 * @return
	 */
	Integer getMaxSort();
	
	/**
	 * 根据角色编号组获取角色信息
	 * @param roldIds
	 * @return
	 */
	public List<RoleVO> getRolesByRoleIds(@Param("roleIds") String[] roleIds);
	
	/**
	 * 获取店铺管理员角色信息
	 */
	RoleVO getShopManagerRoleInfo();
	
	
	/**
	 * 根据用户id查询用户角色列表
	 * @param userId
	 * @return
	 */
	List<RoleVO> getRoleByUserId(Long userId);
}