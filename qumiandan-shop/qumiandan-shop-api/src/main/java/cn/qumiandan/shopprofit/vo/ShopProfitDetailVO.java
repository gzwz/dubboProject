package cn.qumiandan.shopprofit.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ShopProfitDetailVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺Id
	 */
	private Long shopId;
	
	/**
	 * 费率码
	 */
	private String rateCode;
	/**
	 * 费率
	 */
	private BigDecimal rate;
	
	/**
	 * 费率规则
	 */
	private String  rateRule;


}
