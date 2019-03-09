package cn.qumiandan.order.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品编号
     */
    private Long productId;

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
     * 商品属性
     */
    private String attribute;

    /**
     * 客户商品备注
     */
    private String remark;
}
