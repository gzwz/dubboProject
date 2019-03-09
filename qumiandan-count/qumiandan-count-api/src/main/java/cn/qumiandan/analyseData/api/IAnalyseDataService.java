package cn.qumiandan.analyseData.api;

import java.math.BigDecimal;
import java.util.Date;
// TODO
public interface IAnalyseDataService {
	/**
	 * 统计当日收入接口
	 * @param date需要统计的具体日期
	 * @return
	 */
	BigDecimal actualAmount(Date date); 
	/**
	 * 统计支付笔数接口
	 * @param date需要统计的具体日期
	 * @return
	 */
	Long totalPayNum();
}
