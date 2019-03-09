package cn.qumiandan.payaccount.api;

import java.math.BigDecimal;
import java.util.List;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.payaccount.vo.AcountMoneyVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;

/**
 * 支付账户service
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午10:30:07
 */
public interface IPayAccountService extends IBaseService {

	/**
	 * 验证商户账户是否存在
	 * true: 已存在
	 * false: 不在
	 * @param account
	 * @return
	 */
	boolean existAccount(String account);
	
	/**
	 * 添加支付账户
	 * @param vo
	 * @return
	 */
	PayAccountVO addPayAccount(PayAccountVO vo);
	
	/**
	 * 更新支付账户信息
	 * @param vo
	 * @return
	 */
	void updatePayAccount(PayAccountVO vo);
	
	/**
	 * 根据用户id查询支付账户信息
	 * @param userId
	 * @return
	 */
	PayAccountVO getPayAccountByUserId(Long userId);
	
	/**
	 * 根据店铺id查询支付账户信息
	 * @param userId
	 * @return
	 */
	PayAccountVO getPayAccountByShopId(Long shopId);
	
	
	/**
	 * 根据店铺id和用户id获取账户信息
	 * @param userId
	 * @param shopId
	 * @return
	 */
	PayAccountVO getPayAccountByUserIdAndShopId(Long userId, Long shopId);
	
	/**
	 * 根据店铺id集合获取账户信息
	 * @param shopIds
	 * @return
	 */
	List<PayAccountVO> getPayAccountByShopIds(List<Long> shopIds);
	
	
	/**
	 * 根据id获取账户信息
	 * @param id
	 * @return
	 */
	PayAccountVO getPayAccountById(Long id);
	
	/**
	 * 增加某个账户累计总金额
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void increaseBalanceById(Long id, BigDecimal increaseValue);
	
	/**
	 * 增加某个账户可提金额
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void increaseSettBalanceById(Long id, BigDecimal increaseValue);
	
	/**
	 * 增加某账户冻结金额
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void increaseUnbalanceById(Long id, BigDecimal increaseValue);
	
	/**
	 * 增加某个账户的账户余额
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void increaseAccountBalanceById(Long id, BigDecimal increaseValue);
	
	/**
	 * 增加某个账户累计总金额和增加账户的余额
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void increaseBalanceAndAccountBalance(Long id, BigDecimal increaseValue);
	
	/**
	 * 减少某个账户的总余额
	 * @param id
	 * @param decreaseValue
	 */
	void decreaseBalanceById(Long id, BigDecimal decreaseValue);
	
	/**
	 * 增加某个账户累计总金额,账户金额,可提现余额 
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void decreaseBalanceAndAccountBalanceAndSettBalanceById(Long id, BigDecimal desreaseValue);
	
	
	/**
	 * 减少某个账户金额,可提现余额 
	 * @param id 账户id
	 * @param increaseValue 增加值
	 */
	void decreaseAccountBalanceAndSettBalanceById(Long id, BigDecimal desreaseValue);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++ 账户业务接口 ++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 *
	 * 处理订单增 减 账户余额异常
	 * 重复处理账户增减失败
	 * 
	 * 考虑并发付 退款 等情况 这个方法处理对订单的账户入账 出账计算
	 * 具体实现为 处理各业务的do方法中
	 *
	 * 
	 * doProcessAccountIncreaseException  处理订单付款并发异常 
	 * doProcessAccountRefundException	    处理订单退款并发异常 (游戏订单不支持退款)
	 * 
	 * 
	 * @see cn.qumiandan.payaccount.api.process.process.IAccountProcess impl
	 * @param data
	 */
	//void processAccountIncreaseOrDecreaseException(TradeDataVO data);
	
