package cn.qumiandan.web.pay.withdrawcash.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.annotation.ValidateShopManager;
import cn.qumiandan.common.annotation.ValidateShopsManager;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.pay.withdraw.api.IWithdrawCashService;
import cn.qumiandan.pay.withdraw.vo.QueryResponseParamsVO;
import cn.qumiandan.pay.withdraw.vo.QueryWithdrawCashParamsVO;
import cn.qumiandan.pay.withdraw.vo.SalemanLevelApplyWithdrawCaseResultVO;
import cn.qumiandan.pay.withdraw.vo.ShopApplyWithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashResultVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.DateUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.pay.withdrawcash.entity.request.ApplyAuditPassParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.ApplyAuditUnPassParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.BackstageQueryWithdrawCashParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.ManualWithdrawCashParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.PayCallbackParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.QueryWithdrawCashParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.ShopApplyCashParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.ShopOneKeyApplyCashParams;
import cn.qumiandan.web.pay.withdrawcash.entity.request.WithdrawCashDetailParams;
import cn.qumiandan.web.pay.withdrawcash.entity.response.QueryResponseParams;
import cn.qumiandan.web.pay.withdrawcash.entity.response.WithdrawCashVO2Params;

@RestController
@RequestMapping(value="/withdraw/")
public class WithdrawCashController {
	
	@Reference
	private IWithdrawCashService withdrawCashService;
	
	@Reference
	private IPayAccountService payAccountService;
	/**
	 * 店铺管理员申请单个提现申请
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@ValidateShopManager
	@RequestMapping("shopApplyCash")
	public Result<ShopApplyWithdrawCashResultVO> applyCash(@Valid @RequestBody(required = false) ShopApplyCashParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopApplyWithdrawCashResultVO shopApplyCash = withdrawCashService.shopApplyCash(params.getShopId(),ShiroUtils.getUserId());
		Result<ShopApplyWithdrawCashResultVO> result = ResultUtils.success("提现申请成功");
		result.setData(shopApplyCash);
		return result;
	}
	
	/**
	 * 店铺管理员一键提现
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@ValidateShopsManager
	@RequestMapping("shopOneKeyApplyCash")
	public Result<List<ShopApplyWithdrawCashResultVO>> shopOneKeyApplyCash(@Valid @RequestBody(required = false) ShopOneKeyApplyCashParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<ShopApplyWithdrawCashResultVO> shopOneKeyApplyCash = withdrawCashService.shopOneKeyApplyCash(params.getShopIds(),ShiroUtils.getUserId());
		Result<List<ShopApplyWithdrawCashResultVO>> result = ResultUtils.success("一键提现申请成功");
		result.setData(shopOneKeyApplyCash);
		return result;
	}
	
	/**
	 * 业务员 市 省 申请提现
	 * @return
	 */
	@ValidateShopManager
	@RequestMapping("salemanLevelApplyCash")
	public Result<SalemanLevelApplyWithdrawCaseResultVO> salemanLevelApplyCash(){
		SalemanLevelApplyWithdrawCaseResultVO salemanLevelApplyCash = withdrawCashService.salemanLevelApplyCash(ShiroUtils.getUserId());
		Result<SalemanLevelApplyWithdrawCaseResultVO> result =  ResultUtils.success("提现成功");
		result.setData(salemanLevelApplyCash);
		return result;
	}
	
