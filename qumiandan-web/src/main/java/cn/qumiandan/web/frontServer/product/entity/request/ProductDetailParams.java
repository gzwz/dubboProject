package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 商品详情参数对象
 * @author zhuyangyong
 * @version 创建时间：2018年11月26日 10:38
 */
@Data
public class ProductDetailParams implements  Serializable{

	private static final long serialVersionUID = 1L;	
	/**
	 * 店铺id/自定义类型id
	 */
	@NotNull(message="商品id不能为空")
	private Long id;
}