	/**
	 * 
	 * 计算订单支付后 各个商家分润 金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param shopId		 	店铺id
	 * @param orderId		 	订单id
	 * @param gameOrderId		游戏订单id
	 * @param outTradeNo		利楚订单为唯一标识
	 * @param increaseValue	 	入账金额
	 * @param isGame		 	是否为游戏订单
	 * @param operateDate	 	操作时间
	 *//*
	@Deprecated
	void calcPayedCallbackProfitAndIncreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, BigDecimal increaseValue, Boolean isGame, Date operateDate);
	
	*//**
	 * 
	 * 普通订单商家分润
	 * 
	 * 计算普通订单支付后 各个商家分润 金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param shopId		 	店铺id
	 * @param orderId		 	订单id
	 * @param gameOrderId		游戏订单id
	 * @param outTradeNo		利楚订单为唯一标识
	 * @param increaseValue	 	入账金额
	 * @param operateDate	 	操作时间
	 *//*
	void calcPayedCallbackProfitOrderAndIncreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, BigDecimal increaseValue, Date operateDate);
	
	*//**
	 * 
	 * 游戏订单支付分润
	 * 
	 * 计算游戏订单支付后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param shopId		 	店铺id
	 * @param orderId		 	订单id
	 * @param gameOrderId		游戏订单id
	 * @param outTradeNo		利楚订单为唯一标识
	 * @param increaseValue	 	入账金额
	 * @param operateDate	 	操作时间
	 *//*
	void calcPayedCallbackProfitGameOrderAndIncreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, BigDecimal increaseValue, Date operateDate);
	
	
	*//**
	 * 
	 * 计算普通订单退款后各个商家分润
	 * 
	 * 计算普通订单退款后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单退款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺出账信息
	 * 3.处理业务员的当次出账信息
	 * 4.递归处理 市级 省级 平台 账户的出账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:*******************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录出账分润流水 
	 *		会导致分润链上的流水不完整 
	 *	  *******************************************************************************
	 * 
	 * @param shopAccountId 	 店铺账户id
	 * @param shopId		 	 店铺id
	 * @param orderId		 	 订单id
	 * @param gameOrderId	 	 游戏订单id
	 * @param outTradeNo		 利楚订单为唯一标识
	 * @param outRefundNo		 退款利楚订单唯一标识
	 * @param decreaseValue		 退款金额
	 * @param payChannel		 支付方式
	 * @param operateDate		 操作时间
	 *//*
	@Deprecated
	void calcRefundProfitAndDecreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, String outRefundNo, BigDecimal decreaseValue, String payChannel, Boolean isVT, Date operateDate);
	
	
	
	*//**
	 * 
	 * 计算普通订单已支付但未核销,退款的各个商家分润情况
	 * 
	 * 计算普通订单退款后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单退款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺出账信息
	 * 3.处理业务员的当次出账信息
	 * 4.递归处理 市级 省级 平台 账户的出账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:*******************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录出账分润流水 
	 *		会导致分润链上的流水不完整 
	 *	  *******************************************************************************
	 * 
	 * @param shopAccountId 	 店铺账户id
	 * @param shopId		 	 店铺id
	 * @param orderId		 	 订单id
	 * @param gameOrderId	 	 游戏订单id
	 * @param outTradeNo		 利楚订单为唯一标识
	 * @param outRefundNo		 退款利楚订单唯一标识
	 * @param decreaseValue		 退款金额
	 * @param payChannel		 支付方式
	 * @param operateDate		 操作时间
	 *//*
	void calcOrderPayedRefundProfitAndDecreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, String outRefundNo, BigDecimal decreaseValue, String payChannel, Date operateDate);
	
	
	*//**
	 * 
	 * 计算普通订单已支付并核销,退款的各个商家分润情况
	 * 
	 * 计算普通订单退款后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单退款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺出账信息
	 * 3.处理业务员的当次出账信息
	 * 4.递归处理 市级 省级 平台 账户的出账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:*******************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录出账分润流水 
	 *		会导致分润链上的流水不完整 
	 *	  *******************************************************************************
	 * 
	 * @param shopAccountId 	 店铺账户id
	 * @param shopId		 	 店铺id
	 * @param orderId		 	 订单id
	 * @param gameOrderId	 	 游戏订单id
	 * @param outTradeNo		 利楚订单为唯一标识
	 * @param outRefundNo		 退款利楚订单唯一标识
	 * @param decreaseValue		 退款金额
	 * @param payChannel		 支付方式
	 * @param operateDate		 操作时间
	 *//*
	void calcOrderValidationTicketRefundProfitAndDecreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, String outRefundNo, BigDecimal decreaseValue, String payChannel, Date operateDate);
	
	
	*//**
	 * 
	 * 计算订单核销后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param orderId		 	订单id
	 * @param operateDate	 	操作时间
	 *//*
	void calcValidationTicketProfitIncreaseAccountBalance(Long shopId, String orderId, Date operateDate);*/
	
	
	/**
	 * 根据总店管理员查询账户资产信息
	 * @param salemanId
	 * @return
	 */
	List<AcountMoneyVO> getAcountMoneyByShopAdminId(Long shopAdminId);
	
	
	
	
	/**
	 * 
	 * 普通订单商家分润
	 * 
	 * 计算普通订单支付后 各个商家分润 金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param shopId		 	店铺id
	 * @param orderId		 	订单id
	 * @param gameOrderId		游戏订单id
	 * @param outTradeNo		利楚订单为唯一标识
	 * @param increaseValue	 	入账金额
	 * @param operateDate	 	操作时间
	 */
	void calcPayedCallbackProfitOrderAndIncreaseBalance(TradeDataVO data);
	
	
	
