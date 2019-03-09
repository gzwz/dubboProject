package cn.qumiandan.web.commonServer.functionmaintain.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 维护功能主键参数类
 * @author lrj
 *
 */
@Data
public class GetFunctionMaintainIdParams implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(message = "功能模块id不能为空")
	private Long id;
}
