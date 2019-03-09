package cn.qumiandan.pay.withdraw.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 提现申请VO
 * @author PH
 *
 */
@Data
public class WithdrawCashVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 提现id
	 */
	private Long id;
	/**
	 * 账户id
	 */
	private Long accountId;
	
	/**
	 * 类型：1业务员；2市代理；3省代理；4店铺
	 */
	private Byte accountType;

	/**
	 * 账户名
	 */
	private String   accountName;
	
	/**
	 * 提现金额
	 */
	private BigDecimal withdrawalAmount;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	
	/** 
	 * 实际打款金额
	 */
	private BigDecimal actualAmount;
	
	/**
	 * 实际打款时间
	 */
	private Date paymentDate;
	
	/**
	 * 提现申请备注
	 */
	private String remarkSubmit;
	/**
	 * 提现备注审核
	 */
	private String remarkAudit;

	/**
	 * 打款银行卡号
	 */
	private String bankNo;
	
	/**
	 * 持卡人姓名
	 */
	private String cardHolder;
	/**
	 * 支付渠道单号
	 */
	private String outTradeNo;

	/**
	 * 错误信息
	 */
	private String errorMessage;
	
	/**
	 * 状态（1. 审核中 2.未通过  3.等待打款  4.自动打款异常 5.打款完成  6.手动打款 ）
	 */
	private Byte status;
	/**
	 * 审核人
	 */
	private Long auditorId;
	
	/**
	 * 请求参数
	 */
	private String requestParam;
	
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
