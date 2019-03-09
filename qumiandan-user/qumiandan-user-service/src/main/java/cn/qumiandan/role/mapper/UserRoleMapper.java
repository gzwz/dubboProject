package cn.qumiandan.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.role.vo.UserRoleVO;
import cn.qumiandan.user.entity.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole>{
	/**
     * 改变用户角色状态为0(sys_user_role)
     * @param userId
     * @return
     */
    int updateUserRoleByUserId(Long userId);
    
    /**
     * 添加sys_user_role用户角色信息
     * @param userRole
     * @return
     */
    int  addUserRole(UserRole userRole );
    
	/**
	 * 根据用户编号和角色编号查询 关联信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
    UserRoleVO getUserRoleByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId")Long roleId);
    
    
    /**
     * 根据id 修改角色
     * @param id
     * @param roleId
     * @return
     */
    Integer updateUserRoleByIdAndRoleId(@Param("id") Long id, @Param("roleId") Long roleId);
}
