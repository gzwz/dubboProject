package cn.qumiandan.web.commonServer.maintain.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 维护类型参数类
 * @author lrj
 *
 */
@Data
public class MaintainTypeParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 维护类型
	 */
	@NotBlank(message = "维护类型不能为空")
	private String type;
}
