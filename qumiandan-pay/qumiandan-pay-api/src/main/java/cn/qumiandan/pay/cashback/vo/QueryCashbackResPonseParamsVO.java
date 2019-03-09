package cn.qumiandan.pay.cashback.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.qumiandan.ticket.vo.QualificationTicketVO;
import lombok.Data;
/**
 * 总后台查询返现信息
 * @author lrj
 *
 */
@Data
public class QueryCashbackResPonseParamsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//=============用户信息
	/**
	 * 用户信息
	 */
	private String userName;
	
	
	//===========店铺信息===========
	/**
	 * 店铺信息
	 */
	private String shopName;
	
	private String shopLogo;
	
	
	
	/**
	 * 资格券信息
	 */
	private QualificationTicketVO qualificationTicketVO;
	
	//========返现信息=============
	/**
	 * 资格券id
	 */
	private String ticketId;

	private Long  id;
	
	/**
	 * 店铺id
	 */
	private Long shopId;

	/**
	 * 提现申请备注
	 */
	private String remarkSubmit;

	/**
	 * 提现审核备注
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
