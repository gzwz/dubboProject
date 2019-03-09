package cn.qumiandan.web.frontServer.games.entity.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddShareGameOrderParams {
	
	@NotNull(message = "订单id不能为空")
	private String  orderId;
	
	@NotNull(message = "游戏单价price不能为空")
	private BigDecimal price;
	
	@NotNull(message = "游戏倍数不能为空")
	private Integer times;
}
