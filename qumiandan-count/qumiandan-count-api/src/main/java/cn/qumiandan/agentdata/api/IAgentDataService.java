package cn.qumiandan.agentdata.api;

import cn.qumiandan.agentdata.vo.AgentIndexParamsVO;
/**
 * 代理端统计接口类
 * @author lrj
 *
 */
import cn.qumiandan.agentdata.vo.AgentIndexVO;
public interface IAgentDataService {

	/**
	 * 代理端首页统计
	 * @param agentIndexParamsVO
	 * @return
	 */
	AgentIndexVO getAgentIndexData(AgentIndexParamsVO agentIndexParamsVO);
}
