package cn.qumiandan.payaccount.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.qumiandan.trade.enums.TradePayStatusEnum;
import lombok.Data;
/**
 * 交易数据VO
 * @author yuleidian
 * @version 创建时间：2018年12月28日 上午9:47:14
 */
@Data
public class TradeDataVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 处理类型*/
	private TradePayStatusEnum processType;
	/** 退款申请id*/
	private Long refundId;
	/** 交易id */
	private Long tradeId;
	/** 是否是订单   false: 普通订单  true: 游戏订单 */
	private Boolean isGame;
	/** 第三方支付流水号 */
	private String channelTradeNo;
	/** 店铺账户 */
	private Long shopAccountId;
	/** 店铺id */
	private Long shopId;
	/** 订单编号 */
	private String orderId;
	/** 游戏编号 */
	private String gameOrderId;
	/** 利楚订单号 */
	private String outTradeNo;
	/** 退款利楚订单号*/
	private String outRefundNo;
	/** 增加收入 */
	private BigDecimal increaseValue;
	/** 减少收入*/
	private BigDecimal decreaseValue;
	/** 支付完成时间 */
	private Date payEndDate;
	/** 退款完成时间*/
	private Date refundEndDate;
	/** 是否核销*/
	private Boolean isVT;
	/** 核销卷code*/
	private String ticketCode;
	/** 用户id*/
	private Long userId;
	/** 支付方式*/
	private String payChannal;
	
	/** 平台获利*/
	private BigDecimal platformIncreaseValue;
	/** 业务员级获利*/
	private BigDecimal salemanIncreaseValue;
	/** 业务员账户id*/
	private Long salemanAccountId;
	/** 平台实际获利*/
	private BigDecimal platformActualValue;
	
	
	/** 操作时间 */
	private Date operateDate;
	
	public Boolean isGame() {
		return this.isGame;
	}
	
	public Boolean isVT() {
		return this.isVT;
	}
	
	public void setIsGame(Boolean isGame) {
		this.isGame = isGame;
	}
}
