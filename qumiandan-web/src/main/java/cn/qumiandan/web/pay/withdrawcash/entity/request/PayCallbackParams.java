package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PayCallbackParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 提现id
	 */
	@NotNull(message="提现id不能为空")
	private Long id;
	/**
	 * 账户id
	 */
	private Long accountId;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 提现金额
	 */
	private BigDecimal withdrawalAmount;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 提现申请备注
	 */
	private String remarkSubmit;
	/**
	 * 提现备注审核
	 */
	private String remarkAudit;
	/**
	 * 银行支行名称
	 */
	private String bankName;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 持卡人姓名
	 */
	private String holderName;
	/**
	 * 支付渠道
	 */
	private int payChannel;
	/**
	 * 支付渠道单号
	 */
	private String outTradeNo;
	/**
	 * 交易参数（json格式）
	 */
	private String tradeParams;
	/**
	 * 提现申请状态
	 */
	private int status;
	/**
	 * 审核人
	 */
	private Long auditorId;
	/**
	 * 审核时间
	 */
	private Date auditorDate;
	/**
	 * 创建人
	 */
	private Long createId;
	/**
	 * 修改人
	 */
	private Long updateId;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	

}
