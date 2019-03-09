package cn.qumiandan.user.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 店铺信息
 * @author yuleidian
 * @version 创建时间：2018年11月27日 下午7:00:18
 */
@Data
public class ShopInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 店铺编号*/
	private Long id;					
	/** 店铺名称*/
	private String name;

	
	public ShopInfoVO() {}


	public ShopInfoVO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
