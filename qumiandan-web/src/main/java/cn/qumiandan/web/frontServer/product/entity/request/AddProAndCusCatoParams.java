package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @description：自定义分类添加商品
 * @author：lrj
 * @date: 2018/11/13 15:47
 */
@Data
public class AddProAndCusCatoParams implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 自定义分类ID
	 */
	private Long categoryId;
	
	/**
	 * 商品ID
	 */
	private Long productId;
	
	
}
