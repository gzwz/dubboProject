package cn.qumiandan.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.idfactory.api.IdFactory;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.GameStatusEnum;
import cn.qumiandan.order.constant.GameWinEnum;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.entity.GameOrder;
import cn.qumiandan.order.mapper.GameOrderMapper;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.GameOrderDetailVO;
import cn.qumiandan.order.vo.GameOrderVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.shopmember.api.IShopMemberService;
import cn.qumiandan.shopmember.vo.PurchaseShopMemberVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.enums.ValidationTicketStatus;
import cn.qumiandan.validationticket.vo.CreateValidationTicketVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IGameOrderService.class)
public class GameOrderServiceImpl implements IGameOrderService {
	
	@Autowired
	private GameOrderMapper mapper;
	@Reference
	private IdFactory factory;
	@Autowired
	private IOrderService orderService;
	@Reference
	private IShopMemberService shopMemberService;
	
	@Autowired
	private IValidationTicketService validationTicketService;
	
	@Reference
	private ITradeDetailService tradeDetailService;
	
	static Random random = new Random();
	
	@Override
	public BigDecimal getGamePrice(BigDecimal orderNeedPay) {
		AssertUtil.isNull(orderNeedPay, "订单应付金额不能为空");
		if (orderNeedPay.compareTo(BigDecimal.ZERO) <= 0) {
			throw new QmdException("获取游戏单价时，订单应付金额不能小于0");
		}
		// 计算时 都是以分为单位
		// 0 - 50
		/*
		 * if (0 < orderNeedPay.doubleValue() && orderNeedPay.doubleValue() <= 1000) {
		 * return nextPrice(50, 80); } else if(1000 < orderNeedPay.doubleValue() &&
		 * orderNeedPay.doubleValue() <= 3000) { return nextPrice(60, 100); } else
		 * if(3000 < orderNeedPay.doubleValue() && orderNeedPay.doubleValue() <= 5000) {
		 * return nextPrice(70, 120); } else if(5000 < orderNeedPay.doubleValue() &&
		 * orderNeedPay.doubleValue() <= 10000) { return nextPrice(80, 130); }else
		 * if(10000 < orderNeedPay.doubleValue() && orderNeedPay.doubleValue() <= 15000)
		 * { return nextPrice(90, 140); }else if(20000 < orderNeedPay.doubleValue() &&
		 * orderNeedPay.doubleValue() <= 25000) { return nextPrice(150, 250); }else
		 * if(25000 < orderNeedPay.doubleValue() && orderNeedPay.doubleValue() <= 30000)
		 * { return nextPrice(200, 280); }else if(30000 < orderNeedPay.doubleValue() &&
		 * orderNeedPay.doubleValue() <= 35000) { return nextPrice(250, 300); }else
		 * if(35000 < orderNeedPay.doubleValue() && orderNeedPay.doubleValue() <= 40000)
		 * { return nextPrice(280, 400); }else if(40000 < orderNeedPay.doubleValue() &&
		 * orderNeedPay.doubleValue() <= 50000) { return nextPrice(350, 500); }else
		 * if(50000 < orderNeedPay.doubleValue()) { return nextPrice(500,
		 * orderNeedPay.intValue()); }else { throw new QmdException("价格有误"); }
		 */
		// 100一下都是1元   ，一百以上百分之一金额
		BigDecimal gamePrice = new BigDecimal(1);
		if (0 < orderNeedPay.doubleValue() && orderNeedPay.doubleValue() <= 10000) {
			gamePrice = new BigDecimal(100);
		} else {
			gamePrice = orderNeedPay.divide(new BigDecimal(100));
		} 
		// TODO 去掉除以 100
		// gamePrice = gamePrice.divide(new BigDecimal(100));
		return gamePrice;
		
	}
	
	/**
	 * 返回区间金额 分为单位
	 * @param max 最大金额
	 * @param min 最小金额
	 * @return
	 *//*
	private BigDecimal nextPrice(int min,int max) {
		double f = Math.floor(Math.random() * (max - min + 1) + min);
		int i = ((int) (f/10))*10;
        BigDecimal b =  new BigDecimal(i);
        b = b.divide(new BigDecimal(10));
		return b;
		*
		 * NumberFormat ddf1 = NumberFormat.getNumberInstance() ;
		 * ddf1.setMaximumFractionDigits(2); boolean flag = true; float f = 0 ; while
		 * (flag) { f = random.nextInt(max)+random.nextFloat(); if (min <= f && f < max)
		 * { flag = false; break; } }
		 *
	}*/

