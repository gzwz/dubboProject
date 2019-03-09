package cn.qumiandan.web.frontServer.order.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.orderreturn.api.IOrderReturnService;
import cn.qumiandan.orderreturn.vo.OrderReturnVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.order.entity.request.AuditPassParams;
import cn.qumiandan.web.frontServer.order.entity.request.AuditUnPassParams;
import cn.qumiandan.web.frontServer.order.entity.request.OrderReturnInfoParams;
import cn.qumiandan.web.frontServer.order.entity.request.OrderReturnParams;
import cn.qumiandan.web.frontServer.order.entity.response.AuditResponseParams;
import cn.qumiandan.web.frontServer.order.entity.response.OrderReturnInfoResponseParams;
import cn.qumiandan.web.frontServer.order.entity.response.OrderReturnResponseParams;

@RestController
@RequestMapping(value="/orderReturn")
public class OrderReturnController {
	@Reference
	private IOrderReturnService orderReturnService;
	/**
	 * 退款申请接口
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/applyReturn")
	public Result<OrderReturnResponseParams> applyReturn(@Valid @RequestBody(required = false) OrderReturnParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderReturnVO orderReturnVO = CopyBeanUtil.copyBean(params, OrderReturnVO.class);
		orderReturnVO.setAuditUser_id(ShiroUtils.getUserId());
		orderReturnVO.setUserId(ShiroUtils.getUserId());
		OrderReturnVO returnVO = orderReturnService.applyReturn(orderReturnVO);
		OrderReturnResponseParams orderReturnResponseParams = CopyBeanUtil.copyBean(returnVO, OrderReturnResponseParams.class);
		return ResultUtils.success(orderReturnResponseParams);
	}
	
	/**
	 * 退款申请审核通过接口
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/auditPass")
	public Result<Void> auditPass(@Valid @RequestBody(required = false) AuditPassParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		orderReturnService.auditPass(params.getId());
		return ResultUtils.success("审核通过");
	}
	/**
	 * 退款申请审核未通过接口
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/auditUnPass")
	public Result<AuditResponseParams> auditUnPass(@Valid @RequestBody(required = false) AuditUnPassParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderReturnVO bean = CopyBeanUtil.copyBean(params, OrderReturnVO.class);
		orderReturnService.auditUnPass(bean);
		return ResultUtils.success("审核未通过");
	}
	/**
	 * 根据订单id查询退款申请信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/getOrderInfoByOrderId")
	public Result<OrderReturnInfoResponseParams> getOrderInfoByOrderId(@Valid @RequestBody(required = false) OrderReturnInfoParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		OrderReturnVO orderReturnVO = orderReturnService.selectByOrderId(params.getOrderId());
		OrderReturnInfoResponseParams bean = CopyBeanUtil.copyBean(orderReturnVO, OrderReturnInfoResponseParams.class);
		return ResultUtils.success(bean);
	}
}
