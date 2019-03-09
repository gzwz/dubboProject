package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderReturnParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 上次申请退款id
	 */
	private Long parentId;
	/**
	 * 订单id
	 */
	@NotNull(message="订单id不能为空")
	private String orderId;
	/**
	 * 店铺id
	 */	
//	@NotNull(message="店铺id不能为空")
//	private Long shopId;
	/**
	 * 申请金额（一般不能大于订单实付金额）
	 */
	@NotNull(message="申请金额不能为空")
	private BigDecimal applyReturnAmount;
	/**
	 *申请备注 
	 */
//	@NotNull(message="申请备注不能为空")
	private String applyRemark;
}
