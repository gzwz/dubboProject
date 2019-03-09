/*package cn.qumiandan.jobhandler;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.api.IPayAccountScheduleService;
*//**
 * 定时批量操作可提现余额 任务
 * @author yuleidian
 * @version 创建时间：2019年1月7日 下午2:15:52
 *//*
@Component("batchIncreaseSett")
@JobHandler(value = "batchIncreaseSettBalanceScheduledHandler")
public class BatchIncreaseSettBalanceScheduledHandler extends IJobHandler {

	@Reference
	private IPayAccountScheduleService payAccountScheduleService;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		try {
			payAccountScheduleService.batchIncreaseSettBalanceScheduled();
		} catch (Exception e) {
			if (e instanceof QmdException) {
				QmdException ex = (QmdException) e;
				if (PayErrorCode.PAY7000.getCode().equals(ex.getCode())) {
					// 跑批失败
					return IJobHandler.FAIL;
				}
			}
		}
		return IJobHandler.SUCCESS;
	}

}
*/