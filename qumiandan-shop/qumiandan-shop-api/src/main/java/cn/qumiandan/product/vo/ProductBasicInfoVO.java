package cn.qumiandan.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductBasicInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long shopId;//店铺id
	
	private String name;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long categoryId;	//分类id

	private Long brandId;	//品牌编号

	private String mainImage;	//商品主图

	private BigDecimal originalPrice;	//原价

	private BigDecimal presentPrice;	//现价

	private BigDecimal cost;	//成本

	private Byte isQmd;	//是否趣免单
	
	private Byte status; //商品状态
	
	private Long customCategoryId;//自定义分类id

	private String customCategoryName;	//自定义分类名称(菜单名称)
}
