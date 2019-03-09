package test.counttask;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.counttask.api.IPlatformStatsService;
import cn.qumiandan.counttask.vo.PlatformStatQueryVO;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;

@Slf4j
public class PlatformStat2Test extends BaseTest {

	@Autowired
	private IPlatformStatsService platformStatsService;

	
	@Test
	public void query() {
		CommonParams params = new CommonParams();
		Calendar time = Calendar.getInstance();
		time.set(2019, 0, time.getMinimum(Calendar.DATE));
		params.setStartTime(time.getTime());
		time.set(2019, 0, time.getMaximum(Calendar.DATE));
		params.setEndTime(time.getTime());
		log.info("----------------------------------------------------------------------------------------------------");
		List<PlatformStatQueryVO> shopStatPageForShop = platformStatsService.getShopStatForShop(params);
		log.info("----------------------------------------------------------------------------------------------------");
		shopStatPageForShop.stream().forEach(System.out::println);
	}
}
