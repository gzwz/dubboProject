package cn.qumiandan.pay.saobei.model.request.pay;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 商品详情
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午5:12:57
 */
@Data
public class GoodsDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商品编号*/
	@Expose
	@SerializedName("goods_Id")
	private String goodsId;
	
	/** 商品名称*/
	@Expose
	@SerializedName("goods_name")
	private String goods_name;
	
	/** 商品数量*/
	@Expose
	@SerializedName("quantity")
	private String quantity;
	
	/** 商品单价，单位为分*/
	@Expose
	@SerializedName("price")
	private String price;
	
	
}
