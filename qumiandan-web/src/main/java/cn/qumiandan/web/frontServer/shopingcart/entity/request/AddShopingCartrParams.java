package cn.qumiandan.web.frontServer.shopingcart.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class AddShopingCartrParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 店铺编号
	 */
	@NotNull(message = "店铺编号不能为空")
	private Long shopId;

	/**
	 * 商品编号
	 */
	@NotNull(message = "商品编号不能为空")
	private Long productId;

	/**
	 * 购买数量
	 */
	@NotNull(message = "购买数量不能为空")
	private Integer number;


}
