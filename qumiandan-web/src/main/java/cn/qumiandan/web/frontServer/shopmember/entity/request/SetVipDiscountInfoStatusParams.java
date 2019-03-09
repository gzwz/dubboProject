package cn.qumiandan.web.frontServer.shopmember.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 设置会员折扣启用/禁用参数
 * @author lrj
 *
 */
@Data
public class SetVipDiscountInfoStatusParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 店铺id
	 */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	@NotNull(message = "会员折扣状态不能为空")
	private Byte status;

}
