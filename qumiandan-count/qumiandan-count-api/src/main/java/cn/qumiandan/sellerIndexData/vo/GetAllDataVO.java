package cn.qumiandan.sellerIndexData.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 商家端统计
 * @author lrj
 *
 */
@Data
public class GetAllDataVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交易额
	 */
	private BigDecimal tradeVolume;
	
	/**
	 * 应收金额(商品总价)
	 */
	private BigDecimal receivableMoney;
	   
	/**
	 * 实收金额 
	 */
	private BigDecimal actualMoney;
	
	/**
	 * 成本
	 */
	private BigDecimal allCost;

	/**
	 * 待核销订单数
	 */
	private Integer  waitingUsing;
	
	/**
	 * 服务费
	 */
	private BigDecimal serviceMoney;
	
	/**
	 * 退款
	 */
	private BigDecimal refundMoney;

	/**
	 * 订单数量
	 */
	private Integer orderNum;


	/**
	 * 消费人数
	 */
	private Integer personNum;


	public GetAllDataVO() {
		BigDecimal value = new BigDecimal(0);
		this.tradeVolume = value;
		this.receivableMoney = value;
		this.actualMoney = value;
		this.allCost = value;
		this.waitingUsing = 0;
		this.serviceMoney = value;
		this.refundMoney = value;
		this.orderNum = 0;
		this.personNum = 0;
	}
	
	
}
