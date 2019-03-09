package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 根据用户名查询角色信息参数
 * @author lrj
 *
 */
@Data
public class GetUserNameParams implements Serializable{

	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String userName;
}
