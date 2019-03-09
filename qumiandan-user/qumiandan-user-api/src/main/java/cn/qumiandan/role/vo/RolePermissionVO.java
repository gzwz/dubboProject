package cn.qumiandan.role.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class RolePermissionVO implements Serializable {


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