	/**
	 * * 1.外部订单号 取后面10位长度，如果首位为0,则填充为1 当做随机种子数
	 * 2.将随机种子除以当前订单应付金额（分为单位）取得余数的整数（取模）
	 * 3.将余数与当前游戏实付价格（单位：分）进行比较，如果小于等于该价格，则中奖
	 * 4.将中奖信息更新到数据库
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void startShakeGame(GameExtendVO gameOrder) {
		OrderVO order = orderService.getOrderById(gameOrder.getOrderId());
		// 判断当前游戏是否已经玩过，通过是否已开奖字段判断
		GameOrder game = mapper.selectById(gameOrder.getId());
		if (GameWinEnum.Open.getCode().equals(game.getOpenLottery())) {
			log.info("该游戏已经开奖，无需再次调用！！！");
			return;
		}
		/**TradeDetailVO detailVO = tradeDetailService.getTradeDetailByOrderIdAndType(game.getOrderId(), TradeDetailTypeEnum.SHOPGAMEORDER);
		if (detailVO == null) {
			throw new QmdException("游戏订单支付流水不存在！");
		}*/
		// 开始计算游戏是否中奖
		boolean isWin = calculateIsWin(gameOrder.getChannelTradeNo(),gameOrder.getAmountTotal(), order.getNeedPayAmount());
		if (isWin) {
			gameOrder.setWin(GameWinEnum.Win.getCode());
			order.setWin(GameWinEnum.Win.getCode());
			order.setOrderStatus(OrderStatusEnum.Paid.getCode());
			// 如果中奖则创建 核销券
			CreateValidationTicketVO ticket = new CreateValidationTicketVO();
			ticket.setOrderId(order.getOrderId());
			ticket.setShopId(order.getShopId());
			ticket.setUserId(order.getUserId());
			Date date = new Date();
			ticket.setCreateDate(date);
			ticket.setUpdateDate(date);
			ticket.setStatus(ValidationTicketStatus.UnUse.getCode());
			validationTicketService.addValidationTicket(ticket);
			
		}else {
			order.setWin(GameWinEnum.NotWin.getCode());
			gameOrder.setWin(GameWinEnum.NotWin.getCode());
		}
		gameOrder.setOpenLottery(GameWinEnum.Open.getCode());
		// 如果更新游戏信息
		updateGameOrderById(gameOrder);
		
