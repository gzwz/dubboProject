package cn.qumiandan.web.countServer.sellerIndexData.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BackGroundDataResponseParams implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;
	/**
	 * 应收金额
	 */
	private BigDecimal receivableAmount;
	/**
	 * 实收金额
	 */
	private BigDecimal actualAmount;
	/**
	 * 服务费
	 */
	private BigDecimal serviceFee;
	/**
	 * 成本
	 */
	private BigDecimal productCost;
	/**
	 * 待核销（为前端不报错而保留字段）
	 */
	private Integer  discountAmount;
	
	/**
	 * 待核销
	 */
	private Integer waitingUsing;
	
	/**
	 * 支付笔数
	 */
	private Long payNum;
	/**
	 * 退款金额
	 */
	private BigDecimal returnAmount;
	/**
	 * 消费人数
	 */
	private Long consumeNum;
}
