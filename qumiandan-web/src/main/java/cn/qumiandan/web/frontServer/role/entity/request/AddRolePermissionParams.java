package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 角色菜单联合表参数
 * @author lrj
 *
 */
@Data
public class AddRolePermissionParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 角色Id
	 */
	@NotNull(message="角色id不能为空")
	private Long roleId;
	
	/**
	 * 菜单id集合
	 */
	@NotNull(message="菜单id不能为空")
	private List<Long> permissionIds;
}
