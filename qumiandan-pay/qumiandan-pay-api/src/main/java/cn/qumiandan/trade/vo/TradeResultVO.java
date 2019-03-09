package cn.qumiandan.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 流水结果
 * @author yuleidian
 * @version 创建时间：2018年12月26日 上午9:57:49
 */
@Data
public class TradeResultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 流水id*/
	private Long id;
	
	/** 订单id*/
	private String orderId;
	
	/** 游戏订单id*/
	private String gameOrderId;
	
	/** 是否支付成功*/
	private Boolean paySuccess;
	
	/** 支付方法*/
	private String payChannel;
	
	/** 支付金额*/
	private BigDecimal amount;
	
	/** 支付时间*/
	private Date payDate;
}
