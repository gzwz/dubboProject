package cn.qumiandan.order.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName(value = "qmd_order_detail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品编号
     */
    private Long productId;

//    /**
//     * 卖家id
//     */
//    private Long shopId;
//
//    /**
//     * 卖家名称
//     */
//    private String shopName;

    /**
     *  商品名称(商品可能删除,所以这里要记录，不能直接读商品表)
     */
    private String productName;

    /**
     * 商品价格(商品可能删除,所以这里要记录)
     */
    private BigDecimal productPrice;

    /**
     * 商品型号
     */
    private String productMarque;

    /**
     * 商品型号信息(记录详细商品型号，如颜色、规格、包装等)
     */
    private String productModeDesc;

    /**
     * 商品缩略图
     */
    private String productImgUrl;

    /**
     * 折扣比例
     */
    private BigDecimal discountRate;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 小计金额
     */
    private BigDecimal subtotal;
    
    /**
     * 商品属性
     */
    private String attribute;

    /**
     * 客户商品备注 
     */
    private String remark;

}