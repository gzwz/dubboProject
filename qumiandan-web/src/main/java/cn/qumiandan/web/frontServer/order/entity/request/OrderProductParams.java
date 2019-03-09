package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description: 订单商品信息
 * @author: zhuayngyong
 * @date: 2018/12/5 11:38
 */
@Data
public class OrderProductParams implements Serializable {
    private static final long serialVersionUID = 1L;


    @NotNull(message = "商品id不能为空")
    private Long productId;

    @NotBlank(message = "商品名称不能为空")
    private String productName;

    //@NotNull(message = "商品价格不能为空")
    //private BigDecimal productPrice;

   // @NotBlank(message = "商品型号不能为空")
    private String productMarque;

   // @NotBlank(message = "商品型号信息不能为空")
    private String productModeDesc;

    @NotBlank(message = "商品缩略图不能为空")
    private String productImgUrl;

    //	@NotNull(message = "折扣比例不能为空")
   // private BigDecimal discountRate;

    //	@NotNull(message = "折扣金额不能为空")
   // private BigDecimal discountAmount;

    @NotNull(message = "购买数量不能为空")
    private Integer number;

   // @NotBlank(message = "客户商品备注不能为空")
    private String remark;
}
