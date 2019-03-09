package cn.qumiandan.pay.saobei.service;

import cn.qumiandan.pay.saobei.vo.response.merchant.callback.CreateMerchatCallbackVO;
import cn.qumiandan.pay.saobei.vo.response.pay.callback.JSPayCallbackVO;

/**
 * 处理扫呗回调service
 * @author yuleidian
 * @version 创建时间：2018年12月12日 上午10:48:42
 */
public interface ISaobeiCallbackService {

	/**
	 * 创建商户回调
	 * @param vo
	 * @return
	 */
	int createMerchantCallback(CreateMerchatCallbackVO vo);
	
	/**
	 * 支付普通订单回调接口
	 * 当用户在移动端支付成功后,扫呗回调设定的接口地址,内部请求此接口做相应处理
	 * 
	 * 1.验证订单明细 防止接口被外部恶意请求
	 * 2.处理订单流水
	 * 3.处理分润
	 * 4.处理订单状态
	 * 因考虑并发情况 ,在处理入账时采用乐观锁，高并发时入账失败率很好
	 * 目前采用捕获入账异常，在队列中尝试重复处理逻辑
	 * 
	 * 
	 * @see cn.qumiandan.payaccount.process.IAccountProcess->FailAccountProcess
	 * @see cn.qumiandan.payaccount.process.AccountTask->IncreaseBalanceTask
	 * @param vo
	 * @return
	 */
	int jsPayCallback(JSPayCallbackVO vo);
	
	
	/**
	 * 
	 * 支付游戏订单回调接口
	 * 当用户在移动端支付成功后,扫呗回调设定的接口地址,内部请求此接口做相应处理
	 * 1.验证订单明细 防止接口被外部恶意请求
	 * 2.处理订单流水
	 * 3.处理分润
	 * 4.处理订单状态
	 * 
	 * 因考虑并发情况 ,在处理入账时采用乐观锁，高并发时入账失败率很好
	 * 目前采用捕获入账异常，在队列中尝试重复处理逻辑
	 * 
	 * 
	 * @see cn.qumiandan.payaccount.process.IAccountProcess->FailAccountProcess
	 * @see cn.qumiandan.payaccount.process.AccountTask->IncreaseBalanceTask
	 * @param vo
	 * @return
	 */
	int jsPayCallbackGame(JSPayCallbackVO vo);
}
