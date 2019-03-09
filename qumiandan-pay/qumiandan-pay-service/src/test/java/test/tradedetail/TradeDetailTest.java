package test.tradedetail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.vo.QueryTradeDetailParamsVO;
import cn.qumiandan.trade.vo.TradeAndStatisticVO;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.trade.vo.TradeStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;
@Slf4j
public class TradeDetailTest extends BaseTest{

	@Autowired
	private ITradeDetailService tradeDetailService;
	
	@Test
	public void getTradeResult() {
		log.info("-----------------------------------开始------------------------------------------");
		long start = System.currentTimeMillis();
		System.out.println(tradeDetailService.getTradeResult(1L).getPaySuccess());
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	
	@Test
	public void queryTradeDetail() {
		QueryTradeDetailParamsVO paramsVO = new QueryTradeDetailParamsVO();
		paramsVO.setAccountIds(Lists.newArrayList(36L,37L));
		TradeAndStatisticVO queryTradeDetail = tradeDetailService.queryTradeDetail(paramsVO);
		System.out.println(queryTradeDetail);
	}
	
	@Test
	public void queryTradeStatistics() {
		TradeStatisticsVO queryTradeStatistics = tradeDetailService.queryTradeStatistics(Lists.newArrayList(35L,36L,37L,38L));
		System.out.println(queryTradeStatistics);
	}
	
	@Test
	public void update() {
		TradeDetailVO vo = new TradeDetailVO();
		vo.setId(1084795276308971521L);
		vo.setStatus(new Byte("2"));
		tradeDetailService.updateTradeDetail(vo);;
	}
}
