package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @description：订单传输对象
 * @author：WLZ
 * @date: 2018/12/3 15:26
 */
@Data
public class OrderAddVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**所使用的优惠券的id*/
    private Long couponId;
    
    /**
     * 商店编号
     */
    private Long shopId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 订单类型（1.普通支付，2.趣免单）
     */
    private Byte orderType;

    /**
     * 是否开票（是否开具发票）（1.是,2.否）
     */
    private Byte billing;

    /**
     * 订单应付金额
     */
    private BigDecimal needPayAmount;

    /**
     * 商品金额
     */
    private BigDecimal productAmountTotal;
    

    /**
     * 折扣比例
     */
    private BigDecimal discountRate;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 运费金额
     */
//    private BigDecimal logisticsFee;

    /**
     * 订单金额(实际付款金额)
     */
//    private BigDecimal amountTotal;

    /**
     * 支付方式（010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000趣免单支付）
     */
    private String payChannel;

    /**
     * 省
     */
    private String proCode;

    /**
     * 市
     */
    private String cityCode;

    /**
     * 县
     */
    private String countyCode;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 订单商品列表
     */
    private List<OrderProductVO> orderProductList;

    /**
     * 店铺名称
     */
    private String shopName;

}
