package cn.qumiandan.shop.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shop_category_relation")
public class ShopCategoryRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Long id;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 分类id
     */
    private Long categoryId;

}