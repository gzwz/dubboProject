package cn.qumiandan.web.frontServer.order.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 订单退款VO
 * @author W
 *
 */
@Data
public class OrderReturnInfoResponseParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 上次申请退款id
	 */
	private Long parentId;
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 店铺id
	 */
	private Long shopId;
	/**
	 * 申请金额（一般不能大于订单实付金额）
	 */
	private BigDecimal applyReturnAmount;
	/**
	 *申请备注 
	 */
	private String applyRemark;
	/**
	 * 审批状态(1,创建退款申请，2.审批通过，3.审批不通过，4.等待打款，5.退款完成,6.平台介入)
	 */
	private Byte auditlStatus;
	/**
	 * 审批人
	 */
	private Long auditUser_id;
	/**
	 * 审批备注
	 */
	private String auditRemark;
	/**
	 * 申请时间
	 */
	private Date applyDate;
	/**
	 * 审批时间
	 */
	private Date auditDate;
	/**
	 * 退单打款流水号id
	 */
	private Long tradeId;
	/**
	 * 退单打款第三方流水号id
	 */
	private String outTrandNo;
	/**
	 * 退款金额
	 */
	private BigDecimal refundFee;
	/**
	 * 退款单号
	 */
	private String outRefundNo;
	
	/** 退款时间*/
	private Date refundDate;
}
