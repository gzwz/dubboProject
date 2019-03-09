package cn.qumiandan.payaccount.calcprofit;

import cn.qumiandan.payaccount.vo.TradeDataVO;

/**
 * 
 * ---------------使用模板方法计算各级分润信息-------------
 * 
 * 计算分润执行器
 * @author yuleidian
 * @version 创建时间：2019年1月11日 下午5:23:16
 */
public interface IProcessCalcProfitExecutor {

	/**
	 * 执行计算并记录
	 * 1.查询店铺分润
	 * 2.计算店铺分润入账并打印流水
	 * 3.计算业务员---->省级代理分润入账并打印流水
	 */
	void processCalcAndTakeAccountBalance(TradeDataVO data);
	
}
