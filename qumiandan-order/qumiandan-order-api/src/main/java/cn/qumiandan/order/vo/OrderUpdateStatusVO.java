package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.qumiandan.order.constant.OrderStatusEnum;
import lombok.Data;

/**
 * @description：订单状态修改传输对象
 * @author：zhuyangyong
 * @date: 2018/12/5 13:26
 */
@Data
public class OrderUpdateStatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;     //订单编号

    /**
     * 订单状态(1.未付款；2.支付中;3.游戏支付;4.已付款;5.交易完成(已消费);6.退款申请中;7.已退款;8.拒绝退款;9.取消交易;10.已删除;)
     * {@link OrderStatusEnum}
     */
    private Byte orderStatus;
    
    /**
     * 支付方式（010微信，020 支付宝，060 qq钱包，080京东钱包，090口碑，100翼支付，110银联二维码，000趣免单支付）
     */
    private String payChannel;


    private Long updateId;  //修改人
    
    /**
     * 游戏实付金额
     */
    private BigDecimal gameAmount;
    

    private BigDecimal amountTotal; //订单金额(实际付款金额)

    private String outTradeNo;  //订单支付流水号
    
	/** 第三方交易订单号 通道订单号，微信订单号、支付宝订单号等*/
	private String channelTradeNo;

//    private Byte billing;    //是否开票（是否开具发票）（1.是,2.否）

    /**
     * 订单应付金额
     */
    private BigDecimal needPayAmount;

    /**
     * 商品金额
     */
    private BigDecimal productAmountTotal;
}
