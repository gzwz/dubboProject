package cn.qumiandan.payaccount.calcprofit.calcimpl;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.calcprofit.AbstractProcessCalcProfitExecutor;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.shopprofit.api.IShopProfitService;
/**
 * 普通订单各级入账处理
 * @author yuleidian
 * @version 创建时间：2019年1月11日 下午5:32:56
 */
@Service
@Component("orderProfitExcutor")
public class OrderProfitExecutor extends AbstractProcessCalcProfitExecutor {

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
	protected void doProcessShopAccountBalanceAndPrintTrade(TradeDataVO data,  BigDecimal profit) {
		// 店铺实际入账
		BigDecimal shopIncreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 平台获利
		platformIncreaseValue = (shopIncreaseValue.add(data.getIncreaseValue()).multiply(profit)).setScale(0, BigDecimal.ROUND_HALF_UP);
		// 店铺可入账
		shopIncreaseValue = (data.getIncreaseValue().subtract(platformIncreaseValue)).setScale(0, BigDecimal.ROUND_HALF_UP);
		// 店铺入账
		payAccountService.increaseBalanceById(data.getShopAccountId(), shopIncreaseValue);
		
		data.setPlatformIncreaseValue(platformIncreaseValue);
		data.setPlatformActualValue(platformIncreaseValue);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	protected void doProcessSalemenAccountBalanceAndPrintTrade(TradeDataVO data, SalemanTypeEnums type) {
		// 入账
		payAccountService.increaseBalanceById(data.getSalemanAccountId(), data.getSalemanIncreaseValue());
	}


	@Override
	protected void doProcessPlatformActualPrintTrade(TradeDataVO data) {
		// donothing
	}
}
