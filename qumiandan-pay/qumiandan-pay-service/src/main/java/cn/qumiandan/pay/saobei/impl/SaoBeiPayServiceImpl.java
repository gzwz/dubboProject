package cn.qumiandan.pay.saobei.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.httprequest.api.IHttpRequestService;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.orderreturn.api.IOrderReturnService;
import cn.qumiandan.orderreturn.vo.OrderReturnVO;
import cn.qumiandan.orderreturn.vo.RefundResultVO;
import cn.qumiandan.pay.saobei.config.SaoBeiConfig;
import cn.qumiandan.pay.saobei.model.request.helper.AttachHelper;
import cn.qumiandan.pay.saobei.model.request.pay.JSPayReq;
import cn.qumiandan.pay.saobei.model.request.pay.JSRefundReq;
import cn.qumiandan.pay.saobei.model.request.pay.WechatAuthopenIdReq;
import cn.qumiandan.pay.saobei.service.IPlatformMerchantService;
import cn.qumiandan.pay.saobei.service.ISaoBeiPayService;
import cn.qumiandan.pay.saobei.vo.JSPayVO;
import cn.qumiandan.pay.saobei.vo.JSRefundVO;
import cn.qumiandan.pay.saobei.vo.PlatformMerchantVO;
import cn.qumiandan.pay.saobei.vo.WechatAuthopenIdVO;
import cn.qumiandan.pay.saobei.vo.response.pay.JSPayResVO;
import cn.qumiandan.pay.saobei.vo.response.pay.JSRefundRes;
import cn.qumiandan.pay.saobei.vo.response.pay.WechatAuthopenIdResVO;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.api.process.IAccountProcess;
import cn.qumiandan.payaccount.vo.AccountExceptionTaskVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.trade.enums.TradeDetailTypeEnum;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.trade.enums.TradeTypeEnums;
import cn.qumiandan.trade.vo.TradeDetailVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.GsonHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = ISaoBeiPayService.class)
public class SaoBeiPayServiceImpl implements ISaoBeiPayService {
	
	@Autowired
	private SaoBeiConfig saobeiConfig;
	
	@Reference
	private IHttpRequestService httpRequestService;
	
	@Autowired
	private IPlatformMerchantService platformMerchantService;
	
	@Reference
	private IOrderService orderService;
	
	@Reference
	private IGameOrderService gameOrderService;
	
	@Autowired
	private IPayAccountService payAccountService;
	
	@Autowired
	private ITradeDetailService tradeDetailService;
	
	@Reference
	private IShopService shopService;
	
	@Autowired
	private IAccountProcess accountProcess;
	
	@Reference
	private IOrderReturnService orderReturnService;
	
	@Override
	public String decrypt(String cryptograph) {
		return null;
	}
	