		order.setOrderType(GameWinEnum.Used.getCode());
		// 更新订单信息
		orderService.updateOrder(order);
		
	}
	
	/* 中奖几率 -> 游戏金额是0.5元（分为单位）
	 *   /\ 
	 *  /  \
	 * /    \
	 * |____|______________________________|
	 * 0    50                            1000
	 * 如果中奖返回true,未中奖返回false
	 */
	private boolean calculateIsWin(String outTradeNo, BigDecimal gamePrice,BigDecimal orderPrice) {
		boolean flag = false;
		Double seed = getSeed(outTradeNo);
		double result = seed % orderPrice.doubleValue();
		log.info("开奖结果是："+result);
		// 中奖 中奖信息更新到数据库
		if (gamePrice.doubleValue() >= result) {
			flag = true;
		}
		return flag;
	}
	
	private Double getSeed(String outTradeNo) {
		int length = outTradeNo.length();
		outTradeNo = outTradeNo.substring(length - 10, length);
		if (outTradeNo.charAt(0) == '0') {
			outTradeNo = outTradeNo.replaceFirst("0", "1");
		};
		return Double.valueOf(outTradeNo);
	}
	
	@Override
	public GameOrderDetailVO getOrderByGameId(String gameId) {
		AssertUtil.isNull(gameId, "游戏订单id不能为空");
		GameExtendVO game = getGameOrderByGameId(gameId);
		if (null == game) {
			throw new QmdException("该游戏订单不存在！");
		}
		OrderVO order = orderService.getOrderById(game.getOrderId());
		if (order == null) {
			throw new QmdException("该游戏对应的主订单不存在！");
		}
		// 根据当前订单号查询游戏就来 返回的是list
		List<GameOrder> selectList = mapper.selectList(new QueryWrapper<GameOrder>().eq("order_id", order.getOrderId()));
		List<GameExtendVO> list = CopyBeanUtil.copyList(selectList, GameExtendVO.class);
		GameOrderDetailVO orderDetailVO = CopyBeanUtil.copyBean(order, GameOrderDetailVO.class);
		orderDetailVO.setGameOrderInfo(list);
		return orderDetailVO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public GameOrderVO addGameOrder(String orderId, BigDecimal price, Integer times) {
		AssertUtil.isNull(orderId, "orderId 不能为空");
		AssertUtil.isNull(price, "游戏单价price不能为空");
		AssertUtil.isNull(times, "游戏倍数不能为空");
		OrderVO orderVO = orderService.getOrderById(orderId);
		if (null == orderVO) {
			throw new QmdException("该游戏订单对应的订单不存在");
		}
		// 订单只能是未支付的情况下才能玩游戏
		if (orderVO.getWin() == GameWinEnum.Win.getCode()) {
			throw new QmdException("该游戏订单已中奖！不能再次玩游戏！");
		}
		if (orderVO.getOrderStatus() == OrderStatusEnum.Paid.getCode()) {
			throw new QmdException("该游戏订单已经付款！不能再次玩游戏！");
		}
		if (orderVO.getOrderStatus() == OrderStatusEnum.RefundApply.getCode()
				||orderVO.getOrderStatus() == OrderStatusEnum.Refunded.getCode()
				||orderVO.getOrderStatus() == OrderStatusEnum.RefuseRefund.getCode()
			) {
			throw new QmdException("该游戏订单正在申请退款中，不能再次玩游戏！");
		}
		if (orderVO.getOrderStatus() != OrderStatusEnum.NotPay.getCode()
				&& orderVO.getOrderStatus() != OrderStatusEnum.GamePayed.getCode()) {
			throw new QmdException("该游戏订单已经付款！不能再次玩游戏！");
		}
		// 未限制创建游戏订单,数量
		GameOrder entity = new GameOrder();
		entity.setId(factory.getId());
		entity.setCreateDate(new Date());
		// 获取游戏单价
		// 游戏单价乘以倍数
		price = price.multiply(new BigDecimal(times));
		entity.setNeedPayAmount(price);
		
		entity.setOrderId(orderId);
		entity.setTimes(times);
		entity.setWin(GameWinEnum.NotWin.getCode());
		entity.setOrderStatus(GameStatusEnum.NotPay.getCode());
		int insert = mapper.insert(entity);
		if (insert != 1) {
			throw new QmdException("创建游戏订单出错");
		}
		GameOrderVO gameVO = CopyBeanUtil.copyBean(entity, GameOrderVO.class);
		
		PurchaseShopMemberVO purchaseShopMember = new PurchaseShopMemberVO();
		// 根据购买金额设置会员天数，例如，1.5元，则增加会员天数为1.5天
		purchaseShopMember.setDays(price.divide(new BigDecimal(100)).doubleValue());
		purchaseShopMember.setShopId(orderVO.getShopId());
		purchaseShopMember.setUserId(orderVO.getUserId());
		shopMemberService.purchaseShopMember(purchaseShopMember);
		return gameVO;
	}

	@Override
	public GameExtendVO getGameOrderByGameId(String gameId) {
		GameOrder gameOrder = mapper.selectOne(new QueryWrapper<GameOrder>()
				.eq("id", gameId));
		if (gameOrder == null) {
			return null;
		}
		GameExtendVO extendVO = CopyBeanUtil.copyBean(gameOrder, GameExtendVO.class);
		return extendVO;
	}

	/**
	 * 修改游戏订单状态
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer setGameOrderStatus(String gameId, Byte status) {
		GameOrder gameOrder = mapper.selectById(gameId);
		if(gameOrder == null) {
			throw new QmdException("该订单不存在");
		}
		gameOrder.setOrderStatus(status);
		int i = mapper.updateById(gameOrder);
		if(i!=1) {
			throw new QmdException("更新订单状态失败");
		}
		return i;
	}

	/**
	 * 更新游戏订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateGameOrderById(GameExtendVO gameExtendVO) {
		GameOrder gameOrder = mapper.selectById(gameExtendVO.getId());
		if(gameOrder == null) {
			throw new QmdException("该订单不存在");
		}
		gameOrder = CopyBeanUtil.copyBean(gameExtendVO, GameOrder.class);
		int i = mapper.updateById(gameOrder);
		if(i!=1) {
			throw new QmdException("更新订单状态失败");
		}
		return i;
	}

	/**
	 * 根据订单id查询游戏订单集合
	 */
	@Override
	public List<GameExtendVO> getGameExtendByOrderId(String orderId) {
		List<GameOrder> gameOrders = mapper.selectList(new QueryWrapper<GameOrder>().eq("order_id", orderId).orderByDesc("create_date"));
		if(ObjectUtils.isEmpty(gameOrders)) {
			return null;
		}
		return CopyBeanUtil.copyList(gameOrders, GameExtendVO.class);
	}


}
