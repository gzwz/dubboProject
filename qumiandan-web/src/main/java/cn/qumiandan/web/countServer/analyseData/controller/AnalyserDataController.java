package cn.qumiandan.web.countServer.analyseData.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.analyseData.api.IAnalyseDataService;
import cn.qumiandan.analyseData.vo.AnalyseDataVO;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.countServer.analyseData.entity.request.AnalyseDataParams;
import cn.qumiandan.web.countServer.analyseData.entity.response.AnalyseDataResponseParams;
@RestController
@RequestMapping(value="/count/")
public class AnalyserDataController {
	@Reference
	private IAnalyseDataService analyseDataService;
	/**
	 * 数据分析统计
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/analyseData")
	public Result<AnalyseDataResponseParams> analyseData(@Valid @RequestBody(required = false)AnalyseDataParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		
		BigDecimal amount = analyseDataService.actualAmount(params.getTime());
		Long payNum = analyseDataService.totalPayNum();
		AnalyseDataVO dataVO=new AnalyseDataVO();
		dataVO.setPayNum(payNum);
		dataVO.setActualAmount(amount);
		AnalyseDataResponseParams bean = CopyBeanUtil.copyBean(dataVO, AnalyseDataResponseParams.class);
		return ResultUtils.success(bean);
	}
}
