package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取id参数类
 * 
 * @author lrj
 *
 */
@Data
public class GetIdParams implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	/**
	 * 角色id
	 */
	@NotNull(message = "角色id不能为空")
	private Long id;
}
