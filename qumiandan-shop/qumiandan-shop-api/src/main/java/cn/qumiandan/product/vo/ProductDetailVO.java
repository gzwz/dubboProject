package cn.qumiandan.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description：商品详情传输对象
 * @author：zhuyangyong
 * @date: 2018/11/22 10:06
 */
@Data
public class ProductDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
	//=====================商品基础信息=======================
	private Long id;	//商品id

	private String name;	//商品名称

	private Long typeId;	//类型编号(1：团购商品；2：商超商品)

	private Long brandId;	//品牌编号

	private String mainImage;	//商品主图

	private BigDecimal originalPrice;	//原价

	private BigDecimal presentPrice;	//现价

	private BigDecimal cost;	//成本

	private Byte isQmd;	//是否趣免单

	private Byte status;	//状态（1：待审核；2：审核未通过；3：审核通过，上架；4：下架；5：强制下架；6：已删除

	private Long createId;	//创建者

	private Long updateId;	//修改人

	private Date createDate;	//创建时间

	private Date updateDate;	//修改时间

	//=====================商品扩展信息=======================

	private String barcode;	//仓库条码

	private String productDescribe;	//描述

	private String metaKeyword;	//meta关键词

	private String metaDescription;	//meta描述

	private String attribute;	//属性

	//=====================商品自定义分类信息=======================
	private Long categoryId;	//自定义分类id

	private String categoryName;	//自定义分类名称

	//=====================商品自定义分类信息=======================
	private Long customCategoryId;	//自定义分类id

	private String customCategoryName;	//自定义分类名称

	//=====================商品图片信息=======================
	private List<ProductAlbumVO> productPictureVOList;

	//=====================商品-店铺关联信息=======================
	private Long shopId;	//店铺id
}