	/**
	 * 
	 * 游戏订单支付分润
	 * 
	 * 计算游戏订单支付后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param shopId		 	店铺id
	 * @param orderId		 	订单id
	 * @param gameOrderId		游戏订单id
	 * @param outTradeNo		利楚订单为唯一标识
	 * @param increaseValue	 	入账金额
	 * @param operateDate	 	操作时间
	 */
	void calcPayedCallbackProfitGameOrderAndIncreaseBalance(TradeDataVO data);
	
	
	/**
	 * 
	 * 计算订单核销后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单付款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺入账信息
	 * 3.处理业务员的当次入账信息
	 * 4.递归处理 市级 省级 平台 账户的入账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:********************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录分润流水 
	 *		会导致分润链上的流水不完整
	 *	  ********************************************************************************
	 * 
	 * @param shopAccountId	 	店铺账户id
	 * @param orderId		 	订单id
	 * @param operateDate	 	操作时间
	 */
	void calcValidationTicketProfitIncreaseAccountBalance(TradeDataVO data);
	
	
	/**
	 * 
	 * 计算普通订单已支付但未核销,退款的各个商家分润情况
	 * 
	 * 计算普通订单退款后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单退款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺出账信息
	 * 3.处理业务员的当次出账信息
	 * 4.递归处理 市级 省级 平台 账户的出账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:*******************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录出账分润流水 
	 *		会导致分润链上的流水不完整 
	 *	  *******************************************************************************
	 * 
	 * @param shopAccountId 	 店铺账户id
	 * @param shopId		 	 店铺id
	 * @param orderId		 	 订单id
	 * @param gameOrderId	 	 游戏订单id
	 * @param outTradeNo		 利楚订单为唯一标识
	 * @param outRefundNo		 退款利楚订单唯一标识
	 * @param decreaseValue		 退款金额
	 * @param payChannel		 支付方式
	 * @param operateDate		 操作时间
	 */
	void calcOrderPayedRefundProfitAndDecreaseBalance(TradeDataVO data);
	
	/**
	 * 
	 * 计算普通订单已支付并核销,退款的各个商家分润情况
	 * 
	 * 计算普通订单退款后各个商家分润金额单位为(分)
	 * 计算平台 业务员等获利情况
	 * 订单退款使用
	 * 
	 * 
	 * 1.计算 平台 和 店铺商家收入
	 * 2.处理店铺出账信息
	 * 3.处理业务员的当次出账信息
	 * 4.递归处理 市级 省级 平台 账户的出账信息 
	 * (因业务员的查询条件为业务id所以未放入递归)
	 * 
	 * 注:*******************************************************************************
	 *	  1.因扫呗舍弃我们"分"后面的小数  所以计算分润时直接舍弃小数位
	 *	  2.其中 业务员到省级 计算分润入账时  因公司决定 计算出的分润 == 0的金额 不记录出账分润流水 
	 *		会导致分润链上的流水不完整 
	 *	  *******************************************************************************
	 * 
	 * @param shopAccountId 	 店铺账户id
	 * @param shopId		 	 店铺id
	 * @param orderId		 	 订单id
	 * @param gameOrderId	 	 游戏订单id
	 * @param outTradeNo		 利楚订单为唯一标识
	 * @param outRefundNo		 退款利楚订单唯一标识
	 * @param decreaseValue		 退款金额
	 * @param payChannel		 支付方式
	 * @param operateDate		 操作时间
	 */
	void calcOrderValidationTicketRefundProfitAndDecreaseBalance(TradeDataVO data);
	//---------------------------------------------------------------------------------------------------
	//------------------------------ 以下接口涉及数据库在InnoDB 行级锁操作(慎用) ----------------------------
	//---------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------
	
	
	
}
