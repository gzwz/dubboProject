package cn.qumiandan.agentdata.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 代理端首页统计
 * @author lrj
 *
 */
@Data
public class AgentIndexVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 店铺交易额
	 */
	private BigDecimal tradeVolume;
	
	/**
	 * 店铺手续费
	 */
	private BigDecimal serviceCharge;
	
	/**
	 * 代理分润
	 */
	private BigDecimal shareProfit;
	
	/**
	 * 订单数量
	 */
	private Integer orderNum;
	
	/**
	 * 代理数量
	 */
	private Integer agentNum;
	
	
	/**
	 * 业务员数量
	 */
	private Integer salemanNum;
	
	/**
	 * 店铺数量
	 */
	private Integer shopNum;
	
	/**
	 * 资格券数量
	 */
	private Integer ticketNum;

	public AgentIndexVO() {
		BigDecimal value = new BigDecimal(0);
		this.tradeVolume = value;
		this.serviceCharge = value;
		this.shareProfit = value;
		this.orderNum = 0;
		this.agentNum = 0;
		this.salemanNum = 0;
		this.shopNum = 0;
		this.ticketNum = 0;
	}
	
}
