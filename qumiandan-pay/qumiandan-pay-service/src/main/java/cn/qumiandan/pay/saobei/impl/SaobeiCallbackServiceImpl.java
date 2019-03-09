package cn.qumiandan.pay.saobei.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.pay.enums.PayTypeEnum;
import cn.qumiandan.pay.saobei.enums.SaoBeiResult;
import cn.qumiandan.pay.saobei.model.request.helper.AttachHelper;
import cn.qumiandan.pay.saobei.service.IPlatformMerchantService;
import cn.qumiandan.pay.saobei.service.ISaobeiCallbackService;
import cn.qumiandan.pay.saobei.vo.PlatformMerchantVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.callback.CreateMerchatCallbackVO;
import cn.qumiandan.pay.saobei.vo.response.pay.callback.JSPayCallbackVO;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.api.process.IAccountProcess;
import cn.qumiandan.payaccount.vo.AccountExceptionTaskVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.shop.api.IShopAuditRecordService;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.enums.ShopAuditStatusEnum;
import cn.qumiandan.shop.enums.ShopStatusEnum;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.GsonHelper;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ISaobeiCallbackService.class)
public class SaobeiCallbackServiceImpl implements ISaobeiCallbackService {

	@Reference
	private IShopAuditRecordService shopAuditRecordService;
	
	@Reference
	private IShopService shopservice;
	
	@Autowired
	private IPayAccountService payAccountService;
	
	@Autowired
	private ITradeDetailService tradeDetailService;
	
	@Reference
	private IOrderService orderService;

	@Reference
	private IGameOrderService gameOrderService;

	@Reference
	private IShopService shopService;
	
	@Reference
	private IValidationTicketService validationTicketService;
	
	@Autowired
	private IPlatformMerchantService platformMerchantService;
	
