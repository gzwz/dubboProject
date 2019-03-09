package cn.qumiandan.web.frontServer.shopmember.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 根据店铺id查询会员折扣信息参数
 * @author lrj
 *
 */
@Data
public class GetVipDiscountInfoByShopIdParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 店铺id
	 */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;

}
