package test.backgrounddata;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.backgrounddata.api.IBackGroundDataService;
import cn.qumiandan.backgrounddata.vo.MoneyStatisticsVO;
import cn.qumiandan.backgrounddata.vo.NumberStatisticsVO;
import cn.qumiandan.utils.DateUtil;
import test.BaseTest;

public class BackGroundDataTest extends BaseTest{

	@Autowired
	private IBackGroundDataService backGroundDataService;
	
	@Test
	public void backGroundDataService() {
		MoneyStatisticsVO moneyStatistics = backGroundDataService.getMoneyStatistics(DateUtil.StringToDate("2018-01-01 08:00:00"),DateUtil.StringToDate("2019-01-30 08:00:00"));
		System.out.println(moneyStatistics);
		NumberStatisticsVO numberStatistics = backGroundDataService.getNumberStatistics(DateUtil.StringToDate("2018-01-01 08:00:00"),DateUtil.StringToDate("2019-01-30 08:00:00"));
		System.out.println(numberStatistics);
	}
}
