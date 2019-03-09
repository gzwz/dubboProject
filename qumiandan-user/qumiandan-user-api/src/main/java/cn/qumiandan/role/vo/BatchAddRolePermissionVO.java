package cn.qumiandan.role.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 批量添加用户角色关联
 * @author yuleidian
 * @date 2019年1月16日
 */
@Data
public class BatchAddRolePermissionVO implements Serializable {

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
