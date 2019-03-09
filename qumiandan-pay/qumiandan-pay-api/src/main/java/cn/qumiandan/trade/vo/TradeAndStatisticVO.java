package cn.qumiandan.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;

import lombok.Data;
/**
 * 查询流水列表返回参数
 * @author lrj
 *
 */
@Data
public class TradeAndStatisticVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 流水信息
	 */
	PageInfo<QueryTradeDetailResponseParams> detailResponseParams;
	
	/**
	 * 入账
	 */
	private BigDecimal inAccount;
	
	/**
	 * 出账
	 */
	private BigDecimal outAccount;
}
