package cn.qumiandan.role.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "sys_role_permission")
public class RolePermission  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	 
	/**
	 * 菜单ID
	 */
	private Long permissionId;

}
