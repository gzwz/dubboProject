package cn.qumiandan.web.adminServer.manager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class UserIdParams implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "用户id不能为空")
	private Long userId;

}
