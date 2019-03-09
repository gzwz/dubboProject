package cn.qumiandan.payaccount.calcprofit.calcimpl;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.enums.PayTypeEnum;
import cn.qumiandan.payaccount.calcprofit.AbstractProcessCalcProfitExecutor;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.enums.TradeTypeEnums;
import cn.qumiandan.trade.vo.TradeDetailVO;

/**
 * 普通订单核销后退款个级分润退还
 * @author yuleidian
 * @version 创建时间：2019年1月14日 上午11:25:03
 */
@Service
@Component("orderValidationTicketRefundProfitExecutor")
public class OrderValidationTicketRefundProfitExecutor extends AbstractProcessCalcProfitExecutor {

	@Reference
	protected ISalemanService salemanService;
	
	@Reference
	protected IShopProfitService shopProfitService;

	@Override
	public void run(String... args) throws Exception {
		super.shopProfitService = this.shopProfitService;
		super.salemanService = this.salemanService;
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	protected void doProcessShopAccountBalanceAndPrintTrade(TradeDataVO data, BigDecimal profit) {
		// 店铺实际入账
		BigDecimal shopDecreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 平台获利
		platformIncreaseValue = (shopDecreaseValue.add(data.getDecreaseValue()).multiply(profit)).setScale(0, BigDecimal.ROUND_HALF_UP);
		// 店铺除账金额
		shopDecreaseValue = (data.getDecreaseValue().subtract(platformIncreaseValue)).setScale(0, BigDecimal.ROUND_HALF_UP);
		
		// 店铺除账(退款减款)
		payAccountService.decreaseBalanceAndAccountBalanceAndSettBalanceById(data.getShopAccountId(), shopDecreaseValue);
		// 记录店铺除账流水 (退款流水)
		tradeDetailService.addTransferOutRefund(data.getShopAccountId(), null, data.getOrderId(), data.getGameOrderId(), data.getOutTradeNo(), data.getDecreaseValue(), data.getPayChannal(), data.getOperateDate(), TradeDetailTypeEnum.SHOPORDERREFUND);
		// 记录店铺被平台手续费入账流水
		tradeDetailService.addTransferIn(data.getShopAccountId(), null, data.getOrderId(), data.getGameOrderId(), data.getOutTradeNo(), platformIncreaseValue, data.getOperateDate(), TradeDetailTypeEnum.PALFORMORDERREFUND);
	
		data.setPlatformIncreaseValue(platformIncreaseValue);
		data.setPlatformActualValue(platformIncreaseValue);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	protected void doProcessSalemenAccountBalanceAndPrintTrade(TradeDataVO data, SalemanTypeEnums type) {
		TradeDetailTypeEnum tradeType = null;
		switch (type) {
			case Saleman:
				// 记录营业员入账流水记录
				tradeType = TradeDetailTypeEnum.SALEMANREFUND;
				break;
			case CountryAgent:
				// 记录区级入账流水记录
				tradeType = TradeDetailTypeEnum.COUNTRYREFUND;
				break;
			case CityAgent:
				// 记录市级入账流水记录
				tradeType = TradeDetailTypeEnum.CITYREFUND;
				break;
			case ProAgent:
				// 记录省级入账流水记录
				tradeType = TradeDetailTypeEnum.PROVINCEREFUND;
				break;
		default:
			break;
		}
		if (Objects.nonNull(tradeType)) {
			// 除账
			payAccountService.decreaseBalanceAndAccountBalanceAndSettBalanceById(data.getSalemanAccountId(), data.getSalemanIncreaseValue());
			// 记录市级 省级 账流水记录
			tradeDetailService.addTransferOutRefund(data.getSalemanAccountId(), null, data.getOrderId(), data.getGameOrderId(), data.getOutTradeNo(), data.getSalemanIncreaseValue(), data.getPayChannal(), data.getOperateDate(), tradeType);
		}
	}

	@Override
	protected void doProcessPlatformActualPrintTrade(TradeDataVO data) {
		TradeDetailVO trade = new TradeDetailVO();
		trade.setAccountInId(0L);
		trade.setAccountOutId(data.getShopAccountId());
		trade.setSerialNo(data.getOrderId());
		trade.setGameSerialNo(data.getGameOrderId());
		trade.setAmount(data.getPlatformActualValue().negate());
		trade.setCallbackAmount(data.getPlatformActualValue());
		trade.setTradeType(TradeTypeEnums.TRANSFERIN.getCode());
		trade.setPayChannel(PayTypeEnum.PlatformPay.getCode());
		trade.setType(TradeDetailTypeEnum.PLATFORMORDERACTUALREFUND.getCode());
		trade.setProductName(TradeDetailTypeEnum.PLATFORMORDERACTUALREFUND.getName());
		trade.setThirdTradeName("平台实际出账金额");
		trade.setOutTradeNo(data.getOutTradeNo());
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		trade.setCreateDate(data.getOperateDate());
		trade.setPayDate(data.getOperateDate());
		tradeDetailService.addTradeDetail(trade);
	}
}
