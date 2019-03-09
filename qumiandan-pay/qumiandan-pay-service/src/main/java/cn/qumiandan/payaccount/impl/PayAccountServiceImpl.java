package cn.qumiandan.payaccount.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.orderreturn.api.IOrderReturnService;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.calcprofit.IProcessCalcProfitExecutor;
import cn.qumiandan.payaccount.entity.PayAccount;
import cn.qumiandan.payaccount.mapper.PayAccountMapper;
import cn.qumiandan.payaccount.vo.AcountMoneyVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IPayAccountService.class)
public class PayAccountServiceImpl extends ServiceImpl<PayAccountMapper, PayAccount> implements IPayAccountService {

	@Autowired
	private PayAccountMapper payAccountMapper;
	
	@Autowired
	private ITradeDetailService tradeDetailService;
	
	@Reference
	private IShopProfitService shopProfitService;
	
	@Reference
	private ISalemanService salemanService;
	
	@Reference
	private IShopService shopService;
	
	@Reference
	private IOrderService orderService;

	@Reference
	private IGameOrderService gameOrderService;
	
	@Reference
	private IOrderReturnService orderReturnService;
	
	@Reference
	private IValidationTicketService validationTicketService;
	
	@Reference
	private IBankCardService bankCardService ;
	
	@Reference
	private IShopUserService shopUserService;
	
	@Autowired
	@Qualifier("orderProfitExcutor")
	private IProcessCalcProfitExecutor orderProfitExcutor; 
	
	@Autowired
	@Qualifier("gameOrderProfitExecutor")
	private IProcessCalcProfitExecutor gameOrderProfitExecutor; 
	
	@Autowired
	@Qualifier("orderPayedRefundProfitExecutor")
	private IProcessCalcProfitExecutor orderPayedRefundProfitExecutor; 
	
	@Autowired
	@Qualifier("orderValidationTicketProfitExecutor")
	private IProcessCalcProfitExecutor orderValidationTicketProfitExecutor; 
	
	@Autowired
	@Qualifier("orderValidationTicketRefundProfitExecutor")
	private IProcessCalcProfitExecutor orderValidationTicketRefundProfitExecutor; 
	
