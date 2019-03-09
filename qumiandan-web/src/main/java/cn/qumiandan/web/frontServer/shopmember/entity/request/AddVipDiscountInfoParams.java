package cn.qumiandan.web.frontServer.shopmember.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 添加会员折扣信息参数
 * @author lrj
 *
 */
@Data
public class AddVipDiscountInfoParams implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * 店铺id
	 */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	/**
	 * 优惠类型：1:discount_money为折扣；2：discount_money为金额
	 */
	@NotNull(message = "店铺会员优惠类型不能为空")
	private Byte type;
	
	/**
	 * 折扣/金额
	 */
	@NotNull(message = "折扣/金额不能为空")
	private BigDecimal discountMoney;
	
	/**
	 * 状态(1.可用，0.不可用)
	 */
	@NotNull(message = "状态(1.可用，0.不可用)")
	private Byte status;
	
	private Long createId;

}
