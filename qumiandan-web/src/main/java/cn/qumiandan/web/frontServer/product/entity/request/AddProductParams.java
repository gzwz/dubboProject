package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：添加商品信息参数
 * @author：lrj
 * @date: 2018/11/13 15:47
 */
@Data
public class AddProductParams implements Serializable {

	private static final long serialVersionUID = 1L;

    //=====================商品基础信息=======================
    @NotBlank(message = "商品名称不能为空")
    private String name;	//商品名称

    @NotNull(message = "类型编号不能为空")
    private Long typeId;	//类型编号(1：团购商品；2：商超商品)

    @NotNull(message = "类别编号不能为空")
    private Long categoryId;	//类别编号

    //@NotNull(message = "品牌编号不能为空")
    private Long brandId;	//品牌编号

//    @NotBlank(message = "商品主图url不能为空")
//    private String mainImage;	//商品主图

    @NotNull(message = "原价不能为空")
    private BigDecimal originalPrice;	//原价

    @NotNull(message = "现价不能为空")
    private BigDecimal presentPrice;	//现价

//    @NotNull(message = "成本价不能为空")
    private BigDecimal cost;	//成本价

    @NotNull(message = "是否趣免单标记不能为空")
    private Byte isQmd;	//是否趣免单（1：是；0：否）

    private Long createId;	//创建者

    private Date createDate;

    //=====================商品扩展信息=======================
    private Long productId;	//商品id

    private String barcode;	//仓库条码

    @NotBlank(message = "商品描述不能为空")
    private String productDescribe;	//商品描述

    private String metaKeyword;	//meta关键词

    private String metaDescription;	//meta描述

    @NotBlank(message = "商品属性不能为空")
    private String attribute;	//商品属性

    //=====================商品图片信息=======================
    @NotEmpty(message = "商品图片信息不能为空")
    private List<ProductPictureParams> productPictureVOList;

    //=====================商品-店铺关联信息=======================
    @NotNull(message = "店铺id不能为空")
    private Long shopId;	//店铺id
    
  //=====================商品自定义分类信息=======================
  	private Long customCategoryId;	//自定义分类id
}
