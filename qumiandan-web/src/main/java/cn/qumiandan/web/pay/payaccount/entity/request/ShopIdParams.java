package cn.qumiandan.web.pay.payaccount.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 根据店铺查询银行卡参数类
 * @author lrj
 *
 */
@Data
public class ShopIdParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 店铺id	
	 */
	@NotNull(message="店铺id不能为空")
	private Long shopId;

}
