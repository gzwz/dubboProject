package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cn.qumiandan.enums.LoginOrigin;
import lombok.Data;

/**
 * 账号登陆参数
 * @author yuleidian
 * @version 创建时间：2018年11月7日 上午10:30:44
 */
@Data
public class AccountUserParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "账号不能为空")
	private String username;
	
	@NotBlank(message = "密码不能为空")
	private String password;
	
	/** 登录端来源不能为空*/
	@NotNull(message = "来源不能空")
	private LoginOrigin origin;
	
}
