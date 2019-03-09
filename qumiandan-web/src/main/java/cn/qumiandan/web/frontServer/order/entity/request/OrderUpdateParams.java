package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description: 修改订单参数对象
 * @author: zhuayngyong
 * @date: 2018/12/5 17:26
 */
@Data
public class OrderUpdateParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "订单编号不能为空")
	private String orderId;

	@NotNull(message = "订单状态不能为空")
	private Byte orderStatus;

	private Long updateId;	//修改人

}
