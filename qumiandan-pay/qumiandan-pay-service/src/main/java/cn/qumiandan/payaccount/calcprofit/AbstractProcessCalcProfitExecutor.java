package cn.qumiandan.payaccount.calcprofit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.shopprofit.vo.ShopProfitDetailVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import lombok.extern.slf4j.Slf4j;

/**
 * 计算分润执行器模板抽象
 * @author yuleidian
 * @version 创建时间：2019年1月11日 下午5:26:35
 */
@Slf4j
public abstract class AbstractProcessCalcProfitExecutor implements IProcessCalcProfitExecutor, CommandLineRunner {

	/** 
	 * 基础分润百分比
	 * (注:数据库中存储的分润百分比都 * 1000)
	 */
	protected final static BigDecimal rateBase = new BigDecimal(1000);
	
	protected ISalemanService salemanService;
	
	protected IShopProfitService shopProfitService;
	
	@Autowired
	protected IPayAccountService payAccountService;
	
	@Autowired
	protected ITradeDetailService tradeDetailService;

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void processCalcAndTakeAccountBalance(TradeDataVO data) {
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(data.getShopId());
		if (Objects.nonNull(shopProfit)) {
			// 店铺扣除比例
			BigDecimal profit = shopProfit.getRate().divide(rateBase);
			//--------------------------------------处理店铺----------------------------------------------------
			doProcessShopAccountBalanceAndPrintTrade(data, profit);
			//--------------------------------------处理业务员到省级代理-----------------------------------------
			List<SalemanVO> agentSalemans = salemanService.getAgentSalemenByShopId(data.getShopId());
			if (!CollectionUtils.isEmpty(agentSalemans)) {
				agentSalemans.stream().filter(salemen -> Objects.nonNull(salemen)).forEach((salemen) -> {
					// 营业员账户s
					PayAccountVO salemanAccount = payAccountService.getPayAccountByUserId(salemen.getUserId());
					if (Objects.nonNull(salemanAccount)) {
						// 业务员费率
						BigDecimal salemanProfit = salemen.getRate().divide(rateBase);
						// 业务员可分得金额
						BigDecimal salemanIncreaseValue = (data.getPlatformIncreaseValue().multiply(salemanProfit)).setScale(1, BigDecimal.ROUND_HALF_UP);
						if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
							data.setSalemanAccountId(salemanAccount.getId());
							data.setSalemanIncreaseValue(salemanIncreaseValue);
							data.setPlatformActualValue(data.getPlatformActualValue().subtract(salemanIncreaseValue));
							doProcessSalemenAccountBalanceAndPrintTrade(data, SalemanTypeEnums.getType(salemen.getType()));
						}
					}
				});
			}
			doProcessPlatformActualPrintTrade(data);
		} else {
			log.error("processCalcAndTakeAccountBalance|shopId:" + data.getShopId() + "|查询到分润百分比为空");
		}
	}
	
	/**
	 * 处理店铺增加或减少金额处理和打印流水信息
	 * @param data
	 * @param shopIncrease
	 */
	protected abstract void doProcessShopAccountBalanceAndPrintTrade(TradeDataVO data, BigDecimal profit);
	
	/**
	 * 处理业务员到省级代理 账户增减和打印流水
	 * @param data
	 * @param type
	 */
	protected abstract void doProcessSalemenAccountBalanceAndPrintTrade(TradeDataVO data, SalemanTypeEnums type);

	/**
	 * 处理平台实际入账流水
	 * @param data
	 * @param type
	 */
	protected abstract void doProcessPlatformActualPrintTrade(TradeDataVO data);
}
