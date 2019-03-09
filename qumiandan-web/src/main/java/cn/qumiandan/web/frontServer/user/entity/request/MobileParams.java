package cn.qumiandan.web.frontServer.user.entity.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class MobileParams {

	@Length(min = 11, max = 11, message = "请填写正确的手机号")
	@NotBlank(message = "请填写手机号")
	private String mobile;
	
}
