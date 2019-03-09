package cn.qumiandan.payaccount.impl;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Queues;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.ParentDataEnum;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.GameWinEnum;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.orderreturn.api.IOrderReturnService;
import cn.qumiandan.orderreturn.vo.RefundResultVO;
import cn.qumiandan.orderunprocessed.api.IOrderUnprocessedService;
import cn.qumiandan.orderunprocessed.enums.OrderUnprocessedStatusEnum;
import cn.qumiandan.orderunprocessed.enums.OrderUnprocessedTypeEnum;
import cn.qumiandan.orderunprocessed.vo.OrderUnprocessedVO;
import cn.qumiandan.pay.enums.PayTypeEnum;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.api.process.AccountTask;
import cn.qumiandan.payaccount.api.process.IAccountProcess;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.enums.ValidationTicketStatus;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;
import lombok.extern.slf4j.Slf4j;



/**
 * 处理并发回调账户增余额
 * @author yuleidian
 * @version 创建时间：2018年12月28日 上午9:57:19
 */
@Slf4j
@Component
@Service(interfaceClass = IAccountProcess.class)
public class FailAccountProcess implements IAccountProcess {

	/** 处理账户增加 */
	private final BlockingQueue<AccountTask<TradeDataVO>> processQueue = Queues.newLinkedBlockingQueue(999);
	
	@Autowired
	private IPayAccountService payAccountService; 
	
	@Autowired
	private IOrderUnprocessedService orderUnprocessedService;
	
	@Autowired
	private ITradeDetailService tradeDetailService;

	@Reference
	private IOrderService orderService;

	@Reference
	private IGameOrderService gameOrderService;
	
	@Reference
	private IValidationTicketService validationTicketService;
	
	@Reference
	private IShopService shopService;
	
	@Reference
	private IShopUserService shopUserService;
	
