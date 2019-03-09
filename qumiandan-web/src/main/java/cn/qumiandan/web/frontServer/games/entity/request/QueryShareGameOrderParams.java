package cn.qumiandan.web.frontServer.games.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class QueryShareGameOrderParams {
	
	//@NotNull(message = "订单id不能为空")
	//private String orderId;
	
	@NotNull(message = "游戏订单gameId不能为空")
	private String gameId;
}
