package cn.qumiandan.web.pay.payaccount.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.pay.payaccount.entity.request.ShopIdParams;
/**
 * 银行卡管理控制器
 * @author lrj
 *
 */
@RestController
@RequestMapping("/bankCard/")
public class BankCardController {

	@Reference
	private IBankCardService bankCardService; 
	
	/**
	 * 根据店铺id查询账户银行卡信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getBankCardByShopId")
	public Result<BankCardVO> getBankCardByShopId(@Valid @RequestBody(required = false) ShopIdParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		BankCardVO bankCardByShopId = bankCardService.getBankCardByShopId(params.getShopId());
		return ResultUtils.success(bankCardByShopId);
	}
}
