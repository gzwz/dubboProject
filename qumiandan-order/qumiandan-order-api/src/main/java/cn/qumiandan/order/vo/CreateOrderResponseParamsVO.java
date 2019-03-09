package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 创建订单返回参数
 * @author lrj
 *
 */
@Data
public class CreateOrderResponseParamsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单参数
	 */
	private String  orderId;
	
	/**
	 * 店铺名
	 */
	private String ShopName;
	
	/**
     * 订单应付金额
     */
    private BigDecimal needPayAmount;
	
    /**
     * 店铺logo
     */
	private String shopLogo; 

}
