package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取当前用户角色和所有与角色接口
 * @author yuleidian
 * @date 2019年1月16日
 */
@Data
public class GetCurrentUserRoleAndAllRoleParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "用户id不能为空")
	private Long userId;
	
}
