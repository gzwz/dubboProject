package cn.qumiandan.web.frontServer.user.entity.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cn.qumiandan.enums.LoginOrigin;
import lombok.Data;

@Data
public class WechatCodeParams {

	@NotBlank(message = "授权code不能为空")
	private String code;
	
	/** 登录端来源不能为空*/
	@NotNull(message = "来源不能空")
	private LoginOrigin origin;
	
}
