package cn.qumiandan.web.frontServer.shopmember.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class UpdateVipDiscountInfoParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 店铺id
     */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	/**
	 * 折扣/金额
	 */
	@NotNull(message = "折扣/金额不能为空")
	private BigDecimal discountMoney;
}
