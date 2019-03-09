package cn.qumiandan.web.countServer.salemandata.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.salemandata.api.ISalemanDataService;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;

/**
 * 业务员端统计
 * @author lrj
 *
 */
@RestController
@RequestMapping(value="/salemanData/")
public class SalemanDataController {
	@Reference
	private ISalemanDataService salemanDataService;
	
	/**
	 * 今日利润
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("todayProfit")
	public Result<BigDecimal> todayProfit() {
		BigDecimal todayProfit = salemanDataService.todayProfit(ShiroUtils.getUserId());
		return ResultUtils.success(todayProfit);
	}
	
}
