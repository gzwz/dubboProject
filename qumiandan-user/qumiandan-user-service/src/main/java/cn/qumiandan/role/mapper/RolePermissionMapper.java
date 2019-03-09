package cn.qumiandan.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.role.entity.RolePermission;
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int deleteByPrimaryKey(String id);
    
    /**
     * 根据角色id删除角色-菜单联合表信息
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);
    
    /**
     * 根据角色Id、菜单id查询角色-菜单联合表信息
     * @param permissionId
     * @param roleId
     * @return
     */
    int getInfoByPerIdAndRoleId(@Param("permissionId") Long permissionId ,@Param("roleId") Long roleId);
}