	@Override
	public boolean existAccount(String account) {
		AssertUtil.isNull(account, "PayAccountServiceImpl|existAccount|传入参数vo为空");
		PayAccount payAccount = payAccountMapper.selectOne(new QueryWrapper<PayAccount>().eq("account", account));
		if (Objects.nonNull(payAccount) && Objects.nonNull(payAccount.getId())) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public PayAccountVO addPayAccount(PayAccountVO vo) {
		AssertUtil.isNull(vo, "PayAccountServiceImpl|addPayAccount|传入参数vo为空");
		//  userId; shopId 不能同时为空，且不能同时有值
		if (vo.getUserId() != null && vo.getShopId() != null) {
			log.error("userId 和 shopId 不能同时有值");
			throw new QmdException("无法要确定需要创建的是个人账户或者是店铺账户");
		}
		if (vo.getUserId() == null && vo.getShopId() == null) {
			log.error("userId 和 shopId 不能同时为空");
			throw new QmdException("无法要确定需要创建的是个人账户或者是店铺账户");
		}
		// 如果是个人账户 判断是否已经创建过
		if (vo.getUserId() != null) {
			PayAccountVO payAccount = getPayAccountByUserId(vo.getUserId());
			if (payAccount != null) {
				log.info("此用户已经存在账户！"+payAccount);
				return payAccount;
				//throw new QmdException("此用户已经存在账户！");
			}
		}
		if (vo.getShopId() != null) {
			PayAccountVO payAccount = getPayAccountByShopId(vo.getShopId());
			if (payAccount != null) {
				log.info("此用户已经存在账户！"+payAccount);
				return payAccount;
				//throw new QmdException("此店铺已经存在账户！");
			}
		}
		
		PayAccount entity = CopyBeanUtil.copyBean(vo, PayAccount.class);
		if (!checkCUD(payAccountMapper.insert(entity))) {
			log.info("创建商户信息失败|account:" + vo.getAccount());
			throw new QmdException("创建支付账户失败");
		}
		vo.setId(entity.getId());
		return vo;
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void updatePayAccount(PayAccountVO vo) {
		AssertUtil.isNull(vo, "PayAccountServiceImpl|updatePayAccount|传入参数vo为空");
		PayAccount entity = CopyBeanUtil.copyBean(vo, PayAccount.class);
		if (!checkCUD(payAccountMapper.updateById(entity))) {
			log.info("更新商户信息失败|id:" + vo.getId() + "|account:" + vo.getAccount());
			throw new QmdException("更新支付账户失败");
		}
	}

	@Override
	public PayAccountVO getPayAccountByUserId(Long userId) {
		AssertUtil.isNull(userId, "PayAccountServiceImpl|getPayAccountByUserId|传入参数userId为空");
		PayAccount selectOne = payAccountMapper.selectOne(new QueryWrapper<PayAccount>().eq("user_id", userId));
		if (Objects.nonNull(selectOne)) {
			return CopyBeanUtil.copyBean(selectOne, PayAccountVO.class);
		}
		return null;
	}

	@Override
	public PayAccountVO getPayAccountByShopId(Long shopId) {
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|getPayAccountByShopId|传入参数shopId为空");
		PayAccount selectOne = payAccountMapper.selectOne(new QueryWrapper<PayAccount>().eq("shop_id", shopId));
		if (Objects.nonNull(selectOne)) {
			return CopyBeanUtil.copyBean(selectOne, PayAccountVO.class);
		}
		return null;
	}

	@Override
	public PayAccountVO getPayAccountByUserIdAndShopId(Long userId, Long shopId) {
		AssertUtil.isNull(userId, "PayAccountServiceImpl|getPayAccountByUserIdAndShopId|传入参数userId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|getPayAccountByUserIdAndShopId|传入参数shopId为空");
		PayAccount selectOne = payAccountMapper.selectOne(new QueryWrapper<PayAccount>()
				.eq("shop_id", shopId)
				.eq("user_id", userId));
		if (Objects.nonNull(selectOne)) {
			return CopyBeanUtil.copyBean(selectOne, PayAccountVO.class);
		}
		return null;
	}

	@Override
	public List<PayAccountVO> getPayAccountByShopIds(List<Long> shopIds) {
		AssertUtil.isNull(shopIds, "PayAccountServiceImpl|getPayAccountByShopIds|传入参数shopIds为空");
		List<PayAccount> list = payAccountMapper.selectList(new QueryWrapper<PayAccount>().in("shop_id", shopIds).orderByAsc("create_date"));
		if (!CollectionUtils.isEmpty(list)) {
			return CopyBeanUtil.copyList(list, PayAccountVO.class);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void increaseBalanceById(Long id, BigDecimal increaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|increaseBalanceById|传入参数id为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|increaseBalanceById|传入参数increaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal value = account.getBalance().add(increaseValue);
			int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>().set("balance", value).eq("id", id).eq("balance", account.getBalance()));
			if (!checkCUD(ret)) {
				log.error("增加账户累计金额失败|accountId:" + id + "|increaseValue:" + increaseValue);
				throw new QmdException("增加账户累计金额失败");
			}
			log.info(new StringBuilder("增加账户|id:")
					.append(id)
					.append("|增加前的balance:").append(account.getBalance())
					.append("|增加后的balance:").append(value).toString());
		}
		/*log.info(new StringBuilder("PayAccountServiceImpl")
				.append("|increaseBalanceById")
				.append("|accountId:").append(id)
				.append("|increaseValue:").append(increaseValue).toString());*/
	}

	// TODO 给服务商增加可提现余额 走队列
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void increaseSettBalanceById(Long id, BigDecimal increaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|increaseSettBalanceById|传入参数id为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|increaseSettBalanceById|传入参数increaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal value = account.getSettBalance().add(increaseValue);
			int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>().set("sett_balance", value).eq("id", id).eq("sett_balance", account.getSettBalance()));
			if (!checkCUD(ret)) {
				log.error("增加账户可提现金额失败|accountId:" + id + "|increaseValue:" + increaseValue);
				throw new QmdException("增加账户可提现金额失败");
			}
			log.info(new StringBuilder("增加账户|id:")
					.append(id)
					.append("|增加前的settBalance:").append(account.getBalance())
					.append("|增加后的settBalance:").append(value).toString());
		}
		/*log.info(new StringBuilder("PayAccountServiceImpl")
				.append("|increaseSettBalanceById")
				.append("|accountId:").append(id)
				.append("|increaseValue:").append(increaseValue).toString());*/
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void increaseUnbalanceById(Long id, BigDecimal increaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|increaseUnbalanceById|传入参数id为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|increaseUnbalanceById|传入参数increaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal value = account.getUnbalance().add(increaseValue);
			int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>().set("unbalance", value).eq("id", id).eq("unbalance", account.getUnbalance()));
			if (!checkCUD(ret)) {
				log.error("增加账户冻结金额失败|accountId:" + id + "|increaseValue:" + increaseValue);
				throw new QmdException("增加账户冻结金额失败");
			}
			log.info(new StringBuilder("增加账户|id:")
					.append(id)
					.append("|增加前的unbalance:").append(account.getBalance())
					.append("|增加后的unbalance:").append(value).toString());
		}
		/*log.info(new StringBuilder("PayAccountServiceImpl")
				.append("|increaseUnbalanceById")
				.append("|accountId:").append(id)
				.append("|increaseValue:").append(increaseValue).toString());*/
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void increaseAccountBalanceById(Long id, BigDecimal increaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|increaseAccountBalanceById|传入参数id为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|increaseAccountBalanceById|传入参数increaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal value = account.getAccountBalance().add(increaseValue);
			int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>().set("account_balance", value).eq("id", id).eq("account_balance", account.getAccountBalance()));
			if (!checkCUD(ret)) {
				log.error("增加账户余额失败|accountId:" + id + "|increaseValue:" + increaseValue);
				throw new QmdException("增加账户余额失败");
			}
			log.info(new StringBuilder("增加账户|id:")
					.append(id)
					.append("|增加前的accountBalance:").append(account.getBalance())
					.append("|增加后的accountBalance:").append(value).toString());
		}
		/*log.info(new StringBuilder("PayAccountServiceImpl")
				.append("|increaseAccountBalanceById")
				.append("|accountId:").append(id)
				.append("|increaseValue:").append(increaseValue).toString());*/
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void increaseBalanceAndAccountBalance(Long id, BigDecimal increaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|increaseAccountBalanceById|传入参数id为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|increaseAccountBalanceById|传入参数increaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal balanceValue = account.getBalance().add(increaseValue);
			BigDecimal accountBalanceValue = account.getAccountBalance().add(increaseValue);
			int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>()
					.set("balance", balanceValue)
					.set("account_balance", accountBalanceValue)
					.eq("id", id).eq("balance", account.getBalance()).eq("account_balance", account.getAccountBalance()));
			if (!checkCUD(ret)) {
				log.error("增加账户累计总金额和账户余额失败|accountId:" + id + "|increaseValue:" + increaseValue);
				throw new QmdException("增加账户累计总金额和账户余额失败");
			}
			log.info(new StringBuilder("增加账户|id:")
					.append(id)
					.append("|增加前的balance:").append(account.getBalance())
					.append("|增加后的balance:").append(balanceValue)
					.append("|增加前的accountBalance:").append(account.getAccountBalance())
					.append("|增加后的accountBalance:").append(accountBalanceValue).toString());
		}
		/*log.info(new StringBuilder("PayAccountServiceImpl")
				.append("|increaseBalanceAndAccountBalance")
				.append("|accountId:").append(id)
				.append("|increaseValue:").append(increaseValue).toString());*/
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void decreaseBalanceById(Long id, BigDecimal decreaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|decreaseBalance|传入参数id为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|decreaseBalance|传入参数decreaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal balanceValue = account.getBalance().subtract(decreaseValue);
			int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>()
					.set("balance", balanceValue)
					.eq("id", id).eq("balance", account.getBalance()));
			if (!checkCUD(ret)) {
				log.error("减少账户累计总金额失败|accountId:" + id + "|decreaseValue:" + decreaseValue);
				throw new QmdException("减少账户累计总金额失败");
			}
			log.info(new StringBuilder("增加账户|id:")
					.append(id)
					.append("|增加前的balance:").append(account.getBalance())
					.append("|增加后的balance:").append(balanceValue).toString());
		}
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void decreaseAccountBalanceAndSettBalanceById(Long id, BigDecimal desreaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|decreaseAccountBalanceAndSettBalanceById|传入参数id为空");
		AssertUtil.isNull(id, "PayAccountServiceImpl|decreaseAccountBalanceAndSettBalanceById|传入参数desreaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal accountBalanceValue = account.getAccountBalance().subtract(desreaseValue);
			BigDecimal settBalanceValue = account.getSettBalance().subtract(desreaseValue);
				int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>()
						.set("account_balance", accountBalanceValue)
						.set("sett_balance", settBalanceValue)
						.eq("id", id)
						.eq("account_balance", account.getAccountBalance())
						.eq("sett_balance", account.getSettBalance()));
				if (!checkCUD(ret)) {
					log.error("减少账户累计总金额,账户余额,可提现余额失败|accountId:" + id + "|desreaseValue:" + desreaseValue);
					throw new QmdException("增加账户累计总金额和账户余额失败");
				}
				log.info(new StringBuilder("增加账户|id:")
						.append(id)
						.append("|增加前的accountBalance:").append(account.getAccountBalance())
						.append("|增加后的accountBalance:").append(accountBalanceValue)
						.append("|增加前的settBalance:").append(account.getSettBalance())
						.append("|增加后的settBalance:").append(settBalanceValue).toString());
		}
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})	
	public void decreaseBalanceAndAccountBalanceAndSettBalanceById(Long id, BigDecimal desreaseValue) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|decreaseBalanceAndAccountBalanceById|传入参数id为空");
		AssertUtil.isNull(id, "PayAccountServiceImpl|decreaseBalanceAndAccountBalanceById|传入参数desreaseValue为空");
		PayAccount account = payAccountMapper.selectById(id);
		if (Objects.nonNull(account)) {
			BigDecimal balanceValue = account.getBalance().subtract(desreaseValue);
			BigDecimal accountBalanceValue = account.getAccountBalance().subtract(desreaseValue);
			BigDecimal settBalanceValue = account.getSettBalance().subtract(desreaseValue);
			//if (BigDecimal.ZERO.compareTo(settBalanceValue) == 0 || BigDecimal.ZERO.compareTo(settBalanceValue) == 1) {
				int ret = payAccountMapper.update(account, new UpdateWrapper<PayAccount>()
						.set("balance", balanceValue)
						.set("account_balance", accountBalanceValue)
						.set("sett_balance", settBalanceValue)
						.eq("id", id)
						.eq("balance", account.getBalance())
						.eq("account_balance", account.getAccountBalance())
						.eq("sett_balance", account.getSettBalance()));
				if (!checkCUD(ret)) {
					log.error("减少账户累计总金额,账户余额,可提现余额失败|accountId:" + id + "|desreaseValue:" + desreaseValue);
					throw new QmdException("增加账户累计总金额和账户余额失败");
				}
				log.info(new StringBuilder("增加账户|id:")
						.append(id)
						.append("|增加前的balance:").append(account.getBalance())
						.append("|增加后的balance:").append(balanceValue)
						.append("|增加前的accountBalance:").append(account.getAccountBalance())
						.append("|增加后的accountBalance:").append(accountBalanceValue)
						.append("|增加前的settBalance:").append(account.getSettBalance())
						.append("|增加后的settBalance:").append(settBalanceValue).toString());
			/*} else {
				log.info("账户余额不足,不能执行下账操作 accountId:" + id + "减少金额:" + desreaseValue);
				throw new QmdException("商户余额不足");
			}*/
		}
	}
	
	@Override
	public PayAccountVO getPayAccountById(Long id) {
		AssertUtil.isNull(id, "PayAccountServiceImpl|getPayAccountById|传入参数id为空");
		return payAccountMapper.getPayAccountInfoById(id);
	}

	/*@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void processAccountIncreaseOrDecreaseException(TradeDataVO data) {
		switch (data.getProcessType()) {
		case PAYED:
			if (data.getIsVT()) {
				// 处理普通订单分润
				doProcessValidationTicketAccountIncreaseException(data);
			} else {
				// 非核销订单分润
				doProcessAccountIncreaseException(data);
			}
			break;
		case REFUND:
			doProcessAccountRefundException(data);
			break;
		default:
			break;
		}
		
	}
	
	*//**
	 * 处理普通订单核销 因并发失败的增加账户金额操作 
	 * @param data
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessValidationTicketAccountIncreaseException(TradeDataVO data) {
		// 订单状态必须为【已支付】才能进行核销
		ValidationTicketVO ticket = validationTicketService.getUnuseValidationTicketByTicketCode(data.getTicketCode());
		if (ObjectUtils.isEmpty(ticket)) {
			log.error("SaobeiCallbackServiceImpl|doProcessValidationTicketAccountIncreaseException|该核销券不存在或者已经过期 ticketCode" + data.getTicketCode());
			//throw new QmdException("该核销券不存在或者已经过期");
			return;
		}
		
		// 查询该登陆用户的id所管理的店铺id
		List<Long> shopidList = shopUserService.getShopIdListByUserId(data.getUserId());
		// 不包含次管理店铺 直接抛出异常
		if (!shopidList.contains(ticket.getShopId())) {
			log.error("SaobeiCallbackServiceImpl|doProcessValidationTicketAccountIncreaseException|该用户不是此核销券所在商家的管理人员，无法核销！ userId:" + data.getUserId() + "|shopId:" + ticket.getShopId());
			// throw new QmdException("该用户不是此核销券所在商家的管理人员，无法核销！");
			return;
		}
		
		OrderVO orderVO = orderService.getOrderById(ticket.getOrderId());
		if (orderVO == null) {
			log.error("SaobeiCallbackServiceImpl|doProcessValidationTicketAccountIncreaseException|此核销券对应的订单不存在 orderId:" + ticket.getOrderId());
			//throw new QmdException("此核销券对应的订单不存在");
			return;
		}
		
		if (orderVO.getOrderStatus() != OrderStatusEnum.Paid.getCode()) {
			log.error("SaobeiCallbackServiceImpl|doProcessValidationTicketAccountIncreaseException|此核销券对应的订单状态不能核销 orderStatus:" + orderVO.getOrderStatus());
			//throw new QmdException("此核销券对应的订单状态不能核销");
			return;
		}
		
		//为中奖，且 订单实付 > 0 才能调用账户余额增加
		if (GameWinEnum.NotWin.getCode().equals(orderVO.getWin()) && orderVO.getNeedPayAmount().doubleValue() > 0) {
			//calcValidationTicketProfitIncreaseAccountBalance(orderVO.getShopId(), orderVO.getOrderId(), data.getOperateDate());
			calcValidationTicketProfitIncreaseAccountBalance(data);
		}

		ticket.setStatus(ValidationTicketStatus.Used.getCode());
		orderVO.setOrderStatus(OrderStatusEnum.TradeComplete.getCode());
		validationTicketService.updateValidationTicketAndOrderInfo(ticket, orderVO);
	}
	
	*//**
	 * 处理扫呗回调 因并发失败的增加账户金额操作 
	 * @param data
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessAccountIncreaseException(TradeDataVO data) {
		AssertUtil.isNull(data, "PayAccountServiceImpl|processAccountIncreaseBalance|传入参数data为空");
		TradeDetailVO trade = tradeDetailService.getTradeDetailById(data.getTradeId());
		if (!TradePayStatusEnum.CREATE.getCode().equals(trade.getStatus())) {
			log.error("SaobeiCallbackServiceImpl|processAccountIncreaseBalance|扫呗支付回调 订单流水已被处理 tradeStatus" + trade.getStatus());
			return;
		}
		
		OrderVO order = orderService.getOrderById(data.getOrderId());
		if (!OrderStatusEnum.Paying.getCode().equals(order.getOrderStatus())) {
			log.error("SaobeiCallbackServiceImpl|processAccountIncreaseBalance|扫呗支付回调 订单状态已不处于支付中 orderId:" + data.getOrderId());
			return;
		}
		
		GameExtendVO gameOrder = gameOrderService.getGameOrderByGameId(data.getGameOrderId());
		if (StringUtils.isNotBlank(data.getGameOrderId())) {
			gameOrder = gameOrderService.getGameOrderByGameId(data.getGameOrderId());
			if (!OrderStatusEnum.Paying.getCode().equals(gameOrder.getOrderStatus())) {
				log.error("SaobeiCallbackServiceImpl|processAccountIncreaseBalance|扫呗游戏支付回调 订单已被处理过 gameOrderStatus:" + gameOrder.getOrderStatus());
				return;
			}
		}
		
		// 处理支付流水信息
		trade.setChannelTradeNo(data.getChannelTradeNo());
		trade.setCallbackDate(data.getOperateDate());
		trade.setPayDate(data.getPayEndDate());
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		tradeDetailService.updateTradeDetail(trade);
		
		// 计算分润 入账
		if (!data.isGame()) {
			//calcPayedCallbackProfitOrderAndIncreaseBalance(trade.getAccountInId(), order.getShopId(), order.getOrderId(), data.getGameOrderId(), trade.getOutTradeNo(), data.getIncreaseValue(), data.getOperateDate());
			calcPayedCallbackProfitOrderAndIncreaseBalance(data);
		} else {
			//calcPayedCallbackProfitGameOrderAndIncreaseBalance(trade.getAccountInId(), order.getShopId(), order.getOrderId(), data.getGameOrderId(), trade.getOutTradeNo(), data.getIncreaseValue(), data.getOperateDate());
			calcPayedCallbackProfitGameOrderAndIncreaseBalance(data);
		}
		log.info("SaobeiCallbackServiceImpl|doProcessAccountIncreaseException|分润入账完毕tradeId:" + trade.getId());
		
		// 处理普通订单
		if (!data.isGame()) {
			order.setPayChannel(trade.getPayChannel());
			order.setPayDate(data.getOperateDate());
			order.setOrderStatus(OrderStatusEnum.Paid.getCode());
			order.setAmountTotal(order.getAmountTotal().add(data.getIncreaseValue()));
			//orderService.updateOrder(order);
			validationTicketService.createValidationTicketAndUpdateOrderStatus(order);
		// 处理游戏订单
		} else {
			// 处理订单
			gameOrder.setPayDate(data.getOperateDate());
			gameOrder.setPayChannel(data.getPayChannal());
			gameOrder.setOutTradeNo(data.getOutTradeNo());
			gameOrder.setAmountTotal(data.getIncreaseValue());
			gameOrder.setOrderStatus(OrderStatusEnum.Paid.getCode());
			
			// 更新 累加付款资金
			order.setPayChannel(PayTypeEnum.QMDPay.getCode());
			order.setGameAmount(order.getGameAmount().add(data.getIncreaseValue()));
			orderService.updateOrderInfoAndGameOrderInfo(order, gameOrder);
		}
	}
	
	*//**
	 * 处理扫呗回调 因并发失败的减少账户金额操作 
	 * @param data
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessAccountRefundException(TradeDataVO data) {
		TradeDetailVO trade = tradeDetailService.getTradeDetailById(data.getTradeId());
		// 验证流水状态
		if (!TradePayStatusEnum.PAYED.getCode().equals(trade.getStatus())) {
			log.error("退款申请, 订单未支付或处于其他状态 不能申请退款| tradeId:" + data.getTradeId() + "|status:" + trade.getStatus());
			return;
		}
		
		// 验证退款申请金额<=支付金额
		if (data.getDecreaseValue().compareTo(trade.getAmount()) == 1) {
			log.error("退款申请, 申请退款金额大于支付金额 tradeId:" + data.getTradeId() + "|refundMoney:" + data.getDecreaseValue() + "|amount:" + trade.getAmount());
			return;
		}
		
		OrderVO order = orderService.getOrderById(trade.getSerialNo());
		// 验证订单状态
		if (!OrderStatusEnum.RefundApply.getCode().equals(order.getOrderStatus())) {
			log.error("退款申请, 订单状态异常 不处于退款申请中 |OrderStatus:" + order.getOrderStatus());
			return;
		}
		
		// 验证账户
		PayAccountVO shopAccount = getPayAccountById(data.getShopAccountId());
		if (Objects.isNull(shopAccount)) {
			log.error("退款申请失败, 商家账户信息不存在shopId:" + data.getShopAccountId());
			return;
		}
		
		// 验证商户余额
		if (shopAccount.getAccountBalance().compareTo(data.getDecreaseValue()) == -1) {
			log.error("退款申请, 商家余额不足 settBalance:" + shopAccount.getSettBalance());
			return;
		}
		
		// 计算分润 出账
		if (data.isVT()) {
			//calcOrderValidationTicketRefundProfitAndDecreaseBalance(trade.getAccountInId(), data.getShopId(), order.getOrderId(), null, data.getOutTradeNo(), data.getOutRefundNo(), data.getDecreaseValue(), trade.getPayChannel(), data.getOperateDate());
			calcOrderValidationTicketRefundProfitAndDecreaseBalance(data);
		} else {
			//calcOrderPayedRefundProfitAndDecreaseBalance(trade.getAccountInId(), data.getShopId(), order.getOrderId(), null, data.getOutTradeNo(), data.getOutRefundNo(), data.getDecreaseValue(), trade.getPayChannel(), data.getOperateDate());
			calcOrderPayedRefundProfitAndDecreaseBalance(data);
		}
		log.info("SaobeiCallbackServiceImpl|doProcessAccountDecreaseException|分润出账完毕tradeId:" + trade.getId());
		
		// 处理订单 和 退款申请
		RefundResultVO refund = new RefundResultVO();
		refund.setApplyId(data.getRefundId());
		refund.setFlag(true);
		refund.setOrderId(order.getOrderId());
		refund.setOutRefundNo(data.getOutRefundNo());
		refund.setOutTrandNo(data.getOutTradeNo());
		refund.setRefundDate(data.getRefundEndDate());
		refund.setRefundFee(data.getDecreaseValue());
		orderReturnService.updateOrderAndRefundInfo(refund);
	}
	*/
	
	/*
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcPayedCallbackProfitAndIncreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, BigDecimal increaseValue, Boolean isGame, Date operateDate) {
		AssertUtil.isNull(shopAccountId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数increaseValue为空");
		AssertUtil.isNull(isGame, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数isGame为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数operateDate为空");
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopIncreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		
		// 平台获利
		platformIncreaseValue = shopIncreaseValue.add(increaseValue).multiply(profit);
		// 店铺可入账
		shopIncreaseValue = increaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		// 店铺入账
		if (!isGame) {
			increaseBalanceById(shopAccountId, shopIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
			// 记录平台收取店铺手续费转出流水 (因为要核销才能加钱  所以这个流水变到核销的时候才打)
			// tradeDetailService.addTransferOut(shopAccountId, ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, platformIncreaseValue, operateDate, TradeDetailTypeEnum.PALFORMORDER);
		} else {
			increaseBalanceAndAccountBalance(shopAccountId, shopIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
			// 记录平台收取店铺手续费转出流水
			tradeDetailService.addTransferOut(shopAccountId, ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, platformIncreaseValue, operateDate, TradeDetailTypeEnum.PALFORMGAMEORDER);
		}
		// ----------------------------------------- 处理业务员 到 省级 分润入账----------------------------------------------
		doProcessSalemanIncreaseAccount(shopId, orderId, gameOrderId, outTradeNo, isGame, operateDate, rateBase, platformIncreaseValue);
	}

	*//**
	 * 处理业务员 到 省级 分润计算
	 * @param shopId
	 * @param orderId
	 * @param gameOrderId
	 * @param outTradeNo
	 * @param isGame
	 * @param operateDate
	 * @param rateBase
	 * @param platformIncreaseValue
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessSalemanIncreaseAccount(Long shopId, String orderId, String gameOrderId, String outTradeNo, Boolean isGame, Date operateDate, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					// 业务员入账
					if (!isGame) {
						increaseBalanceById(salemanAccount.getId(), salemanIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
						// 记录营业员入账流水记录 (因为要核销才能加钱  所以这个流水变到核销的时候才打)
						// tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.SALEMANORDER);
					} else {
						increaseBalanceAndAccountBalance(salemanAccount.getId(), salemanIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
						// 记录营业员入账流水记录
						tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.SALEMANGAMEORDER);
					}
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						if (!isGame) {
							increaseBalanceById(countryAccount.getId(), countryProfit);
							// 记录市级 省级 账流水记录 (因为要核销才能加钱  所以这个流水变到核销的时候才打)
							// tradeDetailService.addTransferIn(cityAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, operateDate, TradeDetailTypeEnum.CITYORDER);
						} else {
							increaseBalanceAndAccountBalance(countryAccount.getId(), countryProfit);
							// 记录市级 省级 账流水记录
							tradeDetailService.addTransferIn(countryAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, countryIncreaseValue, operateDate, TradeDetailTypeEnum.COUNTRYGAMEORDER);
						}
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						if (!isGame) {
							increaseBalanceById(cityAccount.getId(), cityIncreaseValue);
							// 记录市级 省级 账流水记录 (因为要核销才能加钱  所以这个流水变到核销的时候才打)
							// tradeDetailService.addTransferIn(cityAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, operateDate, TradeDetailTypeEnum.CITYORDER);
						} else {
							increaseBalanceAndAccountBalance(cityAccount.getId(), cityIncreaseValue);
							// 记录市级 省级 账流水记录
							tradeDetailService.addTransferIn(cityAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, operateDate, TradeDetailTypeEnum.CITYGAMEORDER);
						}
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						if (!isGame) {
							increaseBalanceById(provinceAccount.getId(), provinceIncreaseValue);
							// 记录市级 省级 账流水记录 (因为要核销才能加钱  所以这个流水变到核销的时候才打)
							// tradeDetailService.addTransferIn(cityAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, operateDate, TradeDetailTypeEnum.CITYORDER);
						} else {
							increaseBalanceAndAccountBalance(provinceAccount.getId(), provinceIncreaseValue);
							// 记录市级 省级 账流水记录
							tradeDetailService.addTransferIn(provinceAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, provinceIncreaseValue, operateDate, TradeDetailTypeEnum.CITYGAMEORDER);
						}
					}
				}
			}
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------
	// -----------------------------------------递归处理  市级 省级 平台 账户余额 -----------------------------------
	// ---------------------------------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	private void loopIncreaseAccount(Long parentId, final String orderid, final String gameOrderId, String outTradeNo, final Date operateDate, final BigDecimal rateBase, final BigDecimal platformIncreaseValue, final Boolean isGame) {
		if (Objects.nonNull(parentId) && !ParentDataEnum.RootId.getCode().equals(parentId)) {
			SalemanVO saleman = salemanService.getSalemanById(parentId);
			if (Objects.nonNull(saleman)) {
				// 账户
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit).setScale(0, BigDecimal.ROUND_DOWN);
				// 入账
				if (!isGame) {
					increaseBalanceById(salemanAccount.getId(), salemanIncreaseValue);
					// 记录市级 省级 账流水记录
					if (!ParentDataEnum.RootId.getCode().equals(parentId)) {
						tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderid, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.CITYORDER);
					} else {
						tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderid, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.PROVINCEORDER);
					}
				} else {
					increaseBalanceAndAccountBalance(salemanAccount.getId(), salemanIncreaseValue);
					// 记录市级 省级 账流水记录
					if (!ParentDataEnum.RootId.getCode().equals(parentId)) {
						tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderid, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.CITYGAMEORDER);
					} else {
						tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderid, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.PROVINCEGAMEORDER);
					}
				}
				// 递归
				loopIncreaseAccount(saleman.getParentId(), orderid, gameOrderId, outTradeNo, operateDate, rateBase, platformIncreaseValue, isGame);
			}
		}
	}


	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcPayedCallbackProfitGameOrderAndIncreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, BigDecimal increaseValue, Date operateDate) {
		AssertUtil.isNull(shopAccountId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数increaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数operateDate为空");
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopIncreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		
		// 平台获利
		platformIncreaseValue = shopIncreaseValue.add(increaseValue).multiply(profit);
		// 店铺可入账
		shopIncreaseValue = increaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		// 店铺入账
		increaseBalanceAndAccountBalance(shopAccountId, shopIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
		// 记录平台收取店铺手续费转出流水
		tradeDetailService.addTransferOut(shopAccountId, ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, platformIncreaseValue, operateDate, TradeDetailTypeEnum.PALFORMGAMEORDER);
		
		// ----------------------------------------- 处理业务员 到 省级 分润入账----------------------------------------------
		doCalcPayedCallbackProfitGameOrderAndIncreaseBalance(shopId, orderId, gameOrderId, outTradeNo, operateDate, rateBase, platformIncreaseValue);
	}
	
	*//**
	 * 处理业务员 到 省级 分润计算
	 * @param shopId
	 * @param orderId
	 * @param gameOrderId
	 * @param outTradeNo
	 * @param isGame
	 * @param operateDate
	 * @param rateBase
	 * @param platformIncreaseValue
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doCalcPayedCallbackProfitGameOrderAndIncreaseBalance(Long shopId, String orderId, String gameOrderId, String outTradeNo, Date operateDate, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					increaseBalanceAndAccountBalance(salemanAccount.getId(), salemanIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
					// 记录营业员入账流水记录
					tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, operateDate, TradeDetailTypeEnum.SALEMANGAMEORDER);
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceAndAccountBalance(countryAccount.getId(), countryIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferIn(countryAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, countryIncreaseValue, operateDate, TradeDetailTypeEnum.COUNTRYGAMEORDER);
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceAndAccountBalance(cityAccount.getId(), cityIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferIn(cityAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, operateDate, TradeDetailTypeEnum.CITYGAMEORDER);
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						increaseBalanceAndAccountBalance(provinceAccount.getId(), provinceIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferIn(provinceAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, gameOrderId, outTradeNo, provinceIncreaseValue, operateDate, TradeDetailTypeEnum.PROVINCEGAMEORDER);
					}
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcPayedCallbackProfitOrderAndIncreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, BigDecimal increaseValue, Date operateDate) {
		AssertUtil.isNull(shopAccountId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(increaseValue, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数increaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndIncreaseBalance|传入参数operateDate为空");
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopIncreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		// 平台获利
		platformIncreaseValue = shopIncreaseValue.add(increaseValue).multiply(profit);
		// 店铺可入账
		shopIncreaseValue = increaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		// 店铺入账
		increaseBalanceById(shopAccountId, shopIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
		
		// ----------------------------------------- 处理业务员 到 省级 分润入账----------------------------------------------
		doCalcPayedCallbackProfitOrderAndIncreaseBalance(shopId, orderId, gameOrderId, outTradeNo, operateDate, rateBase, platformIncreaseValue);
	}
	
	
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doCalcPayedCallbackProfitOrderAndIncreaseBalance(Long shopId, String orderId, String gameOrderId, String outTradeNo, Date operateDate, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					// 业务员入账
					increaseBalanceById(salemanAccount.getId(), salemanIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceById(countryAccount.getId(), countryIncreaseValue);
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceById(cityAccount.getId(), cityIncreaseValue);
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceById(provinceAccount.getId(), provinceIncreaseValue);
					}
				}
			}
		}
	}
	
	
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcRefundProfitAndDecreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, String outRefundNo, BigDecimal decreaseValue, String payChannel, Boolean isVT, Date operateDate) {
		AssertUtil.isNull(shopAccountId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数decreaseValue为空");
		AssertUtil.isNull(isVT, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数isVT为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数operateDate为空");
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopDecreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		
		// 平台获利
		platformIncreaseValue = shopDecreaseValue.add(decreaseValue).multiply(profit);
		// 店铺出账金额
		shopDecreaseValue = decreaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		if (isVT) {
			// 店铺出账(退款减款)
			decreaseBalanceAndAccountBalanceAndSettBalanceById(shopAccountId, shopDecreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
			// 记录店铺出账流水 (退款流水)
			tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), shopAccountId, orderId, gameOrderId, outTradeNo, decreaseValue, payChannel, operateDate, TradeDetailTypeEnum.SHOPORDERREFUND);
			// 记录店铺被平台手续费入账流水
			tradeDetailService.addTransferIn(shopAccountId, ParentDataEnum.RootId.getCode(), orderId, gameOrderId, outTradeNo, platformIncreaseValue, operateDate, TradeDetailTypeEnum.PALFORMORDERREFUND);
		
			// -----------------------------------------处理业务 到 省级 退款分润出账------------------------------------------------
			doProcessSalemanDecreaseAccount(shopId, orderId, gameOrderId, outTradeNo, payChannel, operateDate, rateBase, platformIncreaseValue);
		} else {
			// 店铺出账
			decreaseBalanceById(shopAccountId, shopDecreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
		}
	}

	*//**
	 * 处理业务 到 省级 退款分润出账
	 * @param shopId
	 * @param orderId
	 * @param gameOrderId
	 * @param outTradeNo
	 * @param payChannel
	 * @param operateDate
	 * @param rateBase
	 * @param platformIncreaseValue
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessSalemanDecreaseAccount(Long shopId, String orderId, String gameOrderId, String outTradeNo, String payChannel, Date operateDate, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					// 业务员出账
					decreaseBalanceAndAccountBalanceAndSettBalanceById(salemanAccount.getId(), salemanIncreaseValue);
					// 记录营业员入账流水记录
					tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), salemanAccount.getId(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.SALEMANREFUND);
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 出账
						decreaseBalanceAndAccountBalanceAndSettBalanceById(countryAccount.getId(), countryIncreaseValue);
						// 记录区级 省级 账流水记录
						tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), countryAccount.getId(), orderId, gameOrderId, outTradeNo, countryIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.COUNTRYREFUND);
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 出账
						decreaseBalanceAndAccountBalanceAndSettBalanceById(cityAccount.getId(), cityIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), cityAccount.getId(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.CITYREFUND);
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 出账
						decreaseBalanceAndAccountBalanceAndSettBalanceById(provinceAccount.getId(), provinceIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), provinceAccount.getId(), orderId, gameOrderId, outTradeNo, provinceIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.PROVINCEREFUND);
					}
				}
			}
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------
	// -----------------------------------------递归处理  市级 省级 平台 账户余额 -----------------------------------
	// ---------------------------------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	private void loopDecreaseAccount(Long parentId, final String orderId, final String gameOrderId, String outTradeNo, final Date operateDate, final BigDecimal rateBase, final BigDecimal platformIncreaseValue, final String payChannel) {
		if (Objects.nonNull(parentId) && !ParentDataEnum.RootId.getCode().equals(parentId)) {
			SalemanVO saleman = salemanService.getSalemanById(parentId);
			if (Objects.nonNull(saleman)) {
				// 账户
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit).setScale(0, BigDecimal.ROUND_DOWN);
				// 出账
				decreaseBalanceAndAccountBalanceAndSettBalanceById(salemanAccount.getId(), salemanIncreaseValue);
				// 记录市级 省级 账流水记录
				if (!ParentDataEnum.RootId.getCode().equals(parentId)) {
					tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), salemanAccount.getId(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.CITYREFUND);
				} else {
					tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), salemanAccount.getId(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.PROVINCEORDER);
				}
				// 递归
				loopDecreaseAccount(saleman.getParentId(), orderId, gameOrderId, outTradeNo, operateDate, rateBase, platformIncreaseValue, payChannel);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcValidationTicketProfitIncreaseAccountBalance(Long shopId, String orderId, Date operateDate) {
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数operateDate为空");
		
		PayAccountVO shopAccount = getPayAccountByShopId(shopId);
		if (Objects.isNull(shopAccount)) {
			log.error("商户账号不存在 |shopId:" + shopId);
			throw new QmdException("商户账号不存在");
		}
		
		TradeDetailVO trade = tradeDetailService.getTradeDetailByOrderIdAndType(orderId, TradeDetailTypeEnum.SHOPORDER);
		if (Objects.isNull(trade)) {
			log.error("已支付流水信息不存在 |orderId:" + orderId);
			throw new QmdException("支付流水信息不存在");
		}
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopIncreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		// 实际支付金额
		BigDecimal increaseValue =  trade.getAmount();
		// 平台获利
		platformIncreaseValue = shopIncreaseValue.add(increaseValue).multiply(profit);
		// 店铺可入账
		shopIncreaseValue = increaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		// 店铺入账
		increaseAccountBalanceById(shopAccount.getId(), shopIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
		// 记录平台收取店铺手续费转出流水
		tradeDetailService.addTransferOut(shopAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, null, trade.getOutTradeNo(), platformIncreaseValue, operateDate, TradeDetailTypeEnum.PALFORMORDER);
		
		// ----------------------------------------- 处理业务员 到 省级 分润入账----------------------------------------------
		doProcessValidationTicketProfitIncreaseAccountBalance(shopId, orderId, operateDate, trade, rateBase, platformIncreaseValue);
		
		// 修改订单流水为 店铺已核销订单流水
		trade.setType(TradeDetailTypeEnum.SHOPORDERVT.getCode());
		tradeDetailService.updateTradeDetail(trade);
	}

	
	*//**
	 * 处理核销普通订单 账户余额分润
	 * @param shopId
	 * @param orderId
	 * @param operateDate
	 * @param trade
	 * @param rateBase
	 * @param platformIncreaseValue
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessValidationTicketProfitIncreaseAccountBalance(Long shopId, String orderId, Date operateDate, TradeDetailVO trade, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					// 业务员入账
					increaseAccountBalanceById(salemanAccount.getId(), salemanIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
					// 记录营业员入账流水记录
					tradeDetailService.addTransferIn(salemanAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, null, trade.getOutTradeNo(), salemanIncreaseValue, operateDate, TradeDetailTypeEnum.SALEMANORDER);
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceById(countryAccount.getId(), countryIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferIn(countryAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, null, trade.getOutTradeNo(), countryIncreaseValue, operateDate, TradeDetailTypeEnum.COUNTRYORDER);
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceById(cityAccount.getId(), cityIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferIn(cityAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, null, trade.getOutTradeNo(), cityIncreaseValue, operateDate, TradeDetailTypeEnum.CITYORDER);
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						increaseBalanceById(provinceAccount.getId(), provinceIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferIn(provinceAccount.getId(), ParentDataEnum.PlatformAccountId.getCode(), orderId, null, trade.getOutTradeNo(), provinceIncreaseValue, operateDate, TradeDetailTypeEnum.PROVINCEORDER);
					}
				}
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcOrderValidationTicketRefundProfitAndDecreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, String outRefundNo, BigDecimal decreaseValue, String payChannel, Date operateDate) {
		AssertUtil.isNull(shopAccountId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数decreaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数operateDate为空");
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopDecreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		
		// 平台获利
		platformIncreaseValue = shopDecreaseValue.add(decreaseValue).multiply(profit);
		// 店铺出账金额
		shopDecreaseValue = decreaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		// 店铺出账(退款减款)
		decreaseBalanceAndAccountBalanceAndSettBalanceById(shopAccountId, shopDecreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
		// 记录店铺出账流水 (退款流水)
		tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), shopAccountId, orderId, gameOrderId, outTradeNo, decreaseValue, payChannel, operateDate, TradeDetailTypeEnum.SHOPORDERREFUND);
		// 记录店铺被平台手续费入账流水
		tradeDetailService.addTransferIn(shopAccountId, ParentDataEnum.RootId.getCode(), orderId, gameOrderId, outTradeNo, platformIncreaseValue, operateDate, TradeDetailTypeEnum.PALFORMORDERREFUND);
		// -----------------------------------------处理业务 到 省级 退款分润出账------------------------------------------------
		doCalcOrderValidationTicketRefundProfitAndDecreaseBalance(shopId, orderId, gameOrderId, outTradeNo, payChannel, operateDate, rateBase, platformIncreaseValue);
	}
	
	*//**
	 * 处理普通订单退款后 各个业务员到省级分润信息
	 * @param shopId
	 * @param orderId
	 * @param gameOrderId
	 * @param outTradeNo
	 * @param payChannel
	 * @param operateDate
	 * @param rateBase
	 * @param platformIncreaseValue
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doCalcOrderValidationTicketRefundProfitAndDecreaseBalance(Long shopId, String orderId, String gameOrderId, String outTradeNo, String payChannel, Date operateDate, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					// 业务员出账
					decreaseBalanceAndAccountBalanceAndSettBalanceById(salemanAccount.getId(), salemanIncreaseValue);
					// 记录营业员入账流水记录
					tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), salemanAccount.getId(), orderId, gameOrderId, outTradeNo, salemanIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.SALEMANREFUND);
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 区级账户出账
						decreaseBalanceAndAccountBalanceAndSettBalanceById(countryAccount.getId(), countryIncreaseValue);
						// 记录营业员入账流水记录
						tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), countryAccount.getId(), orderId, gameOrderId, outTradeNo, countryIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.COUNTRYREFUND);
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 出账
						decreaseBalanceAndAccountBalanceAndSettBalanceById(cityAccount.getId(), cityIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), cityAccount.getId(), orderId, gameOrderId, outTradeNo, cityIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.CITYREFUND);
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 出账
						decreaseBalanceAndAccountBalanceAndSettBalanceById(provinceAccount.getId(), provinceIncreaseValue);
						// 记录市级 省级 账流水记录
						tradeDetailService.addTransferOutRefund(ParentDataEnum.RootId.getCode(), provinceAccount.getId(), orderId, gameOrderId, outTradeNo, provinceIncreaseValue, payChannel, operateDate, TradeDetailTypeEnum.PROVINCEREFUND);
					}
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcOrderPayedRefundProfitAndDecreaseBalance(Long shopAccountId, Long shopId, String orderId, String gameOrderId, String outTradeNo, String outRefundNo, BigDecimal decreaseValue, String payChannel, Date operateDate) {
		AssertUtil.isNull(shopAccountId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(shopId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(orderId, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(decreaseValue, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数decreaseValue为空");
		AssertUtil.isNull(operateDate, "PayAccountServiceImpl|calcProfitAndDecreaseBalance|传入参数operateDate为空");
		
		// 费率基础
		final BigDecimal rateBase = new BigDecimal(1000);
		// 店铺实际入账
		BigDecimal shopDecreaseValue = new BigDecimal(0);
		// 平台扣除金额
		BigDecimal platformIncreaseValue = new BigDecimal(0);
		// 店铺分润百分比
		ShopProfitDetailVO shopProfit = shopProfitService.getShopProfitByShopId(shopId);
		// 店铺扣除比例
		BigDecimal profit = shopProfit.getRate().divide(rateBase);
		
		// 平台获利
		platformIncreaseValue = shopDecreaseValue.add(decreaseValue).multiply(profit);
		// 店铺出账金额
		shopDecreaseValue = decreaseValue.subtract(platformIncreaseValue);
		// -----------------------------------------处理店铺账户------------------------------------------------
		// 店铺出账
		decreaseBalanceById(shopAccountId, shopDecreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
		
		doCalcOrderPayedRefundProfitAndDecreaseBalance(shopId, orderId, gameOrderId, outTradeNo, operateDate, rateBase, platformIncreaseValue);
	}

	*//**
	 * 处理普通订单未核销退款业务员及分润退款
	 * @param shopId
	 * @param orderId
	 * @param gameOrderId
	 * @param outTradeNo
	 * @param operateDate
	 * @param rateBase
	 * @param platformIncreaseValue
	 *//*
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doCalcOrderPayedRefundProfitAndDecreaseBalance(Long shopId, String orderId, String gameOrderId, String outTradeNo, Date operateDate, final BigDecimal rateBase, BigDecimal platformIncreaseValue) {
		ShopAgentVO agentSaleman = salemanService.getAgentAndSalemanByShopId(shopId);
		if (Objects.nonNull(agentSaleman)) {
			// -----------------------------------------处理业务员账户----------------------------------------------
			if (Objects.nonNull(agentSaleman.getSaleman())) {
				SalemanVO saleman = agentSaleman.getSaleman();
				// 营业员账户s
				PayAccountVO salemanAccount = getPayAccountByUserId(saleman.getUserId());
				// 业务员费率
				BigDecimal salemanProfit = saleman.getRate().divide(rateBase);
				// 业务员可分得金额
				BigDecimal salemanIncreaseValue = platformIncreaseValue.multiply(salemanProfit);
				if (salemanIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
					// 业务员入账
					decreaseBalanceById(salemanAccount.getId(), salemanIncreaseValue.setScale(0, BigDecimal.ROUND_DOWN));
				}
			}
			
			// -----------------------------------------处理区级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCountryAgent())) {
				SalemanVO country = agentSaleman.getCountryAgent();
				if (Objects.nonNull(country)) {
					// 账户
					PayAccountVO countryAccount = getPayAccountByUserId(country.getUserId());
					// 费率
					BigDecimal countryProfit = country.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal countryIncreaseValue = platformIncreaseValue.multiply(countryProfit);
					if (countryIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						decreaseBalanceById(countryAccount.getId(), countryIncreaseValue);
					}
				}
			}
			
			// -----------------------------------------处理市级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO city = agentSaleman.getCountryAgent();
				if (Objects.nonNull(city)) {
					// 账户
					PayAccountVO cityAccount = getPayAccountByUserId(city.getUserId());
					// 费率
					BigDecimal cityProfit = city.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal cityIncreaseValue = platformIncreaseValue.multiply(cityProfit);
					if (cityIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						decreaseBalanceById(cityAccount.getId(), cityIncreaseValue);
					}
				}
			}
			
			// -----------------------------------------处理省级 ----------------------------------------------
			if (Objects.nonNull(agentSaleman.getCityAgent())) {
				SalemanVO province = agentSaleman.getCountryAgent();
				if (Objects.nonNull(province)) {
					// 账户
					PayAccountVO provinceAccount = getPayAccountByUserId(province.getUserId());
					// 费率
					BigDecimal provinceProfit = province.getRate().divide(rateBase);
					// 可分得金额
					BigDecimal provinceIncreaseValue = platformIncreaseValue.multiply(provinceProfit);
					if (provinceIncreaseValue.compareTo(BigDecimal.ZERO) != 0) {
						// 入账
						decreaseBalanceById(provinceAccount.getId(), provinceIncreaseValue);
					}
				}
			}
		}
	}*/
	
	/**
	 * 根据业务员查询账户资产信息
	 * @param salemanId
	 * @return
	 */
	@Override
	public List<AcountMoneyVO> getAcountMoneyByShopAdminId(Long shopAdminId) {
		//根据业务员查询店铺List
		List<ShopBasicVO> basicInfoVOList = shopService.getShopBasicInfoByMangerUserId(shopAdminId);
		if(ObjectUtils.isEmpty(basicInfoVOList)) {
			return null;
		}
		
		//准备查询账户条件
		List<Long> shopIdList = new ArrayList<>();
		for(ShopBasicVO basicInfoVO :basicInfoVOList) {
			shopIdList.add(basicInfoVO.getId());
		}
		//查询账户信息
		List<AcountMoneyVO> accounts = payAccountMapper.getAcountMoneyByShopList(shopIdList);
		for(int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setShopId(basicInfoVOList.get(i).getId());
			accounts.get(i).setShopName(basicInfoVOList.get(i).getName());
			accounts.get(i).setShopLogo(basicInfoVOList.get(i).getLogo());
		}
		return accounts;
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcPayedCallbackProfitOrderAndIncreaseBalance(TradeDataVO data) {
		AssertUtil.isNull(data.getShopAccountId(), "PayAccountServiceImpl|calcPayedCallbackProfitOrderAndIncreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(data.getShopId(), "PayAccountServiceImpl|calcPayedCallbackProfitOrderAndIncreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(data.getOrderId(), "PayAccountServiceImpl|calcPayedCallbackProfitOrderAndIncreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(data.getIncreaseValue(), "PayAccountServiceImpl|calcPayedCallbackProfitOrderAndIncreaseBalance|传入参数increaseValue为空");
		AssertUtil.isNull(data.getOperateDate(), "PayAccountServiceImpl|calcPayedCallbackProfitOrderAndIncreaseBalance|传入参数operateDate为空");
		orderProfitExcutor.processCalcAndTakeAccountBalance(data);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcPayedCallbackProfitGameOrderAndIncreaseBalance(TradeDataVO data) {
		AssertUtil.isNull(data.getShopAccountId(), "PayAccountServiceImpl|calcPayedCallbackProfitGameOrderAndIncreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(data.getShopId(), "PayAccountServiceImpl|calcPayedCallbackProfitGameOrderAndIncreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(data.getOrderId(), "PayAccountServiceImpl|calcPayedCallbackProfitGameOrderAndIncreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(data.getIncreaseValue(), "PayAccountServiceImpl|calcPayedCallbackProfitGameOrderAndIncreaseBalance|传入参数increaseValue为空");
		AssertUtil.isNull(data.getOperateDate(), "PayAccountServiceImpl|calcPayedCallbackProfitGameOrderAndIncreaseBalance|传入参数operateDate为空");
		gameOrderProfitExecutor.processCalcAndTakeAccountBalance(data);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcValidationTicketProfitIncreaseAccountBalance(TradeDataVO data) {
		AssertUtil.isNull(data.getShopId(), "PayAccountServiceImpl|calcValidationTicketProfitIncreaseAccountBalance|传入参数shopId为空");
		AssertUtil.isNull(data.getOrderId(), "PayAccountServiceImpl|calcValidationTicketProfitIncreaseAccountBalance|传入参数orderid为空");
		AssertUtil.isNull(data.getOperateDate(), "PayAccountServiceImpl|calcValidationTicketProfitIncreaseAccountBalance|传入参数operateDate为空");
		PayAccountVO shopAccount = getPayAccountByShopId(data.getShopId());
		if (Objects.isNull(shopAccount)) {
			log.error("商户账号不存在 |shopId:" + data.getShopId());
			throw new QmdException("商户账号不存在");
		}
		
		TradeDetailVO trade = tradeDetailService.getTradeDetailByOrderIdAndType(data.getOrderId(), TradeDetailTypeEnum.SHOPORDER);
		if (Objects.isNull(trade)) {
			log.error("已支付流水信息不存在 |orderId:" + data.getOrderId());
			throw new QmdException("支付流水信息不存在");
		}
		
		data.setShopAccountId(shopAccount.getId());
		data.setIncreaseValue(trade.getAmount());
		data.setOutTradeNo(trade.getOutTradeNo());
		orderValidationTicketProfitExecutor.processCalcAndTakeAccountBalance(data);
		
		// 修改订单流水为 店铺已核销订单流水
		trade.setProductName(TradeDetailTypeEnum.SHOPORDERVT.getName());
		trade.setType(TradeDetailTypeEnum.SHOPORDERVT.getCode());
		tradeDetailService.updateTradeDetail(trade);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcOrderPayedRefundProfitAndDecreaseBalance(TradeDataVO data) {
		AssertUtil.isNull(data.getShopAccountId(), "PayAccountServiceImpl|calcOrderPayedRefundProfitAndDecreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(data.getShopId(), "PayAccountServiceImpl|calcOrderPayedRefundProfitAndDecreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(data.getOrderId(), "PayAccountServiceImpl|calcOrderPayedRefundProfitAndDecreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(data.getDecreaseValue(), "PayAccountServiceImpl|calcOrderPayedRefundProfitAndDecreaseBalance|传入参数decreaseValue为空");
		AssertUtil.isNull(data.getOperateDate(), "PayAccountServiceImpl|calcOrderPayedRefundProfitAndDecreaseBalance|传入参数operateDate为空");
		orderPayedRefundProfitExecutor.processCalcAndTakeAccountBalance(data);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void calcOrderValidationTicketRefundProfitAndDecreaseBalance(TradeDataVO data) {
		AssertUtil.isNull(data.getShopAccountId(), "PayAccountServiceImpl|calcOrderValidationTicketRefundProfitAndDecreaseBalance|传入参数shopAccountId为空");
		AssertUtil.isNull(data.getShopId(), "PayAccountServiceImpl|calcOrderValidationTicketRefundProfitAndDecreaseBalance|传入参数shopId为空");
		AssertUtil.isNull(data.getOrderId(), "PayAccountServiceImpl|calcOrderValidationTicketRefundProfitAndDecreaseBalance|传入参数orderid为空");
		AssertUtil.isNull(data.getDecreaseValue(), "PayAccountServiceImpl|calcOrderValidationTicketRefundProfitAndDecreaseBalance|传入参数decreaseValue为空");
		AssertUtil.isNull(data.getOperateDate(), "PayAccountServiceImpl|calcOrderValidationTicketRefundProfitAndDecreaseBalance|传入参数operateDate为空");
		orderValidationTicketRefundProfitExecutor.processCalcAndTakeAccountBalance(data);
	}
}
