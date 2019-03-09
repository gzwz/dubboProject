package cn.qumiandan.pay.cashback.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 返现传输类
 * @author lrj
 *
 */
@Data
public class CashbackVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 资格券id
	 */
	private String ticketId;

	/**
	 * 店铺id
	 */
	private Long shopId;

	/**
	 * 返现申请备注
	 */
	private String remarkSubmit;

	/**
	 * 返现审核备注
	 */
	private String remarkAudit;

	/**
	 * 返现金额
	 */
	private BigDecimal actualAmount;

	/**
	 * 实际打款时间
	 */
	private Date paymentDate;

	/**
	 * 打款异常信息
	 */
	private String errorMessage;

	/**
	 * 状态（1. 审核中 2.未通过 3.等待打款 4.自动打款异常 5.打款完成 6.手动打款 ）
	 */
	private Byte status;

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
	 * 更新人
	 */
	private Long updateId;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;

}
