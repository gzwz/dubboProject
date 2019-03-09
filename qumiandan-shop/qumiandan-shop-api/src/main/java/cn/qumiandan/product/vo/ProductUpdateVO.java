package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @description：商品传输对象
 * @author：zhuyangyong
 * @date: 2018/11/22 10:06
 */
@Data
public class ProductUpdateVO implements Serializable {
    private static final long serialVersionUID = 1L;
	//=====================商品基础信息=======================
	private Long id;	//商品编号

	private String name;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long categoryId;	//类别编号

	private Long brandId;	//品牌编号

	private String mainImage;	//商品主图

	private BigDecimal originalPrice;	//原价

	private BigDecimal presentPrice;	//现价

	private BigDecimal cost;	//成本

	private Byte isQmd;	//是否趣免单

	private Long updateId;	//修改人

	private Date updateDate;

	//=====================商品扩展信息=======================
	private Long productId;	//商品id

	private String barcode;	//仓库条码

	private String productDescribe;	//描述

	private String metaKeyword;	//meta关键词

	private String metaDescription;	//meta描述

	private String attribute;	//属性

	//=====================商品自定义分类信息=======================
	private Long customCategoryId;	//自定义分类id

	//=====================商品图片信息=======================
	private List<ProductAlbumVO> productPictureVOList;

	//=====================商品-店铺关联信息=======================
	private Long shopId;	//店铺id
}
