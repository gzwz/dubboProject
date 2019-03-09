package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 获取首页轮播图id参数
 * @author lrj
 *
 */
@Data
public class GetIdParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 首页轮播图id
	 */
	@NotNull(message = "首页轮播图id不能为空")
	private Long id;
}
