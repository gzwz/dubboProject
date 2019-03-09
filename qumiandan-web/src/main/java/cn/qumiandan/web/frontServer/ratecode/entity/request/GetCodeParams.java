package cn.qumiandan.web.frontServer.ratecode.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 获取费率code参数类
 * @author lrj
 *
 */
@Data
public class GetCodeParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 费率code
	 */
    @NotBlank(message="费率code不能为空")
	private String code;
}
