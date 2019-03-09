package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：更新自定义分类下的商品信息参数
 * @author：lrj
 * @date: 2018/11/13 15:47
 */
@Data
public class UpdateProAndCusCatoParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@NotNull(message="id不能为空")
	private Long Id ;
	
	/**
	 * 自定义分类ID
	 */
	private Long categoryId;
	
	/**
	 * 商品ID
	 */
	private Long productId;
	
	
}
