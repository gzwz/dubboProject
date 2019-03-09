/*package cn.qumiandan.jobhandler;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import cn.qumiandan.counttask.api.IPlatformStatsService;


@Component("platformCountDataHandler")
@JobHandler(value = "platformCountDataHandler")
public class PlatformCountDataHandler extends IJobHandler {

	@Reference
	private IPlatformStatsService platformStatsService;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		// platformStatsService.addPlatformStat(vo);
		return IJobHandler.SUCCESS;
	}

}
*/