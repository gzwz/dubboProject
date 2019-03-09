package test.agentdata;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.agentdata.api.IAgentDataService;
import cn.qumiandan.agentdata.vo.AgentIndexParamsVO;
import cn.qumiandan.agentdata.vo.AgentIndexVO;
import cn.qumiandan.utils.DateUtil;
import test.BaseTest;

public class AgentDataTest extends BaseTest{

	@Resource
	private IAgentDataService agentService;
	@Test
	public void getAgentIndexData() {
		AgentIndexParamsVO agentIndexParamsVO = new AgentIndexParamsVO();
		agentIndexParamsVO.setUserId(67L);
		agentIndexParamsVO.setStartTime(DateUtil.StringToDate("2019-01-01 08:00:00"));
		agentIndexParamsVO.setEndTime(DateUtil.StringToDate("2019-01-30 08:00:00"));
		AgentIndexVO agentIndexData = agentService.getAgentIndexData(agentIndexParamsVO);
		System.out.println(agentIndexData);
	}

	
}
