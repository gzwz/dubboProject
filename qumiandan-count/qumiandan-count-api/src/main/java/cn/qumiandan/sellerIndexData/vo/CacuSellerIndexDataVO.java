package cn.qumiandan.sellerIndexData.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class CacuSellerIndexDataVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;
	/**
	 * 实收金额
	 */
	private BigDecimal actualMoney;
	
	/**
	 * 成本
	 */
	private BigDecimal allCost;
	
}
