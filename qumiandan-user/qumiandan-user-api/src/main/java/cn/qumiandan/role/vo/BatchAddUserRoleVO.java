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
public class BatchAddUserRoleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户id*/
	private Long userId;
	
	/** 新增或修改角色信息*/
	private List<Long> roles;
	
}
