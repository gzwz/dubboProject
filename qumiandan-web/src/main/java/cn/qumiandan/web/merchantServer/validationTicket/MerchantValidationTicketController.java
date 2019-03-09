package cn.qumiandan.web.merchantServer.validationTicket;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;
import cn.qumiandan.web.merchantServer.validationTicket.entity.request.ValidationTicketParams;

/**
 * 商家核销券
 * @author W
 * 2018年12月30日
 */
@RestController
@RequestMapping("/merchantValidationTicket/")
public class MerchantValidationTicketController {

	@Reference
	private IValidationTicketService verifyCodeService;
	
	//@ValidateShopManager
	@RequestMapping("useValTicketForMerchant")
	public Result<ValidationTicketVO>  useValTicketsForTicketCode(@Valid @RequestBody(required = false)
	ValidationTicketParams ticket,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ValidationTicketVO ticketVO = verifyCodeService.useValTicketsForMerchentUserIdAndTicketCode(ShiroUtils.getUserId(),ticket.getTicketCode());
		Result<ValidationTicketVO> result = ResultUtils.success();
		if (result != null) {
			result.setData(ticketVO);
			result.setSuccess(true);
		}
		return result ;
	}
}
