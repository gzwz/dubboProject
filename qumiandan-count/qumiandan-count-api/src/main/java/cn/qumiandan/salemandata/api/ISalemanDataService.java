package cn.qumiandan.salemandata.api;

import java.math.BigDecimal;

/**
 * 业务员端统计
 * @author lrj
 *
 */
public interface ISalemanDataService {

	/**
	 * 业务员端今日利润
	 * @param userId
	 * @return
	 */
	BigDecimal todayProfit(Long userId);
}
