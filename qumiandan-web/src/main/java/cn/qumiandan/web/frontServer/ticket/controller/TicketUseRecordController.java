package cn.qumiandan.web.frontServer.ticket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.ticket.api.ITicketUseRecordService;
import cn.qumiandan.ticket.vo.QueryRecordVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.ticket.entity.request.GetTicketByIdParams;

/**
 * 资格券转让记录
 * @author lrj
 *
 */
@RestController
@RequestMapping("/ticketUseRecord/")
public class TicketUseRecordController {

	@Reference
	private ITicketUseRecordService ticketUseRecordService ;
	
	/**
	 * 根据资格券id查询资格券转让记录
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getTicketUseRecordByTicketId")
	public Result<List<QueryRecordVO>> getTicketUseRecordByTicketId(@Valid @RequestBody(required = false)GetTicketByIdParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<QueryRecordVO> ticketUseRecordByTicketId = ticketUseRecordService.getTicketUseRecordByTicketId(params.getTicketId());
		Result<List<QueryRecordVO>> result = ResultUtils.success();
		result.setData(ticketUseRecordByTicketId);
		return result;
	}
}
