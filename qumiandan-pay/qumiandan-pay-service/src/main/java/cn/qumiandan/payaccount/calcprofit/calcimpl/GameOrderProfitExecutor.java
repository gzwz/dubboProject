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
 * 游戏订单各级入账处理
 * @author yuleidian
 * @version 创建时间：2019年1月11日 下午5:32:56
 */
@Service
@Component("gameOrderProfitExecutor")
public class GameOrderProfitExecutor extends AbstractProcessCalcProfitExecutor {

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
		BigDecimal shopIncreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 平台获利
		platformIncreaseValue = (shopIncreaseValue.add(data.getIncreaseValue()).multiply(profit)).setScale(0, BigDecimal.ROUND_HALF_UP);
		// 店铺可入账
		shopIncreaseValue = (data.getIncreaseValue().subtract(platformIncreaseValue)).setScale(0, BigDecimal.ROUND_HALF_UP);
		// 店铺入账
		payAccountService.increaseBalanceAndAccountBalance(data.getShopAccountId(), shopIncreaseValue);
		// 记录平台收取店铺手续费转出流水
		tradeDetailService.addTransferOut(data.getShopAccountId(), null, data.getOrderId(), data.getGameOrderId(), data.getOutTradeNo(), platformIncreaseValue, data.getOperateDate(), TradeDetailTypeEnum.PALFORMGAMEORDER);
	
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
				tradeType = TradeDetailTypeEnum.SALEMANGAMEORDER;
				break;
			case CountryAgent:
				// 记录区级入账流水记录
				tradeType = TradeDetailTypeEnum.COUNTRYGAMEORDER;
				break;
			case CityAgent:
				// 记录市级入账流水记录
				tradeType = TradeDetailTypeEnum.CITYGAMEORDER;
				break;
			case ProAgent:
				// 记录省级入账流水记录
				tradeType = TradeDetailTypeEnum.PROVINCEGAMEORDER;
				break;
		default:
			break;
		}
		if (Objects.nonNull(tradeType)) {
			payAccountService.increaseBalanceAndAccountBalance(data.getSalemanAccountId(), data.getSalemanIncreaseValue());
			tradeDetailService.addTransferIn(data.getSalemanAccountId(), null, data.getOrderId(), data.getGameOrderId(), data.getOutTradeNo(), data.getSalemanIncreaseValue(), data.getOperateDate(), tradeType);
		}
	}

	@Override
	protected void doProcessPlatformActualPrintTrade(TradeDataVO data) {
		TradeDetailVO trade = new TradeDetailVO();
		trade.setAccountInId(0L);
		trade.setAccountOutId(data.getShopAccountId());
		trade.setSerialNo(data.getOrderId());
		trade.setGameSerialNo(data.getGameOrderId());
		trade.setAmount(data.getPlatformActualValue());
		trade.setCallbackAmount(data.getPlatformActualValue());
		trade.setTradeType(TradeTypeEnums.TRANSFERIN.getCode());
		trade.setPayChannel(PayTypeEnum.PlatformPay.getCode());
		trade.setType(TradeDetailTypeEnum.PLATFORMGAMEORDERACTUAL.getCode());
		trade.setProductName(TradeDetailTypeEnum.PLATFORMGAMEORDERACTUAL.getName());
		trade.setThirdTradeName("平台实际入账金额");
		trade.setOutTradeNo(data.getOutTradeNo());
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		trade.setCreateDate(data.getOperateDate());
		trade.setPayDate(data.getOperateDate());
		tradeDetailService.addTradeDetail(trade);
	}

}
