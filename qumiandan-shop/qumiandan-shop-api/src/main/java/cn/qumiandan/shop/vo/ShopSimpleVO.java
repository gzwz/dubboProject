package cn.qumiandan.shop.vo;

import java.io.Serializable;

import lombok.Data;

/**
 *  shop的简单信息
 * @author yuleidian
 * @version 创建时间：2018年11月29日 下午2:06:28
 */
@Data
public class ShopSimpleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 店铺编号 */
	private Long id;
	
	/** 店铺名称*/
	private String name;
	
	
	public ShopSimpleVO() {}


	public ShopSimpleVO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
