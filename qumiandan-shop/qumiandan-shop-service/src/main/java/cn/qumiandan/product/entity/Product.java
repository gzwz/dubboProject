package cn.qumiandan.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商品编号
     */
    private Long id;
    
    private Long shopId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 类型编号(1：团购商品；2：商超商品)
     */
    private Long typeId;

    /**
     * 类别编号
     */
    private Long categoryId;

    /**
     * 品牌编号
     */
    private Long brandId;

    /**
     * 商品主图
     */
    private String mainImage;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    private BigDecimal presentPrice;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 是否趣免单
     */
    private Byte isQmd;

    /**
     * 状态（1：待审核；2：审核未通过；3：审核通过，上架；4：下架；5：强制下架；6：已删除）
     */
    private Byte status;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

}