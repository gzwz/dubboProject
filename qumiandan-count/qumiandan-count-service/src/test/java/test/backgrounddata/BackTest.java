package test.backgrounddata;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.backgrounddata.api.IPlatformStatsQueryService;
import cn.qumiandan.backgrounddata.vo.StatVO;
import test.BaseTest;

public class BackTest extends BaseTest{

	@Autowired
	private IPlatformStatsQueryService queryService;
	
//	@Autowired
//	private PlatformStatsMapper platformStatsMapper ;
//	
	@Test
	public void backGroundDataService() {
		List<StatVO> amount = queryService.queryReceivedAmount();
		System.out.println(amount);
	}
	
	@Test
	public void test() {
//		Date startTime = DateUtil.getDateByStr("2018-01-01 08:00:00");
//		Date endTime = DateUtil.getDateByStr("2019-01-31 08:00:00");
//		List<StatVO> gameWinAmount = platformStatsMapper.queryGameWinAmount(startTime, endTime);
//		List<StatVO> queryMerchantCost = platformStatsMapper.queryMerchantCost(startTime, endTime);
//		List<StatVO> queryPlatformProfit = platformStatsMapper.queryPlatformProfit(startTime, endTime);
//		List<StatVO> queryReceivedAmount = platformStatsMapper.queryReceivedAmount(startTime, endTime);
//		List<StatVO> queryServiceFee = platformStatsMapper.queryServiceFee(startTime, endTime);
//		List<StatVO> queryGameWithdrawAmount = platformStatsMapper.queryGameWithdrawAmount(startTime, endTime);
//		List<StatVO> queryGameWinAmount = platformStatsMapper.queryGameWinAmount(startTime, endTime);
		System.out.println("ok");
	}
}
