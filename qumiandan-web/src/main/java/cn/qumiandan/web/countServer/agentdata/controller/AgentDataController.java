package cn.qumiandan.web.countServer.agentdata.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.agentdata.api.IAgentDataService;
import cn.qumiandan.agentdata.vo.AgentIndexParamsVO;
import cn.qumiandan.agentdata.vo.AgentIndexVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.countServer.agentdata.entity.request.AgentIndexDataParams;

@RestController
@RequestMapping("/agentData/")
public class AgentDataController {
	
	@Reference
	private IAgentDataService agentDataService;
	
	/**
	 * 代理端首页统计
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getAgentIndexData")
	public Result<AgentIndexVO> getAgentIndexData(@Valid @RequestBody(required = false) AgentIndexDataParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		AgentIndexParamsVO paramsVO = CopyBeanUtil.copyBean(params, AgentIndexParamsVO.class);
		paramsVO.setUserId(ShiroUtils.getUserId());
		AgentIndexVO agentIndexData = agentDataService.getAgentIndexData(paramsVO);
		return ResultUtils.success(agentIndexData);
	}
//	
//	/**
//	 * 服务费
//	 * @param params
//	 * @param bindingResult
//	 * @return
//	 */
//	@RequestMapping("getServiceCharge")
//	public Result<BigDecimal> getServiceCharge(@Valid @RequestBody(required = false) AgentIndexDataParams params,
//			BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			return ResultUtils.paramsError(bindingResult);
//		}
//		AgentIndexParamsVO paramsVO = CopyBeanUtil.copyBean(params, AgentIndexParamsVO.class);
//		paramsVO.setUserId(ShiroUtils.getUserId());
//		BigDecimal tradeVolume = agentDataService.getServiceCharge(paramsVO);
//		return ResultUtils.success(tradeVolume);
//	}
}
