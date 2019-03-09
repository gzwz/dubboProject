package cn.qumiandan.product.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_product_log")
public class ProductLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 操作描述
     */
    private String operationDescription;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 操作状态 (status,成功,失败)
     */
    private Byte status;

}