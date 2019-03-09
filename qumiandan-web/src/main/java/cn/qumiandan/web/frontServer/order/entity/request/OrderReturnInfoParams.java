package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderReturnInfoParams implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(message="订单id不能为空")
	private String orderId;
}
