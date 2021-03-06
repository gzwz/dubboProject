package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 商品-店铺联合传输对象
 * @author lrj
 *
 */
@Data
public class ProductAndShopVO implements Serializable{


	private static final long serialVersionUID = 1L;
    /**
     * 店铺编号
     */
    private Long shopId;
    
    private String shopNme;	//店铺名称
    
    private String productName;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private String categoryName;	//分类id
	
	private Long brandId;//品牌id
	
	private Long categoryId;//分类ID

	private String brandName;	//品牌编号

	private String mainImage;	//商品主图

	private BigDecimal originalPrice;	//原价

	private BigDecimal presentPrice;	//现价

	private BigDecimal cost;	//成本

	private Byte isQmd;	//是否趣免单

	private Long createId;	//创建者

	private Date createDate;
	
	private Long updateId;
	
	private Date updateDate;

	//=====================商品扩展信息=======================
	private Long productId;	//商品id
	
	

	private String barcode;	//仓库条码

	private String productDescribe;	//描述

	private String metaKeyword;	//meta关键词

	private String metaDescription;	//meta描述

	private String attribute;	//属性
	
	private Byte status;//商品状态


}
