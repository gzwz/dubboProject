package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class GameOrderVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	
	/**订单id*/
	private String orderId;
	
	/**是否中奖(1.未中奖，2.已中奖)*/
	private Byte win;
	
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
	
	/**支付渠道（1.微信支付，2.支付宝，3.银行卡支付）*/
	private String payChannel;
	
	/**out_trade_no*/
	private String outTradeNo;
	
	/** 第三方交易订单号 通道订单号，微信订单号、支付宝订单号等*/
	private String channelTradeNo;
	
	/**创建人id*/
	private Long createId;
	
	/**创建时间*/
	private Date createDate;
	
}
