package cn.qumiandan.web.frontServer.games.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CalculateGamePriceParams {
	
	@NotNull(message = "orderId不能为空")
	private String orderId;
}
