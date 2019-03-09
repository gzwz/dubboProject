package cn.qumiandan.web.frontServer.ticket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.ticket.api.IQualificationTicketService;
import cn.qumiandan.ticket.vo.AgentQueryTicketByUserIdVO;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.ticket.vo.QueryTicketParamsVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.web.frontServer.ticket.entity.request.AgentQueryTicketByUserIdParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.BackStageMakeOverBatchTicketParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.CreateTicketParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.DelQualificationTicketParmas;
import cn.qumiandan.web.frontServer.ticket.entity.request.GetTicketByIdParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.GetTicketByUserIdParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.MakeOverBatchTicketParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.MakeOverSingleTicketParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.QueryTicketParams;
import cn.qumiandan.web.frontServer.ticket.entity.request.ShopIdParams;

@RestController
@RequestMapping("/ticket/")
public class TicketController {

	@Reference
	private IQualificationTicketService  qualificationTicketService;

	@Reference
	private IVerifyCodeService verifyCodeService;
	
	/**
	 * 总后台删除资格券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("delQualificationTicketByIds")
	public Result<Void> delQualificationTicketByIds(@Valid @RequestBody(required = false)DelQualificationTicketParmas params, 
			BindingResult bindingResult){
		qualificationTicketService.delQualificationTicketByIds(params.getIds());
		return ResultUtils.success("删除成功！");
	}
	
	/**
	 * 创建资格券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("createTicket")
	public Result<List<QualificationTicketVO>> createTicket(@Valid @RequestBody(required = false)CreateTicketParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QualificationTicketVO qualificationTicketVO = CopyBeanUtil.copyBean(params, QualificationTicketVO.class);
		List<QualificationTicketVO> qualificationTicketVOs =  qualificationTicketService.createTicket(params.getNum(), qualificationTicketVO);
		Result<List<QualificationTicketVO>> result = ResultUtils.success("资格券创建成功");
		result.setData(qualificationTicketVOs);
		return result;
	}
	
	
	/**
	 * 根据资格券id查询资格券详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getTicketById")
	public Result<QualificationTicketVO> getTicketById(@Valid @RequestBody(required = false)GetTicketByIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QualificationTicketVO qualificationTicketVO =  qualificationTicketService.getTicketById(params.getTicketId());
		Result<QualificationTicketVO> result = ResultUtils.success();
		result.setData(qualificationTicketVO);
		return result;
	}
	
	/**
	 * 根据用户id查询资格券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getTicketByUserId")
	public Result<List<QualificationTicketVO>> getTicketByUserId(@Valid @RequestBody(required = false)
	GetTicketByUserIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
	//	List<QualificationTicketVO> qualificationTicketVOs = 
	//			qualificationTicketService.getTicketByUserId(ShiroUtils.getUserId(),params.getStatus());
	// TODO 春节期间 暂时改成分页的，下面一个方法就是重载分页的 ，之后替换回来	
		PageInfo<QualificationTicketVO> ticketPageByUserId = qualificationTicketService.getTicketPageByUserId(ShiroUtils.getUserId(),params.getStatus(),params.getPageNum(),params.getPageSize());
		
		Result<List<QualificationTicketVO>> result = ResultUtils.success();
		result.setData(ticketPageByUserId.getList());
		return result;
		
		/* 之后替换成这个 删除 上面的代码
		 * if (bindingResult.hasErrors()) { return
		 * ResultUtils.paramsError(bindingResult); } List<QualificationTicketVO>
		 * qualificationTicketVOs =
		 * qualificationTicketService.getTicketByUserId(ShiroUtils.getUserId(),params.
		 * getStatus()); Result<List<QualificationTicketVO>> result =
		 * ResultUtils.success(); result.setData(qualificationTicketVOs); return result;
		 */
	}
	
	/**
	 * 根据用户id查询资格券（分页）
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getTicketPageByUserId")
	public Result<PageInfo<QualificationTicketVO>> getTicketPageByUserId(@Valid @RequestBody(required = false)GetTicketByUserIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		PageInfo<QualificationTicketVO> qualificationTicketVOs = 
				qualificationTicketService.getTicketPageByUserId(ShiroUtils.getUserId(),params.getStatus(),params.getPageNum(),params.getPageSize());
		Result<PageInfo<QualificationTicketVO>> result = ResultUtils.success();
		result.setData(qualificationTicketVOs);
		return result;
	}
	
	
	/**
	 * 资格券转让(单个转让），mobile为接受转让者手机号
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("makeOverSingleTicket")
	public Result<Void> makeOverSingleTicket(@Valid @RequestBody(required = false)MakeOverSingleTicketParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		boolean flag = verifyCodeService.validationVerifyCodeAndMobile(params.getMobile(),params.getCode());
		if(!flag) {
			return ResultUtils.error("短信验证码错误");
		}
		qualificationTicketService.makeOverSingleTicket(params.getTicketId(),params.getTargetMobil());
		return ResultUtils.success("资格券转让成功");
	}
	
	/**
	 * 
	 * 资格券转让(批量转让），mobile为接受转让者手机号
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("makeOverBatchTicket")
	public Result<Void> makeOverBatchTicket(@Valid @RequestBody(required = false)MakeOverBatchTicketParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		boolean flag = verifyCodeService.validationVerifyCodeAndMobile(params.getMobile(),params.getCode());
		if(!flag) {
			return ResultUtils.error("短信验证码错误");
		}
		qualificationTicketService.makeOverBatchTicket(params.getTicketIdList(),params.getTargetMobil());
		return ResultUtils.success("资格券转让成功");
	}
	
	/**
	 * 总后台划券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("backStageMakeOverBatchTicket")
	public Result<Void> backStageMakeOverBatchTicket(@Valid @RequestBody(required = false)BackStageMakeOverBatchTicketParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		qualificationTicketService.makeOverBatchTicket(params.getTicketIdList(),params.getTargetMobil());
		return ResultUtils.success("资格券转让成功");
	}
	
	/**
	 * 总后台查询资格券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("queryTicket")
	public Result<PageInfo<QualificationTicketVO>> queryTicket(@Valid @RequestBody(required = false)QueryTicketParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QueryTicketParamsVO paramsVO = new QueryTicketParamsVO();
		if(params != null) {
			paramsVO = CopyBeanUtil.copyBean(params, QueryTicketParamsVO.class);
		}
		PageInfo<QualificationTicketVO> queryTicket = qualificationTicketService.queryTicket(paramsVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<QualificationTicketVO>> result = ResultUtils.success();
		result.setData(queryTicket);
		return result;
	}
	
	/**
	 * 上线根据下线用户id查询资格券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("agentQueryTicketByUserId")
	public Result<PageInfo<QualificationTicketVO>> agentQueryTicketByUserId(@Valid @RequestBody(required = false)AgentQueryTicketByUserIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		AgentQueryTicketByUserIdVO agentQueryTicketByUserIdVO = CopyBeanUtil.copyBean(params, AgentQueryTicketByUserIdVO.class);
		agentQueryTicketByUserIdVO.setAgentUserId(ShiroUtils.getUserId());
		PageInfo<QualificationTicketVO> queryTicket = qualificationTicketService.agentQueryTicketByUserId(agentQueryTicketByUserIdVO);
		Result<PageInfo<QualificationTicketVO>> result = ResultUtils.success();
		result.setData(queryTicket);
		return result;
	}
	
	/**
	 * 根据店铺id查询资格券
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getQualificationTicketByShopId")
	public Result<QualificationTicketVO> getQualificationTicketByShopId (@Valid @RequestBody(required = false)
	ShopIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		QualificationTicketVO qualificationTicket = qualificationTicketService.getQualificationTicketByShopId(params.getShopId());
		return ResultUtils.success(qualificationTicket);
	}
}
