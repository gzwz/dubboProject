package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 设置密码参数
 * @author lrj
 *
 */
@Data
public class SetPasswordParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名（手机号）
	 */
	@NotBlank(message="用户名（手机号）不能为空")
	private String userName;
	
	/**
	 * 新密码
	 */
	@NotBlank(message="密码不能为空")
	private String newPassword;
	
	/**
	 * 旧密码
	 */
//	private String oldPassword;
	
	/**
	 * 验证码
	 */
	@NotBlank(message="验证码不能为空")
	private String code;

}
