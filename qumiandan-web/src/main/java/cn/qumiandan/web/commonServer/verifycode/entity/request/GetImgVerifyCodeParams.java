package cn.qumiandan.web.commonServer.verifycode.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

 
/**
 * 验证码
 * @author WLZ
 * 2018年11月13日
 */
@Data
public class GetImgVerifyCodeParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "唯一标志不能为空")
	private String sign;
 
	
	
	
}
