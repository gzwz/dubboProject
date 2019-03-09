package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 批量操作用户角色关联
 * @author yuleidian
 * @date 2019年1月16日
 */
@Data
public class BatchOperateUserRoleParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户id*/
	@NotNull(message = "用户id不能为空")
	private Long userId;
	
	/** 新增或修改角色信息*/
	private List<Long> roles;
}
