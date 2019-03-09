package cn.qumiandan.payaccount.api;

import cn.qumiandan.common.interfaces.IBaseService;

/**
 * 账户定时任务专用API
 * @author yuleidian
 * @version 创建时间：2019年1月7日 下午2:21:49
 */
public interface IPayAccountScheduleService extends IBaseService {

	//---------------------------------------------------------------------------------------------------
	//------------------------------  定时任务接口 (专用接口,请勿随意调用) ----------------------------
	//---------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------
		
	/**
	 * 批量执行增加每日的可提现余额
	 */
	void batchIncreaseSettBalanceScheduled();
}
