package cn.qumiandan.counttask.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.qumiandan.counttask.api.IDlorSaleStatsService;
import cn.qumiandan.counttask.api.IPlatformStatsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 晚上4点30分 跑批任务
 * @author yuleidian
 * @date 2019年1月25日
 */
@Slf4j
@Component
public class CountStatsTaskScheduled {

	@Autowired
	private IPlatformStatsService platformStatsService;
	@Autowired
	private IDlorSaleStatsService dlorSaleStatsService;
	
	
	@Scheduled(cron = "0 30 04 ? * *")
	public void statsTask() {
		log.info("--------------------------晚上4点30分 跑批任务 开始--------------------------------------------");
		platformStatsService.taskQuery();
		log.info("--------------------------晚上4点30分 跑批任务 结束--------------------------------------------");
	}
	
	
	@Scheduled(cron = "0 00 05 ? * *")
	public void statsSalemanTask() {
		log.info("--------------------------晚上5点00分 跑批任务 开始--------------------------------------------");
		dlorSaleStatsService.taskQuery();
		log.info("--------------------------晚上5点00分 跑批任务 结束--------------------------------------------");
		
	}
}
