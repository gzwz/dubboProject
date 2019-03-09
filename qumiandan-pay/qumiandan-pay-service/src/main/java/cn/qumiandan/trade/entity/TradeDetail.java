package cn.qumiandan.trade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 交易流水信息
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:24:24
 */
@Data
@TableName("qmd_trade_detail")
public class TradeDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号 */
	@TableId(type = IdType.ID_WORKER)
	private Long id;

	/** 入账账户id */
	private Long accountInId;

	/** 出账账户id*/
	private Long accountOutId;
	
	/** 扫呗商户id*/
	private Long merchantId;
	
	/** 交易流水号 (主订单号)*/
	private String serialNo;

	/** 游戏订号*/
	private String gameSerialNo;
	
	/** 交易后的余额 */
	private BigDecimal tradeBalance;

	/** 交易金额 */
	private BigDecimal amount;

	/** 流水类型   TradeDetailTypeEnum
	 * 1.普通订单类型
	 * 2.游戏订单类型
	 * 3.业务员分润类型
	 * 4.市级分润类型
	 * 5.省级分润类型
	 */
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

	/**
	 * 支付渠道单号 (
	 * 第三方渠道交易单号,交易单号建议加表示业务来源和业务类型的前缀,如wxpnrechg...表示微信公众号充值,wxmabuy...表示微信小程序购买,wapalipaybuy...表示手机端支付宝购买)
	 */
	private String outTradeNo;

	/** 交易币种(RMB,$) */
	private String currency;

	/** 交易申请备注 */
	private String remarkSubmit;

	/** 交易参数（JSON格式） */
	private String tradeParams;

	/** 第三方交易订单号 通道订单号，微信订单号、支付宝订单号等*/
	private String channelTradeNo;
	
	/** 状态（1.创建待支付 2.已支付 3.取消支付 ） */
	private Byte status;

	/** 支付回调时间*/
	private Date callbackDate;
	
	/** 回调金额*/
	private BigDecimal callbackAmount;
	
	/** 创建时间 */
	private Date createDate;

	/** 支付时间 */
	private Date payDate;
}
