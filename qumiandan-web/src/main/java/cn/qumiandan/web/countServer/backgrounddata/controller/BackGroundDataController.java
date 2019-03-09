package cn.qumiandan.web.countServer.backgrounddata.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.backgrounddata.api.IBackGroundDataService;
import cn.qumiandan.backgrounddata.vo.MoneyStatisticsVO;
import cn.qumiandan.backgrounddata.vo.NumberStatisticsVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.countServer.backgrounddata.entity.request.DateParams;

/**
 * 总后台统计
 * @author lrj
 *
 */
@RestController
@RequestMapping(value="/backGroundData/")
public class BackGroundDataController {

	@Reference
	private IBackGroundDataService backGroundDataService;
	
	/**
	 *  查询服务费、实收金额、支付笔数、利润
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getMoneyStatistics")
	public Result<MoneyStatisticsVO> getMoneyStatistics(@Valid @RequestBody(required = false)DateParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		MoneyStatisticsVO moneyStatistics = backGroundDataService.getMoneyStatistics(params.getStartTime(), params.getEndTime());
		return ResultUtils.success(moneyStatistics);
	}
	
	/**
	 *  查询用户、店铺、业务员、代理数量参数
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getNumberStatistics")
	public Result<NumberStatisticsVO> getNumberStatistics(@Valid @RequestBody(required = false)DateParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		NumberStatisticsVO numberStatistics = backGroundDataService.getNumberStatistics(params.getStartTime(), params.getEndTime());
		return ResultUtils.success(numberStatistics);
	}
}
