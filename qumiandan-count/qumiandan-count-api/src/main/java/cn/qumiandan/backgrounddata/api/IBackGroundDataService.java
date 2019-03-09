package cn.qumiandan.backgrounddata.api;

import java.util.Date;

import cn.qumiandan.backgrounddata.vo.MoneyStatisticsVO;
import cn.qumiandan.backgrounddata.vo.NumberStatisticsVO;
/**
 * 总后台统计接口
 * @author lrj
 *
 */
public interface IBackGroundDataService {

	/**
	 *  查询用户、店铺、业务员、代理数量参数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	NumberStatisticsVO getNumberStatistics(Date startTime,Date endTime );

	/**
	 *  查询服务费、实收金额、支付笔数、利润
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	MoneyStatisticsVO getMoneyStatistics(Date startTime,Date endTime );
}
