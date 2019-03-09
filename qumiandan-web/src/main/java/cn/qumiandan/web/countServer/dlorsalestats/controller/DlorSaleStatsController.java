package cn.qumiandan.web.countServer.dlorsalestats.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.counttask.api.IDlorSaleStatsService;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryResultVO;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.countServer.dlorsalestats.entity.request.GetSaleStatListParams;

@RestController
@RequestMapping(value="/dlorSaleStats/")
public class DlorSaleStatsController {
	
	@Reference
	private IDlorSaleStatsService dlorSaleStatsService;
	
	/**
	 *  代理端业务员端统计
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getSaleStatList")
	public Result<List<DlorSaleStatQueryResultVO>> getSaleStatList(@Valid @RequestBody(required = false)GetSaleStatListParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		DlorSaleStatQueryVO vo = CopyBeanUtil.copyBean(params, DlorSaleStatQueryVO.class);
		vo.setUserId(ShiroUtils.getUserId());
		List<DlorSaleStatQueryResultVO> saleStatList = dlorSaleStatsService.getSaleStatList(vo);
		return ResultUtils.success(saleStatList);
	}
}
