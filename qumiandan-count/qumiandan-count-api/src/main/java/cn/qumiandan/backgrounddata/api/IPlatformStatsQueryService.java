package cn.qumiandan.backgrounddata.api;

import java.util.List;

import cn.qumiandan.backgrounddata.vo.StatVO;

public interface IPlatformStatsQueryService {

	/**
	 * 店铺实收金额
	 */
	List<StatVO>  queryReceivedAmount();
	/**
	 * 商家成本
	 * @return
	 */
	List<StatVO> queryMerchantCost();
	/**
	 * 商家利润
	 */
	List<StatVO>  queryMerchantProfit();
	/**
	 * 平台利润
	 */
	List<StatVO>  queryPlatformProfit();
	/**
	 * 手续费支出
	 */
	List<StatVO>  queryServiceFee();
	/**
	 * 游戏支付金额
	 */
	List<StatVO>  queryGameAmount();
	/**
	 * 游戏中奖金额
	 */
	List<StatVO>  queryGameWinAmount();
	/**
	 * 提现金额
	 */
	List<StatVO>  queryWithdrawAmount();
	
	/**
	 * 获取所有统计
	 */
	List<StatVO> getAllStats();
	
}
