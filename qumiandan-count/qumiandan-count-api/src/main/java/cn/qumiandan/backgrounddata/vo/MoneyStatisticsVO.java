package cn.qumiandan.backgrounddata.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
/**
 * 查询服务费、实收金额、支付笔数、利润
 * @author lrj
 *
 */
@Data
public class MoneyStatisticsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 服务费
	 */
	private BigDecimal serviceMoney;
	
	/**
	 * 实收金额 
	 */
	private BigDecimal actualMoney;
	
	/**
	 * 支付笔数
	 */
	private Integer payNum;
	
	/**
	 * 利润
	 */
	private BigDecimal profit;

	public MoneyStatisticsVO() {
		BigDecimal zeroValue = new BigDecimal(0);
		this.serviceMoney = zeroValue;
		this.actualMoney = zeroValue;
		this.payNum = 0;
		this.profit = zeroValue;
	}
	
	
}
