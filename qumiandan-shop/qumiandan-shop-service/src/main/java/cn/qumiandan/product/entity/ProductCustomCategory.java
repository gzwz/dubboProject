package cn.qumiandan.product.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName(value = "qmd_product_and_custom_category")
public class ProductCustomCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Long id;

    /**
     * 店铺自定义分类编号
     */
    private Long categoryId;

    /**
     * 商品编号
     */
    private Long productId;

}