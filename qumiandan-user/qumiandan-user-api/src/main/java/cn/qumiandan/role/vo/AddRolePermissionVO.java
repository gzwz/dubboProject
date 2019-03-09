package cn.qumiandan.role.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
public class AddRolePermissionVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 角色Id
	 */
	private Long roleId;
	
	/**
	 * 菜单id集合
	 */
	private List<Long> permissionIds;
}
