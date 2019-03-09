package cn.qumiandan.web.frontServer.validationTicket;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.params.PageInfoParams;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;

/**
 * 核销券
 * @author W
 * 2018年12月30日
 */
@RestController
@RequestMapping("/validationTicket/")
public class ValidationTicketController {

	@Reference
	private IValidationTicketService verifyCodeService;
	
	
	@RequestMapping("getValTicketsForUser")
	public Result<PageInfo<ValidationTicketVO>>  getValTicketsForUser(@Valid @RequestBody(required = false)
	PageInfoParams page,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		PageInfo<ValidationTicketVO> resultData = verifyCodeService.getValTicketsForUserId(ShiroUtils.getUserId(), page.getPageNum(),page.getPageSize());
		Result<PageInfo<ValidationTicketVO>> result = ResultUtils.success();
		if (resultData != null) {
			result.setData(resultData);
			result.setSuccess(true);
		}
		return result ;
	}
}
