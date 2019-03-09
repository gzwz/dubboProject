package cn.qumiandan.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 账户流水统计参数类
 * @author lrj
 *
 */
@Data
public class TradeStatisticsVO implements Serializable{


	private static final long serialVersionUID = 1L;

	/**
	 * 入账
	 */
	private BigDecimal inAccount;
	
	/**
	 * 出账
	 */
	private BigDecimal outAccount;
}