	/**
	 * 提现申请审核通过
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="applyAutitPass")
	public Result<Void> applyAuditPass(@Valid @RequestBody(required = false)ApplyAuditPassParams params,BindingResult bindingResult)throws Exception{
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		WithdrawCashVo withdrawCashVo = new WithdrawCashVo();
		withdrawCashVo.setId(params.getId());
		withdrawCashVo.setAuditorId(ShiroUtils.getUserId());
		withdrawCashVo.setAuditorDate(DateUtil.dateFormat(new Date()));
		withdrawCashVo.setStatus(StatusEnum.TRUE.getCode());
		WithdrawCashResultVO applyAudit = withdrawCashService.applyAudit(withdrawCashVo);
		if(applyAudit != null ) {
			if(applyAudit.getSuccess()) {
				return ResultUtils.success(applyAudit.getMessage());
			}
			else {
				return ResultUtils.error(applyAudit.getMessage());
			}
		}	
		return ResultUtils.error("审核失败");
	}
	

	/**
	 * 提现申请审核未通过
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/applyAuditUnPass")
	public Result<Void> applyAuditUnPass(@Valid @RequestBody(required = false)ApplyAuditUnPassParams params,BindingResult bindingResult)throws Exception{
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		WithdrawCashVo withdrawCashVo = new WithdrawCashVo();
		withdrawCashVo.setId(params.getId());
		withdrawCashVo.setAuditorId(ShiroUtils.getUserId());
		withdrawCashVo.setAuditorDate(DateUtil.dateFormat(new Date()));
		withdrawCashVo.setRemarkAudit(params.getRemarkAudit());
		withdrawCashVo.setStatus(StatusEnum.FALSE.getCode());
		withdrawCashService.applyAudit(withdrawCashVo);
		return ResultUtils.success("审核未通过");
	}
	/**
	 * 支付成功回调函数
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payCallback")
	public Result<Void> payCallback(@Valid @RequestBody(required = false)PayCallbackParams params,BindingResult bindingResult)throws Exception{
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		WithdrawCashVo bean = CopyBeanUtil.copyBean(params, WithdrawCashVo.class);
		withdrawCashService.payCallback(bean);
		return ResultUtils.success("回调成功");
		
	}
	
	/**
	 * 查询提现申请记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@ValidateShopsManager
	@RequestMapping("queryWithdrawCash")
	public Result<QueryResponseParams> queryWithdrawCash(@Valid @RequestBody(required = false)QueryWithdrawCashParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryWithdrawCashParamsVO withdrawCashVo = CopyBeanUtil.copyBean(params, QueryWithdrawCashParamsVO.class);
		QueryResponseParamsVO queryWithdrawCash = withdrawCashService.queryWithdrawCash(withdrawCashVo);
		QueryResponseParams queryResponseParams = setQueryResponseParams(queryWithdrawCash);
		return ResultUtils.success(queryResponseParams);
		
	}
	
	/**
	 * 总后台查询提现申请记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("backstageQueryWithdrawCash")
	public Result<QueryResponseParams> backstageQueryWithdrawCash(@Valid @RequestBody(required = false)BackstageQueryWithdrawCashParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryWithdrawCashParamsVO withdrawCashVo = CopyBeanUtil.copyBean(params, QueryWithdrawCashParamsVO.class);
		QueryResponseParamsVO queryWithdrawCash = withdrawCashService.queryWithdrawCash(withdrawCashVo);
		QueryResponseParams queryResponseParams = setQueryResponseParams(queryWithdrawCash);
		return ResultUtils.success(queryResponseParams);
	}
	
	/**
	 * 业务员端查询提现记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("agentQueryWithdrawCash")
	public Result<QueryResponseParams> agentQueryWithdrawCash(@Valid @RequestBody(required = false)QueryWithdrawCashParamsVO params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(ShiroUtils.getUserId());
		if(payAccountByUserId == null) {
			return ResultUtils.success();
		}
		params.setAccountIdList(Lists.newArrayList(payAccountByUserId.getId()));
		QueryWithdrawCashParamsVO withdrawCashVo = CopyBeanUtil.copyBean(params, QueryWithdrawCashParamsVO.class);
		QueryResponseParamsVO queryWithdrawCash = withdrawCashService.queryWithdrawCash(withdrawCashVo);
		QueryResponseParams queryResponseParams = setQueryResponseParams(queryWithdrawCash);
		return ResultUtils.success(queryResponseParams);
	}
	
	private QueryResponseParams setQueryResponseParams(QueryResponseParamsVO paramsVO) {
		QueryResponseParams params = new QueryResponseParams();
		if(!ObjectUtils.isEmpty(paramsVO) ) {
			params.setSumWithdrawalAmount(params.getSumWithdrawalAmount());
			if(!ObjectUtils.isEmpty(paramsVO.getPageInfo()) && !ObjectUtils.isEmpty(paramsVO.getPageInfo().getList())) {
				List<WithdrawCashVO2Params> cashVO2s = CopyBeanUtil.copyList(paramsVO.getPageInfo().getList(), WithdrawCashVO2Params.class);
				PageInfo<WithdrawCashVO2Params>	pageInfo = new PageInfo<>(cashVO2s);
				params.setPageInfo(pageInfo);		
			}
			
		}
		return params;
	}
	
	/**
	 * 修改提现申请为手动打款的标识
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("manualWithdrawCash")
	public Result<Void> manualWithdrawCash(@Valid @RequestBody(required = false)ManualWithdrawCashParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		withdrawCashService.manualWithdrawCash(params.getId(), ShiroUtils.getUserId());
		return ResultUtils.success("修改成功");
	}
	
	/**
	 * 查询提现申请详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getWithdrawCashDetail")
	public Result<WithdrawCashVo> getWithdrawCashDetail(@Valid @RequestBody(required = false)WithdrawCashDetailParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		WithdrawCashVo detail = withdrawCashService.getWithdrawCashInfoById(params.getId());
		return ResultUtils.success(detail);
	}
}
