package cn.qumiandan.payaccount.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.api.IPayAccountScheduleService;
import lombok.extern.slf4j.Slf4j;

/**
 * 预留
 * 
 * 每晚12点 批量增加每日可提现余额
 * @author yuleidian
 * @version 创建时间：2019年1月7日 上午11:34:26
 */
@Slf4j
@Component
public class BatchIncreaseSettBalanceScheduled {

	@Autowired
	private IPayAccountScheduleService payAccountScheduleService;
	
	/**
	 * 每晚0点跑批
	 */
	@Scheduled(cron = "0 00 00 ? * *")
	public void batchIncreaseSettBalanceScheduled() {
		try {
			payAccountScheduleService.batchIncreaseSettBalanceScheduled();
		} catch (Exception e) {
			if (e instanceof QmdException) {
				QmdException ex = (QmdException) e;
				if (PayErrorCode.PAY7000.getCode().equals(ex.getCode())) {
					// 跑批失败
				}
			}
			log.info("每晚12点 批量增加每日可提现余额 跑批失败");
		}
	}
}
