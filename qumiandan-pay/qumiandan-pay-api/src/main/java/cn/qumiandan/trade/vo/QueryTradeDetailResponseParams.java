package cn.qumiandan.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class QueryTradeDetailResponseParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 扫呗商户id*/
	private Long merchantId;
	
	/** 入账账户id */
	private Long accountInId;

	/** 出账账户id*/
	private Long accountOutId;
	
	/** 交易流水号 (主订单号)*/
	private String serialNo;

	/** 游戏订号*/
	private String gameSerialNo;
	
	/**
	 * 支付渠道单号 (
	 * 第三方渠道交易单号,交易单号建议加表示业务来源和业务类型的前缀,如wxpnrechg...表示微信公众号充值,wxmabuy...表示微信小程序购买,wapalipaybuy...表示手机端支付宝购买)
	 */
	private String outTradeNo;

	/** 第三方交易订单号 通道订单号，微信订单号、支付宝订单号等*/
	private String channelTradeNo;
	
	/** 交易后的余额 */
	private BigDecimal tradeBalance;

	/** 交易金额 */
	private BigDecimal amount;

	/** 流水类型   TradeDetailTypeEnum
	 ** 1.普通订单类型
	/** 2.游戏订单类型
	/** 3.业务员分润类型
	/** 4.市级分润类型
	/** 5.省级分润类型*/
	private Byte type;
	
	/** 交易类型: 1.充值，2:消费，3.（转入）收到转账，4.（转出），5.提现 */
	private Byte tradeType;

	/** 交易商品名称 */
	private String productName;

	/** 交易方名称 */
	private String thirdTradeName;

	/**
	 * (trade.alipay.native:支付宝、trade.weixin.jspay:微信公众号支付、trade.weixin.native:微信扫码支付、trade.bankpay.native:网银)
	 */
	private String payChannel;


	/** 交易申请备注 */
	private String remarkSubmit;


	/** 状态（1.创建待支付 2.已支付 3.取消支付 4.退款 5.订单） */
	private Byte status;
	
	/** 创建时间 */
	private Date createDate;

}
