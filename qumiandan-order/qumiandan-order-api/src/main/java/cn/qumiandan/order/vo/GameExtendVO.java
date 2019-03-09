package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class GameExtendVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	/**订单id*/
	private String orderId;
	
	/**是否中奖(1.未中奖，2.已中奖)*/
	private Byte win;
	
	/** 是否开奖标志，1.未开奖，2. 已开奖 */
	private Byte openLottery;
	
	/**游戏订单应付金额*/
	private BigDecimal needPayAmount;
	
	/**实付金额*/
	private BigDecimal amountTotal;
	
	/**倍率，购买数量*/
	private Integer times;
	
	/** 1.未付款，2.已付款.，3.已消费 */
	private Byte orderStatus;
	
	/**支付时间*/
	private Date payDate;
	
	/**支付渠道,与支付渠道表相关联*/
	private String payChannel;
	
	/** 第三方支付流水号 */
	private String outTradeNo;
	
	/** 通道订单号，微信订单号、支付宝订单号等*/
	private String channelTradeNo;
	
	/**创建人id*/
	private Long createId;
	
	/**创建时间*/
	private Date createDate;

}
