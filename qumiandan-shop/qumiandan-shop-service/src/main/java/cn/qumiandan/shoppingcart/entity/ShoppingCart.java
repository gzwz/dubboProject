package cn.qumiandan.shoppingcart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_shopping_cart")
public class ShoppingCart implements Serializable {
	
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 店铺编号
     */
    private Long shopId;

    /**
     *  商品编号
     */
    private Long productId;

    /**
     * 是否有效(1.有效，0.无效)
     */
    private Byte productExists;

    /**
     *  购买数量
     */
    private Integer number;

    /**
     * 创建时间
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

   
}