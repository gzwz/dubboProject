package cn.qumiandan.product.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_product_extend")
public class ProductExtend implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 仓库条码
     */
    private String barcode;

    /**
     * 描述
     */
    private String productDescribe;

    /**
     * meta关键词
     */
    private String metaKeyword;

    /**
     * meta描述
     */
    private String metaDescription;

    /**
     * 属性
     */
    private String attribute;

    private String membersLimit;	//成员数量限制
}