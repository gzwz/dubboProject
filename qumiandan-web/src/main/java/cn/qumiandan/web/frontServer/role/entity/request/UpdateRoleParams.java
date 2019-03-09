package cn.qumiandan.web.frontServer.role.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改角色信息参数类
 * 
 * @author lrj
 *
 */
@Data
public class UpdateRoleParams {
	/**
	 * 角色id
	 */
	@NotNull(message = "角色id不能为空")
	private Long id;

	/**
	 * 序号
	 */
	private Integer sort;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 部门名称
	 */
	private Integer deptId;

	/**
	 * 英文别名
	 */
	private String ealias;

	/**
	 * 更新人id
	 */
	private Long updateId;

}
