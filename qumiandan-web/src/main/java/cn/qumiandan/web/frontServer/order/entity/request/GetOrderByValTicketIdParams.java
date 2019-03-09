package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 根据核销券查询订单
 * @author lrj
 *
 */
@Data
public class GetOrderByValTicketIdParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 核销码
	 */
	@NotNull(message="核销码不能为空")
	private String ticketCode;
	
}
