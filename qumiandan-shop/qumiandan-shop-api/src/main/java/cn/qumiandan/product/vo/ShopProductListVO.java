package cn.qumiandan.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description：店铺商品列表传输对象
 * @author：zhuyangyong
 * @date: 2018/11/26 10:06
 */
@Data
public class ShopProductListVO implements Serializable {
    private static final long serialVersionUID = 1L;
	//=====================商品基础信息=======================
	private Long id;	//商品id
	
	private Long shopId;

	private String name;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long brandId;	//品牌编号

	private String mainImage;	//商品主图

	private BigDecimal originalPrice;	//原价

	private BigDecimal presentPrice;	//现价

	private BigDecimal cost;	//成本

	private Byte isQmd;	//是否趣免单

	private Long createId;	//创建者

	/**
	 * 状态（1：待审核；2：审核未通过；3：审核通过，上架；4：下架；5：强制下架；6：已删除）
	 */
	private Byte status;

	//=====================商品扩展信息=======================

	private String barcode;	//仓库条码

	private String productDescribe;	//描述

	private String attribute;	//属性

	//=====================商品自定义分类信息=======================
	private Long categoryId;	//分类id

	private String categoryName;	//分类名称

	//=====================商品自定义分类信息=======================
	private Long customCategoryId;	//自定义分类id

	private String customCategoryName;	//自定义分类名称

}
