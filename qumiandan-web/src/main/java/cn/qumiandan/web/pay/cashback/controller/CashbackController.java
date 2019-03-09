package cn.qumiandan.web.pay.cashback.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.pay.cashback.api.ICashbackService;
import cn.qumiandan.pay.cashback.enums.CashbackStatusEnum;
import cn.qumiandan.pay.cashback.vo.CashbackVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackRequestParamsVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackResPonseParamsVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.pay.cashback.entity.request.AddCashbackParams;
import cn.qumiandan.web.pay.cashback.entity.request.AuditCashbackParams;
import cn.qumiandan.web.pay.cashback.entity.request.QueryCashbackRequestParams;

@RestController
@RequestMapping("/cashback/")
public class CashbackController {

	
	@Reference
	private ICashbackService cashbackService;
	
	/**
	 *	申请返现
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addCashback")
	public Result<CashbackVO> addCashback(@Valid @RequestBody(required = false) AddCashbackParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		CashbackVO cashbackVO = CopyBeanUtil.copyBean(params, CashbackVO.class);
		CashbackVO addCashback = cashbackService.addCashback(cashbackVO);
		return ResultUtils.success(addCashback);
	}
	
	/**
	 * 审核返现信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("auditCashback")
	public Result<Boolean> auditCashback(@Valid @RequestBody(required = false) AuditCashbackParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		CashbackVO cashbackVO = CopyBeanUtil.copyBean(params, CashbackVO.class);
		cashbackVO.setUpdateId(ShiroUtils.getUserId());
		cashbackVO.setAuditorId(cashbackVO.getUpdateId());
		Result<Boolean> result;
		if(params.getStatus().equals(StatusEnum.TRUE.getCode())) {
			result = ResultUtils.success("审核成功；审核结果：通过");
			//线下打款，审核通过直接改为打款完成状态
			cashbackVO.setStatus(CashbackStatusEnum.FinishedWithdraw.getCode());
			result.setData(true);
		}else {
			if(params.getRemarkAudit() == null) {
				return  ResultUtils.error("审核未通过时，审核备注不能为空");
			}
			cashbackVO.setStatus(CashbackStatusEnum.AuditUnPass.getCode());
			result = ResultUtils.success("审核成功；审核结果：未通过");
			result.setData(false);
		}
		cashbackService.auditCashback(cashbackVO);
		return result;
	}
	
	/**
	 * 总后台查询返现信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("backStageQueryCashback")
	public Result<PageInfo<QueryCashbackResPonseParamsVO>> backStageQueryCashback(@Valid @RequestBody(required = false) QueryCashbackRequestParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryCashbackRequestParamsVO cashbackVO = CopyBeanUtil.copyBean(params, QueryCashbackRequestParamsVO.class);
		PageInfo<QueryCashbackResPonseParamsVO> queryCashback = cashbackService.queryCashback(cashbackVO);
		return ResultUtils.success(queryCashback);
	}
}
