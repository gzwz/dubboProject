package cn.qumiandan.web.pay.tradedetail.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;

import cn.qumiandan.common.annotation.ValidateShopsManager;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.vo.QueryTradeDetailParamsVO;
import cn.qumiandan.trade.vo.TradeAndStatisticVO;
import cn.qumiandan.trade.vo.TradeStatisticsVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.pay.tradedetail.entity.request.QueryTradeDetailParams;
import cn.qumiandan.web.pay.tradedetail.entity.request.UserQueryTradeDetailParams;

@RestController
@RequestMapping(value="tradeDetail")
public class TradeDetailController {
	@Reference
	private ITradeDetailService detailService;
	
	/**
	 * 流水记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@ValidateShopsManager
	@RequestMapping("queryTradeDetail")
	public Result<TradeAndStatisticVO> queryTradeDetail(@Valid @RequestBody(required = false) QueryTradeDetailParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryTradeDetailParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryTradeDetailParamsVO.class);
		if(ObjectUtils.isEmpty(paramsVO.getStatusList())) {
			paramsVO.setStatusList(Lists.newArrayList(TradePayStatusEnum.PAYED.getCode(),TradePayStatusEnum.REFUND.getCode()));
		}
		TradeAndStatisticVO andStatisticVO  = detailService.queryTradeDetail(paramsVO);
		Result<TradeAndStatisticVO> result = ResultUtils.success();
		result.setData(andStatisticVO);
		return result;
	}
	
	/**
	 * 用户查询流水
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("userQueryTradeDetail")
	public Result<TradeAndStatisticVO> userQueryTradeDetail(@Valid @RequestBody(required = false) UserQueryTradeDetailParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryTradeDetailParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryTradeDetailParamsVO.class);
		paramsVO.setUserId(ShiroUtils.getUserId());
		TradeAndStatisticVO pageInfo= detailService.queryTradeDetail(paramsVO);
		Result<TradeAndStatisticVO> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
	}
	
	
	/**
	 * 总店管理员账户id集合查询账户流水统计
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@ValidateShopsManager
	@RequestMapping("shopAdminQueryTradeStatistics")
	public Result<TradeStatisticsVO> shopAdminQueryTradeStatistics(@Valid @RequestBody(required = false) QueryTradeDetailParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryTradeDetailParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryTradeDetailParamsVO.class);
		TradeStatisticsVO queryTradeStatistics = detailService.queryTradeStatistics(paramsVO.getAccountIds());
		Result<TradeStatisticsVO> result = ResultUtils.success();
		result.setData(queryTradeStatistics);
		return result;
	}
}