	@Override
	public WechatAuthopenIdResVO getAuthopenId(WechatAuthopenIdVO vo) {
		AssertUtil.isNull(vo, "SaoBeiPayServiceImpl|getAuthopenId|传入参数vo为空");
		WechatAuthopenIdResVO result = new WechatAuthopenIdResVO();
		// 随机获取一个商户信息
		PlatformMerchantVO merchant = platformMerchantService.randomGetMerchant();
		if (Objects.nonNull(merchant)) {
			WechatAuthopenIdReq req = new WechatAuthopenIdReq();
			req.setMerchantNo(merchant.getMerchantNo());
			req.setTerminalNo(merchant.getTerminalNo());
			req.setAccessToken(merchant.getAccessToken());
			req.setRedirectUri(vo.getRedirectUri());
			result.setMerchantId(merchant.getId());
			result.setUrl(req.getUrl(saobeiConfig.getAuthopenidUrl()));
		}
		log.info("SaoBeiPayServiceImpl|getAuthopenId|返回前端url:" + result.getUrl());
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSPayResVO jsPay(JSPayVO vo) {
		AssertUtil.isNull(vo, "SaobeiBizServiceImpl|platformOrderExpectPayInfo|传入参数vo为空");
		OrderVO orderInfo = orderService.getOrderById(vo.getOrderId());
		if (Objects.isNull(orderInfo)) {
			log.error("查询不到订单|orderId:" + vo.getOrderId());
			throw new QmdException("系统异常");
		}
		return processNormalOrder(vo, orderInfo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSPayResVO gameJsPay(JSPayVO vo) {
		AssertUtil.isNull(vo, "SaobeiBizServiceImpl|platformOrderExpectPayInfo|传入参数vo为空");
		OrderVO orderInfo = orderService.getOrderById(vo.getOrderId());
		if (Objects.isNull(orderInfo)) {
			log.error("查询不到订单|orderId:" + vo.getOrderId());
			throw new QmdException("系统异常");
		}
		// 游戏趣免单
		return processGameOrder(vo, orderInfo);
	}

	private JSPayResVO doJsPay(JSPayReq req) {
		Map<String, String> condition = req.getRequestParameter();
		log.info(new StringBuilder("SaoBeiPayServiceImpl|").append("jsPay|").append("request:").append(req.toJson()).toString());
		String resultStr = httpRequestService.doPostForJson(saobeiConfig.getJsPayUrl(), condition);
		log.info(new StringBuilder("SaoBeiPayServiceImpl|").append("jsPay|").append("response:").append(resultStr).toString());
		JSPayResVO result = null;
		if (StringUtils.isNotBlank(resultStr)) {
			result = GsonHelper.fromJson(resultStr, JSPayResVO.class);
		} else {
			log.error("请求扫呗公众号预支付(统一下单) 返回空字符串");
			throw new QmdException("系统异常");
		}
		return result;
	}
	
	/**
	 * 处理普通订单信息
	 * @param vo
	 * @param orderInfo
	 */
	private JSPayResVO processNormalOrder(JSPayVO vo, OrderVO orderInfo) {
		PlatformMerchantVO merchant = platformMerchantService.getPlatformMerchantById(vo.getMerchantId());
		if (Objects.isNull(merchant)) {
			log.error("查询不到商户|MerchantId:" + vo.getMerchantId());
			throw new QmdException("系统异常");
		}
		
		PayAccountVO accountInfo = payAccountService.getPayAccountByShopId(orderInfo.getShopId());
		if (Objects.isNull(accountInfo)) {
			log.error("查询不到店铺账户|shopId:" + orderInfo.getShopId());
			throw new QmdException("系统异常");
		}
		
		Date now = new Date();
		// 生成支付流水
		TradeDetailVO trade = new TradeDetailVO();
		trade.setMerchantId(merchant.getId());
		//trade.setAccountOutId(ParentDataEnum.RootId.getCode());
		trade.setAccountInId(accountInfo.getId());
		trade.setSerialNo(orderInfo.getOrderId());
		trade.setAmount(orderInfo.getNeedPayAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		trade.setTradeType(TradeTypeEnums.TRANSFERIN.getCode());
		trade.setProductName(orderInfo.getTitle());
		trade.setThirdTradeName(vo.getPayType().getName());
		trade.setPayChannel(vo.getPayType().getCode().toString());
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.CREATE.getCode());
		trade.setCreateDate(now);
		trade.setType(TradeDetailTypeEnum.SHOPORDER.getCode());
		TradeDetailVO tradeVO = tradeDetailService.addTradeDetail(trade);
		
		// 设置请求参数
		JSPayReq req = new JSPayReq();
		req.setMerchantNo(merchant.getMerchantNo());
		req.setTerminalId(merchant.getTerminalNo());
		req.setTerminalTrace(orderInfo.getOrderId());
		req.setAccessToken(merchant.getAccessToken());
		req.setTotalFee(orderInfo.getNeedPayAmount().setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		req.setNotifyUrl(saobeiConfig.getJsPayCallbackUrl());
		req.setOrderBody(orderInfo.getTitle());
		req.setPayType(vo.getPayType().getCode());
		switch (vo.getPayType()) {
		case AliPay:
			req.setOpenId(vo.getOpenId());
			break;
		case WeChatPay:
			req.setSubAppid(vo.getAppId());
			req.setOpenId(vo.getOpenId());
			break;
		default:
			break;
		}
		req.setAttach(new AttachHelper(orderInfo.getOrderId(), null, trade.getId()).toJson());
		req.setTradeId(trade.getId());
		
		// 调用扫呗接口发送预付款请求
		JSPayResVO result = doJsPay(req);
		if (Objects.nonNull(result) && result.isSuccess()) {
			// 保存扫呗返回利楚订单唯一编号
			tradeVO.setOutTradeNo(result.getOutTradeNo());
			tradeDetailService.updateTradeDetail(tradeVO);
			// 修改订单状态
			orderService.setOrderStatus(orderInfo.getOrderId(), OrderStatusEnum.Paying.getCode());
			result.setTradeId(tradeVO.getId());
		}
		return result;
	}

	
	/**
	 * 处理普通订单信息
	 * @param vo
	 * @param orderInfo
	 */
	@Transactional(rollbackFor = {Exception.class , QmdException.class})
	private JSPayResVO processGameOrder(JSPayVO vo, OrderVO orderInfo) {
		PlatformMerchantVO merchant = platformMerchantService.getPlatformMerchantById(vo.getMerchantId());
		if (Objects.isNull(merchant)) {
			log.error("查询不到商户|MerchantId:" + vo.getMerchantId());
			throw new QmdException("系统异常");
		}
		
		PayAccountVO accountInfo = payAccountService.getPayAccountByShopId(orderInfo.getShopId());
		if (Objects.isNull(accountInfo)) {
			log.error("查询不到店铺账户|shopId:" + orderInfo.getShopId());
			throw new QmdException("系统异常");
		}
		
		GameExtendVO gameOrder = gameOrderService.getGameOrderByGameId(vo.getGameOrderId());
		if (Objects.isNull(gameOrder)) {
			log.error("查询不到游戏订单信息|gameOrderId:" + vo.getGameOrderId());
			throw new QmdException("系统异常");
		}
		// 生成支付流水
		Date now = new Date();
		TradeDetailVO trade = new TradeDetailVO();
		trade.setMerchantId(merchant.getId());
		//trade.setAccountOutId(ParentDataEnum.RootId.getCode());
		trade.setAccountInId(accountInfo.getId());
		trade.setSerialNo(orderInfo.getOrderId());
		trade.setGameSerialNo(gameOrder.getId());
		// 游戏需要支付的金额 向下取整
		trade.setAmount(gameOrder.getNeedPayAmount().setScale(0, BigDecimal.ROUND_HALF_UP));
		trade.setTradeType(TradeTypeEnums.TRANSFERIN.getCode());
		trade.setProductName(orderInfo.getTitle());
		trade.setThirdTradeName(vo.getPayType().getName());
		trade.setPayChannel(vo.getPayType().getCode().toString());
		trade.setCurrency("RMB");
		trade.setStatus(TradePayStatusEnum.CREATE.getCode());
		trade.setCreateDate(now);
		trade.setType(TradeDetailTypeEnum.SHOPGAMEORDER.getCode());
		TradeDetailVO tradeVO = tradeDetailService.addTradeDetail(trade);
		
		
		JSPayReq req = new JSPayReq();
		req.setMerchantNo(merchant.getMerchantNo());
		req.setTerminalId(merchant.getTerminalNo());
		req.setAccessToken(merchant.getAccessToken());
		req.setTerminalTrace(orderInfo.getOrderId());
		// 游戏订单应付金额
		req.setTotalFee(gameOrder.getNeedPayAmount().setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		req.setSubAppid(vo.getAppId());
		req.setNotifyUrl(saobeiConfig.getJsPayGameCallbackUrl());
		req.setOrderBody(orderInfo.getTitle());
		req.setPayType(vo.getPayType().getCode());
		switch (vo.getPayType()) {
		case AliPay:
			req.setOpenId(vo.getOpenId());
			break;
		case WeChatPay:
			req.setSubAppid(vo.getAppId());
			req.setOpenId(vo.getOpenId());
			break;
		default:
			break;
		}
		req.setAttach(new AttachHelper(orderInfo.getOrderId(), gameOrder.getId(), trade.getId()).toJson());
		req.setTradeId(trade.getId());
	
		// 调用扫呗接口发送预付款请求
		JSPayResVO result = doJsPay(req);
		if (Objects.nonNull(result) && result.isSuccess()) {
			// 保存扫呗返回利楚订单唯一编号
			tradeVO.setOutTradeNo(result.getOutTradeNo());
			tradeDetailService.updateTradeDetail(tradeVO);
			
			//  修改订单 和 游戏订单状态
			orderInfo.setOrderStatus(OrderStatusEnum.GamePayed.getCode());
			gameOrder.setOrderStatus(OrderStatusEnum.Paying.getCode());
			orderService.updateOrderInfoAndGameOrderInfo(orderInfo, gameOrder);
			result.setTradeId(tradeVO.getId());
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class , QmdException.class})
	public JSRefundRes jsRefund(JSRefundVO vo) {
		AssertUtil.isNull(vo, "SaoBeiPayServiceImpl|jsRefund|传入参数vo为空");
		//AssertUtil.isNull(vo.getTradeId(), "SaoBeiPayServiceImpl|jsRefund|传入参数TradeId为空");
		//AssertUtil.isNull(vo.getRefundMoney(), "SaoBeiPayServiceImpl|jsRefund|传入参数refundMoney为空");
		
		OrderReturnVO refund = orderReturnService.getOrderReturnInfoById(vo.getRefundId());
		if (Objects.isNull(refund)) {
			log.error("退款申请, 退款申请记录不存在refundId:" + vo.getRefundId());
			throw new QmdException("退款申请, 退款申请记录不存在");
		}
		
		TradeDetailVO trade = tradeDetailService.getPayedOrderTradeDetail(refund.getOrderId());
		if (Objects.isNull(trade)) {
			log.error("退款申请失败, 订单流水不存在 orderId:" + refund.getOrderId());
			throw new QmdException("退款申请失败, 订单流水不存在");
		}
		
		// 验证流水状态
		if (!TradePayStatusEnum.PAYED.getCode().equals(trade.getStatus())) {
			log.error("退款申请失败, 订单未支付或处于其他状态 不能申请退款 tradeId:" + trade.getId() + "|status:" + trade.getStatus());
			throw new QmdException("退款申请失败, 订单未支付或处于其他状态 不能申请退款");
		}
		
		// 验证退款申请金额<=支付金额
		if (refund.getApplyReturnAmount().compareTo(trade.getAmount()) == 1) {
			log.error("退款申请失败, 申请退款金额大于支付金额 tradeId:" + trade.getId() + "|refundMoney:" + refund.getApplyReturnAmount() + "|amount:" + trade.getAmount());
			throw new QmdException("退款申请失败, 申请退款金额大于支付金额");
		}
		
		OrderVO order = orderService.getOrderById(trade.getSerialNo());
		if (Objects.isNull(order)) {
			log.error("退款申请失败, 订单不存在 orderId:" + trade.getSerialNo());
			throw new QmdException("退款申请失败, 订单不存在");
		}
		
		// 验证订单状态
		if (!OrderStatusEnum.RefundApply.getCode().equals(order.getOrderStatus())) {
			log.error("退款申请, 订单状态异常 不处于退款申请中 OrderStatus:" + order.getOrderStatus());
			throw new QmdException("退款申请失败, 订单状态异常");
		}
		
		PlatformMerchantVO merchant = platformMerchantService.getPlatformMerchantById(trade.getMerchantId());
		if (Objects.isNull(merchant)) {
			log.error("退款申请失败, 商户信息不存在 merchatId:" + trade.getMerchantId());
			throw new QmdException("退款申请失败, 商户信息不存在");
		}
		
		// 验证账户
		PayAccountVO shopAccount = payAccountService.getPayAccountByShopId(refund.getShopId());
		if (Objects.isNull(shopAccount)) {
			log.error("退款申请失败, 商家账户信息不存在shopId:" + refund.getShopId());
			throw new QmdException("退款申请失败, 商家账户信息不存在");
		}
		
		// 验证商户余额
		if (OrderStatusEnum.TradeComplete.getCode().equals(order.getOrderStatus())) {
			if (shopAccount.getAccountBalance().compareTo(refund.getApplyReturnAmount()) == -1) {
				log.error("退款申请, 商家余额不足 settBalance:" + shopAccount.getSettBalance());
				throw new QmdException("退款申请失败, 商家余额不足");
			}
		}
		
		JSRefundReq req = new JSRefundReq();
		req.setPayType(trade.getPayChannel());
		req.setMerchantNo(merchant.getMerchantNo());
		req.setTerminalId(merchant.getTerminalNo());
		req.setTerminalTrace(vo.getRefundId().toString());
		req.setRefundFee(refund.getApplyReturnAmount().setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		req.setOutTradeNo(trade.getOutTradeNo());
		req.setAccessToken(merchant.getAccessToken());
		
		JSRefundRes res = doJSRefund(req);
		if (res.isSuccess()) {
			// 处理账户信息
			BigDecimal decreaseValue = new BigDecimal(res.getRefundFee());
			// 验证申请金额 和 实际退款金额是否相等
			if (refund.getApplyReturnAmount().compareTo(decreaseValue) != 0) {
				log.info("扫呗实际退款金额和客户申请退款金额不一致|applyRefundMoney:" + refund.getApplyReturnAmount() + "|refundFee:" + decreaseValue);
				throw new QmdException("扫呗实际退款金额和客户申请退款金额不一致");
			}
			Date now = new Date();
			// 是否是核销过的
			final boolean isVT = TradeDetailTypeEnum.SHOPORDERVT.getCode().equals(trade.getType())  ? true : false;
			TradeDataVO data = new TradeDataVO();
			data.setRefundId(vo.getRefundId());
			data.setProcessType(TradePayStatusEnum.REFUND);
			data.setChannelTradeNo(trade.getChannelTradeNo());
			data.setDecreaseValue(decreaseValue);
			data.setOrderId(order.getOrderId());
			data.setOutTradeNo(res.getOutTradeNo());
			data.setOutRefundNo(res.getOutRefundNo());
			data.setShopAccountId(trade.getAccountInId());
			data.setShopId(order.getShopId());
			data.setTradeId(trade.getId());
			data.setOperateDate(now);
			data.setRefundEndDate(res.getRefundEndTime());
			data.setIsVT(isVT);
			data.setPayChannal(trade.getPayChannel());
			try {
				if (isVT) {
					// payAccountService.calcOrderValidationTicketRefundProfitAndDecreaseBalance(trade.getAccountInId(), refund.getShopId(), order.getOrderId(), null, res.getOutTradeNo(), res.getOutRefundNo(), decreaseValue, trade.getPayChannel(), now);
					payAccountService.calcOrderValidationTicketRefundProfitAndDecreaseBalance(data);
				} else {
					// payAccountService.calcOrderPayedRefundProfitAndDecreaseBalance(trade.getAccountInId(), refund.getShopId(), order.getOrderId(), null, res.getOutTradeNo(), res.getOutRefundNo(), decreaseValue, trade.getPayChannel(), now);
					payAccountService.calcOrderPayedRefundProfitAndDecreaseBalance(data);
				}
				log.info("SaoBeiPayServiceImpl|jsRefund|分润出账完毕tradeId:" + trade.getId());
			} catch (QmdException e) {
				AccountExceptionTaskVO task = new AccountExceptionTaskVO();
				task.setTradeData(data);
				accountProcess.add(task);
				throw e;
			}
			
			// 处理订单 和 退款申请
			RefundResultVO refundResult = new RefundResultVO();
			refundResult.setApplyId(vo.getRefundId());
			refundResult.setFlag(true);
			refundResult.setOrderId(order.getOrderId());
			refundResult.setOutRefundNo(res.getOutRefundNo());
			refundResult.setOutTrandNo(res.getOutTradeNo());
			refundResult.setRefundDate(res.getRefundEndTime());
			refundResult.setRefundFee(decreaseValue);
			orderReturnService.updateOrderAndRefundInfo(refundResult);
		}
		return res;
	}
	
	
	/**
	 * 发送退款申请
	 * @param req
	 * @return
	 */
	private JSRefundRes doJSRefund(JSRefundReq req) {
		Map<String, String> condition = req.getRequestParameter();
		log.info(new StringBuilder("SaoBeiPayServiceImpl|").append("doJSRefund|").append("request:").append(req.toJson()).toString());
		String resultStr = httpRequestService.doPostForJson(saobeiConfig.getJsRefundUrl(), condition);
		log.info(new StringBuilder("SaoBeiPayServiceImpl|").append("doJSRefund|").append("response:").append(resultStr).toString());
		JSRefundRes result = null;
		if (StringUtils.isNotBlank(resultStr)) {
			result = GsonHelper.fromJson(resultStr, JSRefundRes.class);
		} else {
			log.error("请求扫呗退款申请 返回空字符串");
			throw new QmdException("系统异常");
		}
		return result;
	}
	
}
