package cn.qumiandan.web.commonServer.verifycode.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

 
/**
 * 验证码
 * @author WLZ
 * 2018年11月13日
 */
@Data
public class SmsVerifyCodeParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@Length(min = 3, max = 6, message = "请填写正确的验证码")
	@NotBlank(message = "验证码不能为空")
	private String code;
	
	@Length(min = 11, max = 11, message = "请填写正确的手机号")
	@NotBlank(message = "手机号不能为空")
	private String mobile;
	
	
	
}
