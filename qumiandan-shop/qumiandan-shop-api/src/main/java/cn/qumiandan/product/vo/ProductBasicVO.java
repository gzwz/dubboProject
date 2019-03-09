package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ProductBasicVO implements Serializable {
	private static final long serialVersionUID = 1L;
	//=====================商品基础信息=======================
	private Long id;
	
	private String name;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long categoryId;	//分类id

	private Long brandId;	//品牌编号

	private String mainImage;	//商品主图

	private BigDecimal originalPrice;	//原价

	private BigDecimal presentPrice;	//现价

	private BigDecimal cost;	//成本

	private Byte isQmd;	//是否趣免单

	private Long createId;	//创建者

	private Date createDate;
	
	private Byte status; //商品状态
	
	//===========店铺关联表信息=========
	
	private Long shopId;//店铺id
	

}
