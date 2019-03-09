package cn.qumiandan.web.frontServer.shop.entity.request.updateshop;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改店铺状态参数
 * @author lrj
 *
 */
@Data
public class UpdateShopStatusParams implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id
	 */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;

	/**
	 * 店铺状态
	 */
	@NotNull(message = "店铺状态不能为空")
	private Byte status;
	
	private Long updateId;
}
