package cn.qumiandan.role.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户角色关联 更新信息VO
 * @author yld
 */
@Data
public class UpdateRolePermissionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 角色id*/
	private Long permissionId;
	
	///** 是否删除*/
	//private Boolean deleted;
}
