package cn.qumiandan.web.frontServer.order.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 退款申请返回数据
 * @author W
 *
 */
@Data
public class OrderReturnResponseParams implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 退款申请id
	 */
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
	 * 申请时间
	 */
	private Date applyDate;
}