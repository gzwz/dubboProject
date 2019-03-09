package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class AddUserRoleParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 用户Id
     */
	@NotNull(message = "用户Id不能为空")
    private Long userId;

    /**
     * 用户角色列表
     */
	@NotNull(message="用户角色id集合不能为空")
    private List<Long> roleIds;
    
}
