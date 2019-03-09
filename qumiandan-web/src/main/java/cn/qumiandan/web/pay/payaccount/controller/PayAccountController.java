package cn.qumiandan.web.pay.payaccount.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.AcountMoneyVO;
import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.pay.payaccount.entity.request.ShopIdParams;
import cn.qumiandan.web.pay.payaccount.entity.request.UserIdParams;
import cn.qumiandan.web.pay.payaccount.entity.response.salemanSettlementParams;

@RestController
@RequestMapping("/payAccount/")
public class PayAccountController {
	
	@Reference
	private IPayAccountService payAccountService;
	
	@Reference
	private IBankCardService bankCardService; 
	@Reference
	private ISalemanService salemanService ;
	
	/**
	 * 根据总店管理员查询账户资产信息
	 * @return
	 */
	@RequestMapping("getAcountMoneyByShopAdminId")
    public Result<List<AcountMoneyVO>> getAcountMoneyByShopAdminId(){
		List<AcountMoneyVO> acountMoneyVOs = payAccountService.getAcountMoneyByShopAdminId(ShiroUtils.getUserId());
		return ResultUtils.success(acountMoneyVOs);
    }
	
	/**
	 * 根据用户id查询支付账户信息
	 * @return
	 */
	@RequestMapping("getPayAccountByUserId")
    public Result<PayAccountVO> getPayAccountByUserId(){
		PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(ShiroUtils.getUserId());
		//账户累计总金额字段不返回
		if(payAccountByUserId != null) {
			payAccountByUserId.setBalance(null);
		}
		return ResultUtils.success(payAccountByUserId);
    }
	
	/**
	 * 业务员端结算信息
	 * @return
	 */
	@RequestMapping("salemanSettlementInfo")
    public Result<salemanSettlementParams> salemanSettlementInfo(){
		PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(ShiroUtils.getUserId());
		salemanSettlementParams params = new salemanSettlementParams();
		if(payAccountByUserId != null) {
			//账户累计总金额字段不返回
			payAccountByUserId.setBalance(null);
			BankCardVO bankCardByAccountId = bankCardService.getBankCardByAccountId(payAccountByUserId.getId());
			params.setAccountVO(payAccountByUserId);
			params.setBankCardVO(bankCardByAccountId);
			SalemanVO salemanByUserId = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
			params.setSalemanVO(salemanByUserId);
		}
		return ResultUtils.success(params);
    }
    
    
    /**
	 * 代理端查询下线结算信息
	 * @return
	 */
	@RequestMapping("agentQuerySalemanSettlementInfo")
    public Result<salemanSettlementParams> agentQuerySalemanSettlementInfo(@Valid @RequestBody(required = false) UserIdParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		//判断是否是上下级关系
		if(!salemanService.isOffline(ShiroUtils.getUserId(), params.getUserId())) {
			return ResultUtils.error("当前用户与目标用户非上下级关系");
		}
		PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(params.getUserId());
		salemanSettlementParams	settlementParams = new salemanSettlementParams();
		if(payAccountByUserId != null) {
			//账户累计总金额字段不返回
			payAccountByUserId.setBalance(null);
			BankCardVO bankCardByAccountId = bankCardService.getBankCardByAccountId(payAccountByUserId.getId());
			settlementParams.setAccountVO(payAccountByUserId);
			settlementParams.setBankCardVO(bankCardByAccountId);
			SalemanVO salemanByUserId = salemanService.getSalemanByUserId(params.getUserId());
			settlementParams.setSalemanVO(salemanByUserId);
		}
		return ResultUtils.success(settlementParams);
    }

    
    /**
     * 根据店铺id获取账户详情
     * @param params
     * @param bindingResult
     * @return
     */
	@RequestMapping("getPayAccountByShopId")
    public Result<PayAccountVO> getPayAccountByShopId(@Valid @RequestBody(required = false) ShopIdParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		PayAccountVO accountVO = payAccountService.getPayAccountByShopId(params.getShopId());
		//账户累计总金额字段不返回
//		if(accountVO != null) {
//			accountVO.setBalance(null);
//		}
		return ResultUtils.success(accountVO);
    }
	
	
	
	/**
	 * 总后台查询业务员或代理结算信息
	 * @return
	 */
	@RequestMapping("querySalemanSettlementInfo")
    public Result<salemanSettlementParams> querySalemanSettlementInfo(@Valid @RequestBody(required = false) UserIdParams params,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		PayAccountVO payAccountByUserId = payAccountService.getPayAccountByUserId(params.getUserId());
		salemanSettlementParams	settlementParams = new salemanSettlementParams();
		if(payAccountByUserId != null) {
			//账户累计总金额字段不返回
			payAccountByUserId.setBalance(null);
			BankCardVO bankCardByAccountId = bankCardService.getBankCardByAccountId(payAccountByUserId.getId());
			settlementParams.setAccountVO(payAccountByUserId);
			settlementParams.setBankCardVO(bankCardByAccountId);
			SalemanVO salemanByUserId = salemanService.getSalemanByUserId(params.getUserId());
			settlementParams.setSalemanVO(salemanByUserId);
		}
		return ResultUtils.success(settlementParams);
    }

}