	@Autowired
	private IAccountProcess accountProcess;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int createMerchantCallback(CreateMerchatCallbackVO vo) {
		AssertUtil.isNull(vo, "SaobeiCallbackServiceImpl|createMerchantCallback|传入参数vo为空");
		ShopBasicVO shopBasicVO = shopservice.getShopByMerchantNo(vo.getMerchantNo());
		if (Objects.nonNull(shopBasicVO) && Objects.nonNull(shopBasicVO.getId())) {
			if (vo.getResultCode() == SaoBeiResult.RESULT_SUCCESS.getCode()) {
				shopservice.updateShopStatus(shopBasicVO.getId(), ShopStatusEnum.PASS.getCode());
			} else {
				// 修改店铺状态
				// shopservice.updateShopStatus(shopBasicVO.getId(), ShopStatusEnum.UPDATENUPASS.getCode());
				
				// 插入审核信息
				Date now = new Date();
				ShopAuditRecordVO record = new ShopAuditRecordVO();
				record.setShopId(shopBasicVO.getId());
				record.setAuditDate(now);
				record.setAuditor(1L);
				record.setCreateId(1L);
				record.setCreateDate(now);
				record.setRemark(vo.getReturnMsg());
				record.setStatus(ShopAuditStatusEnum.UNPASS.getCode());
				shopAuditRecordService.addShopAuditRecord(record);
			}
		} else {
			log.info("SaobeiCallbackServiceImpl|createMerchantCallback|扫呗回调商户创建 merchantNo:" + vo.getMerchantNo() + "数据库中没有查询到商户信息");
		}
		return 1;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int jsPayCallback(JSPayCallbackVO vo) {
		AssertUtil.isNull(vo, "SaobeiCallbackServiceImpl|jsPayCallback|传入参数vo为空");
		if (!SaoBeiResult.RESULT_SUCCESS.getCode().equals(vo.getResultCode())) {
			// 询问扫呗 目前不会出现这种非01的情况
			log.info("扫呗回调出现返回resultCode不为01的情况resultCode:" + vo.getResultCode());
			return 1;
		} 
		
		AttachHelper attach = GsonHelper.fromJson(vo.getAttach(), AttachHelper.class);
		if (Objects.isNull(attach.getTradeId())) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调信息中没有流水tradeId:" + attach.getTradeId());
			throw new QmdException("扫呗支付回调信息中没有流水tradeId");
		}
		
		PlatformMerchantVO merchantInfo = platformMerchantService.getPlatformMerchantByMerchantNo(vo.getMerchantNo());
		if (Objects.isNull(merchantInfo)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调没有找到商户信息 MerchantNo:" + vo.getMerchantNo());
			throw new QmdException("扫呗支付回调没有找到商户信息");
		}
		
		TradeDetailVO trade = tradeDetailService.getTradeDetailById(attach.getTradeId());
		if (Objects.isNull(trade)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调 无法查询到流水信息tradeId:" + attach.getTradeId());
			throw new QmdException("扫呗支付回调 无法查询到流水信息");
		}
		
		if (!TradePayStatusEnum.CREATE.getCode().equals(trade.getStatus())) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调 订单流水已被处理 tradeStatus" + trade.getStatus());
			throw new QmdException("扫呗支付回调 订单流水已被处理");
		}
		// 比较回调金额和流水需支付金额
		BigDecimal increaseValue = new BigDecimal(vo.getTotalFee());
		if (increaseValue.compareTo(trade.getAmount().setScale(0, BigDecimal.ROUND_DOWN)) != 0) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗回调支付金额与订单流水金额不一致 tradeId:" + attach.getTradeId() + "|amount:" + increaseValue);
			throw new QmdException(PayErrorCode.PAY1003.getCode(), "扫呗回调支付金额与订单流水金额不一致");
		}
		
		// 验签
		if (StringUtils.isBlank(vo.getKeySign()) || !vo.getKeySign().equals(vo.sign(merchantInfo.getAccessToken()))) {
			log.info("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调验签未通过 tradeId:" + attach.getTradeId() +
					"|orderId:" + attach.getOrderId());
			throw new QmdException("扫呗支付回调  验签未通过");
		}
		
		OrderVO order = orderService.getOrderById(attach.getOrderId());
		if (Objects.isNull(order)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调 无法查询到订单信息 orderId:" + attach.getOrderId());
			throw new QmdException("扫呗支付回调 无法查询到订单信息");
		}
		
		if (!OrderStatusEnum.Paying.getCode().equals(order.getOrderStatus())) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调 订单状态已不处于支付中 orderId:" + attach.getOrderId());
			throw new QmdException("扫呗支付回调 订单状态已不处于支付中");
		}
		
		Date now = new Date();
		// 处理支付流水信息
		trade.setCallbackAmount(increaseValue);
		trade.setChannelTradeNo(vo.getChannelTradeNo());
		trade.setCallbackDate(now);
		trade.setPayDate(vo.getPayedEndTime());
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		tradeDetailService.updateTradeDetail(trade);
		
		TradeDataVO data = new TradeDataVO();
		data.setProcessType(TradePayStatusEnum.PAYED);
		data.setIsGame(false);
		data.setIsVT(false);
		data.setChannelTradeNo(trade.getChannelTradeNo());
		data.setIncreaseValue(increaseValue);
		data.setOrderId(order.getOrderId());
		data.setOutTradeNo(trade.getOutTradeNo());
		data.setShopAccountId(trade.getAccountInId());
		data.setShopId(order.getShopId());
		data.setTradeId(trade.getId());
		data.setOperateDate(now);
		data.setPayEndDate(vo.getPayedEndTime());
		data.setPayChannal(trade.getPayChannel());
		try {
			// 计算分润 入账
			//payAccountService.calcPayedCallbackProfitOrderAndIncreaseBalance(trade.getAccountInId(), order.getShopId(), order.getOrderId(), null, trade.getOutTradeNo(), increaseValue, now);
			payAccountService.calcPayedCallbackProfitOrderAndIncreaseBalance(data);
			log.info("SaobeiCallbackServiceImpl|jsPayCallback|分润入账完毕tradeId:" + trade.getId());
		} catch (QmdException e) {
			AccountExceptionTaskVO task = new AccountExceptionTaskVO();
			task.setTradeData(data);
			accountProcess.add(task);
			throw e;
		}

		// 处理订单
		order.setPayDate(now);
		order.setAmountTotal(increaseValue);
		order.setPayChannel(trade.getPayChannel());
		order.setOutTradeNo(trade.getOutTradeNo());
		order.setChannelTradeNo(trade.getChannelTradeNo());
		order.setOrderStatus(OrderStatusEnum.Paid.getCode());
		// rpc垮裤 防止分布式事务
		validationTicketService.createValidationTicketAndUpdateOrderStatus(order);
		//orderService.updateOrder(order);
		return 1;
	}

	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int jsPayCallbackGame(JSPayCallbackVO vo) {
		AssertUtil.isNull(vo, "SaobeiCallbackServiceImpl|jsPayCallbackGame|传入参数vo为空");
		if (!SaoBeiResult.RESULT_SUCCESS.getCode().equals(vo.getResultCode())) {
			// 询问扫呗 目前不会出现这种非01的情况
			log.info("扫呗回调出现返回resultCode不为01的情况resultCode:" + vo.getResultCode());
			return 1;
		} 
		
		AttachHelper attach = GsonHelper.fromJson(vo.getAttach(), AttachHelper.class);
		if (Objects.isNull(attach.getTradeId())) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗支付回调信息中没有流水tradeId:" + attach.getTradeId());
			throw new QmdException("扫呗支付回调信息中没有流水tradeId");
		}
		
		PlatformMerchantVO merchantInfo = platformMerchantService.getPlatformMerchantByMerchantNo(vo.getMerchantNo());
		if (Objects.isNull(merchantInfo)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗支付回调没有找到商户信息 MerchantNo:" + vo.getMerchantNo());
			throw new QmdException("扫呗支付回调没有找到商户信息");
		}
		
		TradeDetailVO trade = tradeDetailService.getTradeDetailById(attach.getTradeId());
		if (Objects.isNull(trade)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗游戏支付回调 无法查询到流水信息tradeId:" + attach.getTradeId());
			throw new QmdException("扫呗支付回调 无法查询到流水信息");
		}
		
		if (!TradePayStatusEnum.CREATE.getCode().equals(trade.getStatus())) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗游戏支付回调 订单流水已被处理 tradeStatus" + trade.getStatus());
			throw new QmdException("扫呗游戏支付回调 订单流水已被处理");
		}
		// 比较回调金额和流水需支付金额
		BigDecimal increaseValue = new BigDecimal(vo.getTotalFee());
		if (increaseValue.compareTo(trade.getAmount().setScale(0, BigDecimal.ROUND_DOWN)) != 0) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallback|扫呗回调支付金额与订单流水金额不一致 tradeId:" + attach.getTradeId() + "|amount:" + increaseValue);
			throw new QmdException(PayErrorCode.PAY1003.getCode(), "扫呗回调支付金额与订单流水金额不一致");
		}
		
		// 验签
		if (StringUtils.isBlank(vo.getKeySign()) || !vo.getKeySign().equals(vo.sign(merchantInfo.getAccessToken()))) {
			log.info("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗游戏支付回调验签未通过 tradeId:" + attach.getTradeId() +
					"|orderId:" + attach.getOrderId());
			throw new QmdException("扫呗支付回调  验签未通过");
		}
		
		OrderVO order = orderService.getOrderById(attach.getOrderId());
		if (Objects.isNull(order)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗游戏支付回调 无法查询到订单信息 orderId:" + attach.getOrderId());
			throw new QmdException("扫呗游戏支付回调 无法查询到订单信息");
		}
		
		GameExtendVO gameOrder = gameOrderService.getGameOrderByGameId(attach.getGameOrderId());
		if (Objects.isNull(gameOrder)) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗游戏支付回调 无法查询到游戏订单信息 gameOrderId:" + attach.getGameOrderId());
			throw new QmdException("扫呗游戏支付回调 无法查询到订单信息");
		}
		
		if (!OrderStatusEnum.Paying.getCode().equals(gameOrder.getOrderStatus())) {
			log.error("SaobeiCallbackServiceImpl|jsPayCallbackGame|扫呗游戏支付回调 订单已被处理过 gameOrderStatus:" + gameOrder.getOrderStatus());
			throw new QmdException("扫呗游戏支付回调 订单已被处理过");
		}
		
		Date now = new Date();
		// 处理支付流水信息
		trade.setCallbackAmount(increaseValue);
		trade.setChannelTradeNo(vo.getChannelTradeNo());
		trade.setCallbackDate(now);
		trade.setPayDate(now);
		trade.setStatus(TradePayStatusEnum.PAYED.getCode());
		tradeDetailService.updateTradeDetail(trade);
				
		TradeDataVO data = new TradeDataVO();
		data.setProcessType(TradePayStatusEnum.PAYED);
		data.setIsGame(true);
		data.setIsVT(false);
		data.setChannelTradeNo(trade.getChannelTradeNo());
		data.setIncreaseValue(increaseValue);
		data.setOrderId(order.getOrderId());
		data.setOutTradeNo(trade.getOutTradeNo());
		data.setShopAccountId(trade.getAccountInId());
		data.setShopId(order.getShopId());
		data.setTradeId(trade.getId());
		data.setGameOrderId(gameOrder.getId());
		data.setOperateDate(now);
		data.setPayEndDate(vo.getPayedEndTime());
		data.setPayChannal(trade.getPayChannel());
		
		try {
			// 计算分润 入账
			//payAccountService.calcPayedCallbackProfitGameOrderAndIncreaseBalance(trade.getAccountInId(), order.getShopId(), order.getOrderId(), gameOrder.getId(), trade.getOutTradeNo(), increaseValue, now);
			payAccountService.calcPayedCallbackProfitGameOrderAndIncreaseBalance(data);
			log.info("SaobeiCallbackServiceImpl|jsPayCallbackGame|分润入账完毕tradeId:" + trade.getId());
		} catch (Exception e) {
			AccountExceptionTaskVO task = new AccountExceptionTaskVO();
			task.setTradeData(data);
			accountProcess.add(task);
			throw e;
		}

		// 处理订单
		gameOrder.setPayDate(now);
		gameOrder.setAmountTotal(increaseValue);
		gameOrder.setPayChannel(trade.getPayChannel());
		gameOrder.setOutTradeNo(trade.getChannelTradeNo());
		gameOrder.setOrderStatus(OrderStatusEnum.Paid.getCode());
		gameOrder.setChannelTradeNo(trade.getChannelTradeNo());
		// 更新累加付款资金
		order.setPayChannel(PayTypeEnum.QMDPay.getCode());
		order.setGameAmount(order.getGameAmount().add(increaseValue));
		orderService.updateOrderInfoAndGameOrderInfo(order, gameOrder);
		// 游戏支付成功，接收到回到，就开始游戏
		gameOrderService.startShakeGame(gameOrder);
		return 1;
	}
}
