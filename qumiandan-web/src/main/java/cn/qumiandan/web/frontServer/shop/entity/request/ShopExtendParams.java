package cn.qumiandan.web.frontServer.shop.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ShopExtendParams {
	@NotNull(message="店铺id不能为空")
	private Long shopId;
	@NotNull(message="店铺开关状态不能为空")
	private Byte gameSwitch;
	
}
