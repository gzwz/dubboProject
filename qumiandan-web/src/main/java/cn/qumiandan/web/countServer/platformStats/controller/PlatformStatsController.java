package cn.qumiandan.web.countServer.platformStats.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.annotation.ValidateShopsManager;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.counttask.api.IPlatformStatsService;
import cn.qumiandan.counttask.vo.AdminStatQueryVO;
import cn.qumiandan.counttask.vo.DLStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatQueryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.countServer.platformStats.entity.request.GetShopStatPageForDLParams;
import cn.qumiandan.web.countServer.platformStats.entity.request.PlatformStatQueryParams;
import cn.qumiandan.web.countServer.platformStats.entity.request.ShopStatParams;

@RestController
@RequestMapping(value="/platformStats/")
public class PlatformStatsController {
	
	@Reference
	private IPlatformStatsService service;
	
	@RequestMapping(value="queryStats")
	public Result<List<PlatformStatQueryVO>> queryStats(@Valid @RequestBody(required = false)
	PlatformStatQueryParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		AdminStatQueryVO vo = CopyBeanUtil.copyBean(params, AdminStatQueryVO.class);
		List<PlatformStatQueryVO> platformStatPage = service.getPlatformStatPage(vo);
		Result<List<PlatformStatQueryVO>> result = ResultUtils.success();
		result.setData(platformStatPage);
		return result;
	}

	
	@ValidateShopsManager
	@RequestMapping("getShopStatForShop")
	public Result<List<PlatformStatQueryVO>> getShopStatForShop(@Valid @RequestBody(required = false)ShopStatParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		CommonParams condition = CopyBeanUtil.copyBean(params, CommonParams.class);
		List<PlatformStatQueryVO> resultList = service.getShopStatForShop(condition);
		return ResultUtils.success(resultList);
	}
	
	/**
	 * 代理端查询统计
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="getShopStatPageForDL")
	public Result<List<PlatformStatQueryVO>> getShopStatPageForDL(@Valid @RequestBody(required = false)
	GetShopStatPageForDLParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		DLStatQueryVO vo = CopyBeanUtil.copyBean(params, DLStatQueryVO.class);
		List<PlatformStatQueryVO> shopStatPageForDL = service.getShopStatPageForDL(vo);
		Result<List<PlatformStatQueryVO>> result = ResultUtils.success();
		result.setData(shopStatPageForDL);
		return result;
	}
}