	@Reference
	private IOrderReturnService orderReturnService;
	
	
	@Override
	public void add(AccountTask<TradeDataVO> task) {
		try {
			processQueue.offer(task, 10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("FailAccountProcess|add|tradeId:" + task.getSourceData().getTradeId() + "|orderId:" + task.getSourceData().getOrderId()
					+ "|gameOrderId:" + task.getSourceData().getGameOrderId());
			throw new QmdException("处理账户队列 等待超时");
		}
	}

	@Override
	public void run(String... args) throws Exception {
		// 目前用单个线程处理 余额增加, 如果上线压力较大 可调高线程数
		ExecutorService service = new ThreadPoolExecutor(1, 999, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		service.execute(() -> {
			while (true) {
				AccountTask<TradeDataVO> task;
				try {
					task = processQueue.take();
					if (Objects.nonNull(task)) {
						if (task.getTimes() < 10) {
							try {
								processAccountIncreaseOrDecreaseException(task.getSourceData());
							} catch (Exception e) {
								task.increaseTimes();
								if (task.getTimes() < 10) {
									add(task);
								} else {
									OrderUnprocessedVO unprocessed = new OrderUnprocessedVO();
									unprocessed.setOrderId(task.getSourceData().getOrderId());
									unprocessed.setGameOrderId(task.getSourceData().getGameOrderId());
									unprocessed.setTradeId(task.getSourceData().getTradeId());
									unprocessed.setType(OrderUnprocessedTypeEnum.ORDERUNPROCESSED_EXCEPTION.getCode());
									unprocessed.setStatus(OrderUnprocessedStatusEnum.PROCESSING.getCode());
									unprocessed.setCreateDate(task.getSourceData().getOperateDate());
									unprocessed.setCreateId(ParentDataEnum.RootId.getCode());
									orderUnprocessedService.addOrderUnprocessed(unprocessed);
									log.error("账户处理分润添加余额失败|tradeId:" + task.getSourceData().getTradeId() + "|orderId:" + task.getSourceData().getTradeId() + "|尝试10次任然失败");
								}
							}
						}
					}
				} catch (Exception e1) {
					log.error("处理账户队列异常", e1);
				}
			}
		});
		//service.shutdown();
	}

	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	private void processAccountIncreaseOrDecreaseException(TradeDataVO data) {
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
	
	/**
	 * 处理普通订单核销 因并发失败的增加账户金额操作 
	 * @param data
	 */
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessValidationTicketAccountIncreaseException(TradeDataVO data) {
		// 订单状态必须为【已支付】才能进行核销
		ValidationTicketVO ticket = validationTicketService.getUnuseValidationTicketByTicketCode(data.getTicketCode());
		if (ObjectUtils.isEmpty(ticket)) {
			log.error("FailAccountProcess|doProcessValidationTicketAccountIncreaseException|该核销券不存在或者已经过期 ticketCode" + data.getTicketCode());
			//throw new QmdException("该核销券不存在或者已经过期");
			return;
		}
		
		// 查询该登陆用户的id所管理的店铺id
		List<Long> shopidList = shopUserService.getShopIdListByUserId(data.getUserId());
		// 不包含次管理店铺 直接抛出异常
		if (!shopidList.contains(ticket.getShopId())) {
			log.error("FailAccountProcess|doProcessValidationTicketAccountIncreaseException|该用户不是此核销券所在商家的管理人员，无法核销！ userId:" + data.getUserId() + "|shopId:" + ticket.getShopId());
			// throw new QmdException("该用户不是此核销券所在商家的管理人员，无法核销！");
			return;
		}
		
		OrderVO orderVO = orderService.getOrderById(ticket.getOrderId());
		if (orderVO == null) {
			log.error("FailAccountProcess|doProcessValidationTicketAccountIncreaseException|此核销券对应的订单不存在 orderId:" + ticket.getOrderId());
			//throw new QmdException("此核销券对应的订单不存在");
			return;
		}
		
		if (orderVO.getOrderStatus() != OrderStatusEnum.Paid.getCode()) {
			log.error("FailAccountProcess|doProcessValidationTicketAccountIncreaseException|此核销券对应的订单状态不能核销 orderStatus:" + orderVO.getOrderStatus());
			//throw new QmdException("此核销券对应的订单状态不能核销");
			return;
		}
		
		//为中奖，且 订单实付 > 0 才能调用账户余额增加
		if (GameWinEnum.NotWin.getCode().equals(orderVO.getWin()) && orderVO.getNeedPayAmount().doubleValue() > 0) {
			//calcValidationTicketProfitIncreaseAccountBalance(orderVO.getShopId(), orderVO.getOrderId(), data.getOperateDate());
			payAccountService.calcValidationTicketProfitIncreaseAccountBalance(data);
		}

		ticket.setStatus(ValidationTicketStatus.Used.getCode());
		orderVO.setOrderStatus(OrderStatusEnum.TradeComplete.getCode());
		validationTicketService.updateValidationTicketAndOrderInfo(ticket, orderVO);
	}
	
	/**
	 * 处理扫呗回调 因并发失败的增加账户金额操作 
	 * @param data
	 */
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	private void doProcessAccountIncreaseException(TradeDataVO data) {
		AssertUtil.isNull(data, "FailAccountProcess|processAccountIncreaseBalance|传入参数data为空");
		TradeDetailVO trade = tradeDetailService.getTradeDetailById(data.getTradeId());
		if (!TradePayStatusEnum.CREATE.getCode().equals(trade.getStatus())) {
			log.error("FailAccountProcess|processAccountIncreaseBalance|扫呗支付回调 订单流水已被处理 tradeStatus" + trade.getStatus());
			return;
		}
		
		OrderVO order = orderService.getOrderById(data.getOrderId());
		if (!OrderStatusEnum.Paying.getCode().equals(order.getOrderStatus())) {
			log.error("FailAccountProcess|processAccountIncreaseBalance|扫呗支付回调 订单状态已不处于支付中 orderId:" + data.getOrderId());
			return;
		}
		
		GameExtendVO gameOrder = gameOrderService.getGameOrderByGameId(data.getGameOrderId());
		if (StringUtils.isNotBlank(data.getGameOrderId())) {
			if (!OrderStatusEnum.Paying.getCode().equals(gameOrder.getOrderStatus())) {
				log.error("FailAccountProcess|processAccountIncreaseBalance|扫呗游戏支付回调 订单已被处理过 gameOrderStatus:" + gameOrder.getOrderStatus());
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
			payAccountService.calcPayedCallbackProfitOrderAndIncreaseBalance(data);
		} else {
			//calcPayedCallbackProfitGameOrderAndIncreaseBalance(trade.getAccountInId(), order.getShopId(), order.getOrderId(), data.getGameOrderId(), trade.getOutTradeNo(), data.getIncreaseValue(), data.getOperateDate());
			payAccountService.calcPayedCallbackProfitGameOrderAndIncreaseBalance(data);
		}
		log.info("FailAccountProcess|doProcessAccountIncreaseException|分润入账完毕tradeId:" + trade.getId());
		
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
	
	/**
	 * 处理扫呗回调 因并发失败的减少账户金额操作 
	 * @param data
	 */
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
		PayAccountVO shopAccount = payAccountService.getPayAccountById(data.getShopAccountId());
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
			payAccountService.calcOrderValidationTicketRefundProfitAndDecreaseBalance(data);
		} else {
			//calcOrderPayedRefundProfitAndDecreaseBalance(trade.getAccountInId(), data.getShopId(), order.getOrderId(), null, data.getOutTradeNo(), data.getOutRefundNo(), data.getDecreaseValue(), trade.getPayChannel(), data.getOperateDate());
			payAccountService.calcOrderPayedRefundProfitAndDecreaseBalance(data);
		}
		log.info("FailAccountProcess|doProcessAccountDecreaseException|分润出账完毕tradeId:" + trade.getId());
		
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
}
