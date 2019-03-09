package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 绑定微信参数类
 * @author lrj
 *
 */
@Data
public class GetCodeParams implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="code不能为空")
	private String code;
}
