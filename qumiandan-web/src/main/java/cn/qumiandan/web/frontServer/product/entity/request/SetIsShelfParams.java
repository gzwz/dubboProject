package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 商品上下架参数
 * @author lrj
 *
 */
@Data
public class SetIsShelfParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@NotNull(message = "商品id不能为空")
	private Long productId;
	
	/**
	 * 店铺id
	 */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	/**
	 * 商品状态
	 */
	@NotNull(message = "商品状态不能为空")
	private Byte status;
}
