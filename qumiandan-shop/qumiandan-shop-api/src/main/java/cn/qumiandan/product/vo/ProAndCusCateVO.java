package cn.qumiandan.product.vo;

import java.io.Serializable;
/**
 * @description：自定义分类商品传输对象
 * @author：lrj
 * @date: 2018/11/12 10:30
 */
public class ProAndCusCateVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Long Id ;
	
	/**
	 * 自定义分类ID
	 */
	private Long categoryId;
	
	/**
	 * 商品ID
	 */
	private Long productId;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ProAndCusCateVO [Id=" + Id + ", categoryId=" + categoryId + ", productId=" + productId + "]";
	}
	
	
}
