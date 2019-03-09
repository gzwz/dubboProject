package cn.qumiandan.trade.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.trade.vo.QueryTradeDetailParamsVO;
import cn.qumiandan.trade.vo.TradeAndStatisticVO;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.trade.vo.TradeResultVO;
import cn.qumiandan.trade.vo.TradeStatisticsVO;

/**
 * 交易流水service
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:35:38
 */
public interface ITradeDetailService extends IBaseService {

	/**
	 * ****************************************************************************
	 * 目前所有流水记录操作账户id，只保存了accountInId
	 * 无论入账,出账都只记录accountInId 查询或统计时只需用这个id作为条件  
	 * 判断是入账还是出账 需要用type字段区分
	 * 
	 * accountOutId暂时没有使用,此id为以后平台内部账户和账户转账使用
	 * **************************************************************************** 
	 * 
	 * 
	 * 添加交易流水信息
	 * @param vo
	 * @return
	 */
	TradeDetailVO addTradeDetail(TradeDetailVO vo);
	
	/**
	 * ****************************************************************************
	 * 目前所有流水记录操作账户id，只保存了accountInId
	 * 无论入账,出账都只记录accountInId 查询或统计时只需用这个id作为条件
	 * 判断是入账还是出账 需要用type字段区分
	 * 
	 * accountOutId暂时没有使用,此id为以后平台内部账户和账户转账使用
	 * **************************************************************************** 
	 * 
	 * 
	 * 更新流水信息
	 * @param vo
	 */
	void updateTradeDetail(TradeDetailVO vo);
	
	
	/**
	 * 根据id查询流水信息
	 * @param id
	 * @return
	 */
	TradeDetailVO getTradeDetailById(Long id);
	
	/**
	 * 查询此流水是否支付成功
	 * 
	 * paySuccess true: 支付成功
	 * paySuccess false: 未支付成功
	 * @param tradeId
	 * @return
	 */
	TradeResultVO getTradeResult(Long tradeId);
	
	/**
	 * 根据普通订单id查询已支付流水
	 * @param orderId	订单id
	 * @param type		流水类型
	 * @return
	 */
	TradeDetailVO getTradeDetailByOrderIdAndType(String orderId, TradeDetailTypeEnum type);
	
	/**
	 * 根据普通订单id查询已支付流水
	 * @param orderId
	 * @param types
	 * @return
	 */
	TradeDetailVO getPayedOrderTradeDetail(String orderId);
	
	//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
	//—————————————————————————————————————————————————添加水流记录——————————————————————————————————————————————————————————————————————————————
	//——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
	
	/**
	 * ****************************************************************************
	 * 目前所有流水记录操作账户id，只保存了accountInId
	 * 无论入账,出账都只记录accountInId 查询或统计时只需用这个id作为条件
	 * 判断是入账还是出账 需要用type字段区分
	 * 
	 * accountOutId暂时没有使用,此id为以后平台内部账户和账户转账使用
	 * **************************************************************************** 
	 * 注:内部转账使用
	 * 
	 * 添加转出流水
	 * @param accountInId 转入账户
	 * @param accountOutId 转出账户
	 * @param serialNo	订单号
	 * @param gameSerialNo	游戏订单号
	 * @param outTradeNo	第三方订单号
	 * @param increaseValue	转入金额 请填写正数
	 * @param operateDate	操作时间
	 * @param TradeDetailTypeEnum type 流水类型
	 * @return
	 */
	public TradeDetailVO addTransferIn(Long accountInId, Long accountOutId, String serialNo, String gameSerialNo, String outTradeNo, BigDecimal increaseValue, Date operateDate, TradeDetailTypeEnum type);
	
	
	/**
	 * ****************************************************************************
	 * 目前所有流水记录操作账户id，只保存了accountInId
	 * 无论入账,出账都只记录accountInId 查询或统计时只需用这个id作为条件
	 * 判断是入账还是出账 需要用type字段区分
	 * 
	 * accountOutId暂时没有使用,此id为以后平台内部账户和账户转账使用
	 * **************************************************************************** 
	 * 注:内部转账使用
	 * 
	 * 添加转出流水
	 * @param accountInId 转入账户
	 * @param accountOutId 转出账户
	 * @param serialNo	订单号
	 * @param gameSerialNo	游戏订单号
	 * @param outTradeNo	第三方订单号
	 * @param decreaseValue	转出金额 请填写正数(系统统一刷入数据库时 为负数)
	 * @param operateDate	操作时间
	 * @param TradeDetailTypeEnum type 流水类型
	 * @return
	 */
	TradeDetailVO addTransferOut(Long accountInId, Long accountOutId, String serialNo, String gameSerialNo, String outTradeNo, BigDecimal decreaseValue, Date operateDate, TradeDetailTypeEnum type);
	
	
	/**
	 * 
	 * ****************************************************************************
	 * 目前所有流水记录操作账户id，只保存了accountInId
	 * 无论入账,出账都只记录accountInId 查询或统计时只需用这个id作为条件
	 * 判断是入账还是出账 需要用type字段区分
	 * 
	 * accountOutId暂时没有使用,此id为以后平台内部账户和账户转账使用
	 * **************************************************************************** 
	 * 添加退款流水接口
	 * @param accountInId
	 * @param accountOutId
	 * @param serialNo
	 * @param gameSerialNo
	 * @param outTradeNo
	 * @param decreaseValue
	 * @param operateDate
	 * @param TradeDetailTypeEnum type 流水类型
	 * @return
	 */
	TradeDetailVO addTransferOutRefund(Long accountInId, Long accountOutId, String serialNo, String gameSerialNo, String outTradeNo, BigDecimal decreaseValue, String payChannel, Date operateDate, TradeDetailTypeEnum type);
	
	
	/**
	 * ****************************************************************************
	 * 目前所有流水记录操作账户id，只保存了accountInId
	 * 无论入账,出账都只记录accountInId 查询或统计时只需用这个id作为条件
	 * 判断是入账还是出账 需要用type字段区分
	 * 
	 * accountOutId暂时没有使用,此id为以后平台内部账户和账户转账使用
	 * **************************************************************************** 
	 * 添加提现流水记录
	 * @param accountInId
	 * @param accountOutId
	 * @param decreaseValue
	 * @param payChannel
	 * @param operateDate
	 * @param type
	 * @return
	 */
	TradeDetailVO addTransferWithDrawCash(Long accountInId, Long accountOutId, BigDecimal decreaseValue, String payChannel, Date operateDate, TradeDetailTypeEnum type);

	/**
	 * 店铺查询流水
	 * @param paramsVO
	 * @return
	 */
	TradeAndStatisticVO queryTradeDetail(QueryTradeDetailParamsVO paramsVO);
	
	/**
	 * 用户账户查询流水
	 * @param paramsVO
	 * @return
	 */
	TradeAndStatisticVO userQueryTradeDetail(QueryTradeDetailParamsVO paramsVO);

	/**
	 * 查询流水统计
	 * @param accountIds
	 * @return
	 */
	TradeStatisticsVO queryTradeStatistics(List<Long> accountIds);
	
	
	
	
	
}
