package cn.qumiandan.web.frontServer.supportpayment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.supportpayment.api.ISupportPaymentService;
import cn.qumiandan.supportpayment.vo.SupportPaymentVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.supportpayment.entity.request.GetSupportPaymentByCodeParams;

/**
 * 支付方式管理
 * @author lrj
 * @version 创建时间：2018年12月10日 17:55
 */
@RestController
@RequestMapping("/supportPayment/")
public class SupportPaymentController {

	@Reference(check = false)
	private ISupportPaymentService supportPaymentService;
	
	/**
	 * 查询所有支付方式
	 * @return
	 */
	@RequestMapping("getAllSupportPayment")
	public Result<List<SupportPaymentVO>> getAllSupportPayment() {
		List<SupportPaymentVO> supportPaymentVOList = supportPaymentService.getAllSupportPayment();
		Result<List<SupportPaymentVO>> result = ResultUtils.success();
		result.setData(supportPaymentVOList);
		return result;
	}
	
	/**
	 * 根据code查询支付方式详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getSupportPaymentByCode")
	public Result<List<SupportPaymentVO>> getSupportPaymentByCode(@Valid @RequestBody(required = false)GetSupportPaymentByCodeParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<SupportPaymentVO> supportPaymentVOList = supportPaymentService.getSupportPaymentByCode(params.getCode());
		Result<List<SupportPaymentVO>> result = ResultUtils.success();
		result.setData(supportPaymentVOList);
		return result;
	}
	
}
