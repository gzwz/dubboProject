package cn.qumiandan.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.coupon.api.ICouponService;
import cn.qumiandan.coupon.api.ICouponUseRecordService;
import cn.qumiandan.coupon.vo.CouponUseRecordVO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.idfactory.api.IdFactory;
import cn.qumiandan.order.api.IGameOrderService;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.GameWinEnum;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.entity.Order;
import cn.qumiandan.order.entity.OrderDetail;
import cn.qumiandan.order.mapper.OrderDetailMapper;
import cn.qumiandan.order.mapper.OrderMapper;
import cn.qumiandan.order.vo.CreateOrderResponseParamsVO;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderDetailVO;
import cn.qumiandan.order.vo.OrderProductVO;
import cn.qumiandan.order.vo.OrderQueryParamsVO;
import cn.qumiandan.order.vo.OrderQueryVO;
import cn.qumiandan.order.vo.OrderStatusListVO;
import cn.qumiandan.order.vo.OrderUpdateStatusVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.pricecalculate.api.IOrderPriceCalculateService;
import cn.qumiandan.product.api.IProductService;
import cn.qumiandan.product.vo.ProductDetailVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicInfoVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 订单接口实现类
 * @author: WLZ
 * @date: 2018/12/4 11:37
 */
@Slf4j
@Component
@Service(interfaceClass = IOrderService.class)
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private IGameOrderService gameOrderService;
	@Autowired
	private IOrderPriceCalculateService orderPriceCalculate;
	@Reference
	private IdFactory idFactory;
	@Reference
	private IProductService productService;
	@Reference
	private IShopService shopService;
	@Reference
	private IUserService userService;
	@Autowired
	private ICouponService couponService;
	@Autowired
	private IValidationTicketService validationTicketService;
	@Autowired
	private ICouponUseRecordService couponUseRecordService;
	
	
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public CreateOrderResponseParamsVO createOrder(OrderAddVO orderAddVO) {
		
		// 订单基础信息
		Date date = new Date();
		Order order = CopyBeanUtil.copyBean(orderAddVO, Order.class);
		
		// 订单编号
		order.setOrderType(GameWinEnum.NotUse.getCode());
		order.setOrderId(idFactory.getId());
		order.setOrderSettlementTime(date);
		order.setCreateDate(date);
		order.setUpdateDate(date);
		order.setUpdateId(orderAddVO.getCreateId());
		// 订单状态：OrderStatusEnum
		order.setOrderStatus(OrderStatusEnum.NotPay.getCode());
		// 店铺基本信息
		ShopBasicInfoVO shopBasicInfoVO = shopService.getShopBasicInfoByShopId(orderAddVO.getShopId());
		if (shopBasicInfoVO != null) {
			// 业务员id
			order.setSalesmanId(shopBasicInfoVO.getCreateId());
			// 添加省市县信息
			order.setProCode(shopBasicInfoVO.getProCode());
			order.setCityCode(shopBasicInfoVO.getCityCode());
			order.setCountyCode(shopBasicInfoVO.getCityCode());
			order.setShopName(shopBasicInfoVO.getName());
		}
		// 计算应付金额
		BigDecimal needPayPrice = orderPriceCalculate.orderCalculateByCoupon(orderAddVO);
		// 设置会员折扣率
		order.setDiscountRate(orderAddVO.getDiscountRate());
		order.setProductAmountTotal(orderAddVO.getProductAmountTotal());
		// 订单金额(实际付款金额)
		// 如果优惠券金额大于或者等于付款，订单直接设置为已付款
		if (needPayPrice.longValue() < 0) {
			//order.setAmountTotal(new BigDecimal(0));
			order.setNeedPayAmount(needPayPrice);
			order.setOrderStatus(OrderStatusEnum.Paid.getCode());
			log.info("该笔订单实际支付为 0 元，计算后的价格：" + needPayPrice);
		} else {
			//order.setAmountTotal(needPayPrice);
			order.setNeedPayAmount(needPayPrice);
			order.setOrderStatus(OrderStatusEnum.NotPay.getCode());
			log.info("该笔订单实际支付 计算后的价格：" + needPayPrice);
		}
		
		// 将领取的优惠券优惠券设置为已经使用
		// order.getOrderId(),orderAddVO.getCouponId(), orderAddVO.getUserId()
		if (orderAddVO.getCouponId() != null) {
			CouponUseRecordVO recordVO = new CouponUseRecordVO();
			CouponVO couponVO = couponService.queryCouponById(orderAddVO.getCouponId());
			recordVO.setCouponUseRule(couponVO.getUseRule());
			recordVO.setCouponId(orderAddVO.getCouponId());
			recordVO.setOrderId(order.getOrderId());
			recordVO.setUserId(order.getUserId());
			couponService.useCouponRecord(recordVO);
		}
		// 保存订单
		int orderRet = orderMapper.insert(order);
				
		int orderDetailRet = 0;
		if (orderAddVO.getOrderProductList() != null && orderAddVO.getOrderProductList().size() > 0) {
			List<OrderProductVO> orderProductList = orderAddVO.getOrderProductList();
			// ? 只取第一条商品判断？
			Set <Long> proIds = orderProductList.stream().filter(op -> Objects.nonNull(op)).map(op -> op.getProductId()).collect(Collectors.toSet());
			List<ProductDetailVO> productSet = productService.getProductByProductIdSet(proIds);
			if (productSet == null || productSet.size() == 0) {
				throw new QmdException("商品不存在");
			}
			String productName = productSet.get(0).getName();
			// 订单名称 最长20位
			String title = productName.length() > 20 ? productName.substring(0, 20) : productName;
			order.setTitle(title);
			// 保存商品订单详情
			orderDetailRet = addOrderDetail(order.getOrderId(), orderAddVO.getShopId(), orderAddVO.getShopName(),
					orderAddVO.getOrderProductList());
		}
		if (orderRet > 0 && orderDetailRet > 0) {
			//设置返回参数
			CreateOrderResponseParamsVO responseParamsVO = new CreateOrderResponseParamsVO();
			responseParamsVO.setOrderId(order.getOrderId());
			responseParamsVO.setNeedPayAmount(order.getNeedPayAmount());
			responseParamsVO.setShopName(order.getShopName());
			responseParamsVO.setShopLogo(shopBasicInfoVO.getLogo());
			return responseParamsVO;
		} else {
			throw new QmdException("添加失败");
		}
	}

	/**
	 * 保存商品订单详情
	 * 
	 * @return
	 */
	public int addOrderDetail(String orderId, Long shopId, String shopName, List<OrderProductVO> orderProductList) {
		int insertRet = 0;
		if (orderProductList.size() > 0) {
			for (OrderProductVO orderProduct : orderProductList) {
				OrderDetail orderDetail = CopyBeanUtil.copyBean(orderProduct, OrderDetail.class);
				orderDetail.setOrderId(orderId);
				// 商品信息
				ProductDetailVO productBasicVO = productService.getProductDetailById(orderDetail.getProductId());
				if (productBasicVO != null) {
					if(productBasicVO.getAttribute()!=null) {
						orderDetail.setAttribute(productBasicVO.getAttribute());
					}
					BigDecimal number = new BigDecimal(orderDetail.getNumber());
					BigDecimal subTotal = productBasicVO.getPresentPrice().multiply(number);
					// 商品订单小计金额
					orderDetail.setSubtotal(subTotal);
				}
				// orderDetail.setDiscountRate();//折扣比例，暂时保留
				// orderDetail.setDiscountAmount();//折扣金额，暂时保留
				insertRet += orderDetailMapper.insert(orderDetail); 
			}
		}
		return insertRet;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public OrderVO updateOrderStatusById(OrderUpdateStatusVO orderUpdateStatusVO) {
		Order order = orderMapper.selectByPrimaryKey(orderUpdateStatusVO.getOrderId());
		if (order == null) {
			throw new QmdException("订单不存在");
		}
		// 订单状态保留到sql最后设置
		//order.setOrderStatus(orderUpdateStatusVO.getOrderStatus());
		order.setUpdateDate(new Date());
		order.setUpdateId(orderUpdateStatusVO.getUpdateId());
		// 如果是付款
		if (orderUpdateStatusVO.getOrderStatus().equals(OrderStatusEnum.Paid.getCode())) {
			order.setPayDate(new Date()); // 支付时间
			order.setPayChannel(orderUpdateStatusVO.getPayChannel()); // 支付方式
			order.setOutTradeNo(orderUpdateStatusVO.getOutTradeNo()); // 订单支付流水号
		}
		// 更改状态,并且状态必须为上面查出来的状态，否则说明状态已经被别人更改过了
		int updateRet = orderMapper.update(order, new UpdateWrapper<Order>()
				.set("order_status", orderUpdateStatusVO.getOrderStatus())
				.eq("order_status", order.getOrderStatus())
				.eq("order_id", order.getOrderId()));
		if (updateRet != 1) {
			throw new QmdException("更新订单状态失败！");
		}
		OrderVO orderVO = CopyBeanUtil.copyBean(order, OrderVO.class);
		return orderVO;
	}

	/**
	 * 根据订单编号查询订单详情
	 */
	@Override
	public OrderQueryVO getOrderDetailById(String orderId) {
		Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_id", orderId));
		List<OrderDetail> orderDetailList = orderDetailMapper
				.selectList(new QueryWrapper<OrderDetail>().eq("order_id", orderId));
		if (order == null || ObjectUtils.isEmpty(orderDetailList)) {
			return null;
		}
		OrderQueryVO orderQueryVO = CopyBeanUtil.copyBean(order, OrderQueryVO.class);
		List<OrderDetailVO> orderDetailVOList = CopyBeanUtil.copyList(orderDetailList, OrderDetailVO.class);
		// 查询订单商品详情
		//查询商品is_qmd字段
		Set<Long> productIds = new HashSet<>();
		for(OrderDetailVO detail : orderDetailVOList) {
			productIds.add(detail.getProductId());
		}
		if(ObjectUtils.isEmpty(productIds)) {
			return null;
		}
		List<ProductDetailVO> productByProductIdSet = productService.getProductByProductIdSet(productIds);
		if(ObjectUtils.isEmpty(productByProductIdSet)) {
			return null;
		}
		for(ProductDetailVO detailVO: productByProductIdSet) {
			for(OrderDetailVO detail :orderDetailVOList) {
				if(detail.getProductId().equals(detailVO.getId()) && detailVO.getIsQmd() != null) {
					detail.setIsQmd(detailVO.getIsQmd());
				}
			}
		}
		orderQueryVO.setOrderDetailVOList(orderDetailVOList);

		// 查询订单核销券信息
		ValidationTicketVO valTicketByOrderId = validationTicketService.getValTicketByOrderId(orderId);
		if (valTicketByOrderId != null) {
			orderQueryVO.setValidationTicketVO(valTicketByOrderId);
		}
		// 查询订单优惠券信息
		CouponUseRecordVO couponUseRecordByOrderId = couponUseRecordService.getCouponUseRecordByOrderId(orderId);
		if (couponUseRecordByOrderId != null) {
			orderQueryVO.setCouponUseRecordVO(couponUseRecordByOrderId);
		}
		// 查询游戏订单
		// List<GameExtendVO> gameExtendByOrderId =
		// gameOrderService.getGameExtendByOrderId(orderId);
		// if(!ObjectUtils.isEmpty(gameExtendByOrderId)) {
		// orderQueryVO.setGameOrderExtendList(gameExtendByOrderId);
		// }
		return orderQueryVO;
	}

	/**
	 * 根据条件查询订单
	 */
	@Override
	public PageInfo<OrderQueryVO> getOrderByorderStatusList(OrderStatusListVO orderStatusListVO,int pageNum ,int pageSize) {
		QueryWrapper<Order> queryWrapper = setQueryParams(orderStatusListVO);
		queryWrapper.orderByDesc("create_date");
		return getOrderDetail(queryWrapper,pageNum,pageSize);
	}

	/**
	 * 根据订单id集合查询订单详情
	 * 
	 * @return
	 */
	
	private PageInfo<OrderQueryVO> getOrderDetail(QueryWrapper<Order> queryWrapper,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Order> orderList = orderMapper.selectList(queryWrapper);
		long count = PageHelper.count(()->orderMapper.selectList(queryWrapper));
		if (CollectionUtils.isEmpty(orderList)) {
			PageInfo<OrderQueryVO> info = new PageInfo<>();
			info.setTotal(count);
			return info;
		}
		
		List<OrderQueryVO> orderQueryVOList = CopyBeanUtil.copyList(orderList, OrderQueryVO.class);
		// 将订单id放入orderIdList中
		List<String> orderIdList = new ArrayList<String>();
		for (OrderQueryVO orderQueryVO : orderQueryVOList) {
			orderIdList.add(orderQueryVO.getOrderId());
		}
		List<OrderDetail> orderDetailList = orderDetailMapper.selectList(new QueryWrapper<OrderDetail>().in("order_id", orderIdList));
		if (CollectionUtils.isEmpty(orderDetailList)) {
			PageInfo<OrderQueryVO> info = new PageInfo<>();
			info.setTotal(count);
			return info;
		}
		
		List<OrderDetailVO> orderDetailVOList = CopyBeanUtil.copyList(orderDetailList, OrderDetailVO.class);
		for (OrderQueryVO orderQueryVO : orderQueryVOList) {
			List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
			for (OrderDetailVO orderDetailVO : orderDetailVOList) {
				if (orderQueryVO.getOrderId().equals(orderDetailVO.getOrderId())) {
					list.add(orderDetailVO);
				}
			}
			orderQueryVO.setOrderDetailVOList(list);
		}
		PageInfo<OrderQueryVO> info = new PageInfo<>(orderQueryVOList);
		info.setTotal(count);
		return  info;
	}
	
	private QueryWrapper<Order> setQueryParams(OrderStatusListVO orderQueryVO){
		//当前段订单状态未传是，查询状态为1——9的订单
		List<Byte> statusList = new ArrayList<>();
		statusList.add(OrderStatusEnum.NotPay.getCode());
		statusList.add(OrderStatusEnum.Paying.getCode());
		statusList.add(OrderStatusEnum.GamePayed.getCode());
		statusList.add(OrderStatusEnum.Paid.getCode());
		statusList.add(OrderStatusEnum.TradeComplete.getCode());
		statusList.add(OrderStatusEnum.RefundApply.getCode());
		statusList.add(OrderStatusEnum.Refunded.getCode());
		statusList.add(OrderStatusEnum.RefuseRefund.getCode());
		statusList.add(OrderStatusEnum.TradeClose.getCode());
		QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>();
		if(orderQueryVO.getUserId()!=null) {
			queryWrapper.eq("user_id", orderQueryVO.getUserId());
		}
		if(orderQueryVO.getShopId()!=null) {
			queryWrapper.eq("shop_id", orderQueryVO.getShopId());
		}
		if(orderQueryVO.getStatusList()!=null) {
			queryWrapper.in("order_status", orderQueryVO.getStatusList());
		}else {
			queryWrapper.in("order_status", statusList);
		}
		if(orderQueryVO.getOrderId()!=null) {
			queryWrapper.eq("order_id", orderQueryVO.getOrderId());
		}
		if(orderQueryVO.getOrderType()!=null) {
			queryWrapper.eq("order_type", orderQueryVO.getOrderType());
		}
		if(orderQueryVO.getPayChannel()!=null) {
			queryWrapper.eq("pay_channel", orderQueryVO.getPayChannel());
		}
		if(orderQueryVO.getShopName()!=null&&orderQueryVO.getShopName().equals("")) {
			queryWrapper.likeLeft("shop_name", orderQueryVO.getShopName());
		}
		if(orderQueryVO.getWin()!=null) {
			queryWrapper.eq("win", orderQueryVO.getWin());
		}
		return queryWrapper;
	}

    /**
     * 根据店铺id集合查询订单
     * @param shopIdListVO
     * @return
     */
	@Override
	public PageInfo<OrderQueryVO> getOrderByShopIdList(OrderQueryParamsVO shopIdListVO,int pageNum,int pageSize) {
		QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>();
		//设置订单状态
		if(shopIdListVO.getStatusList()!=null) {
			OrderStatusListVO listVO = new OrderStatusListVO();
			listVO.setStatusList(shopIdListVO.getStatusList());
			queryWrapper = setQueryParams(listVO);
		}
		if(shopIdListVO.getShopName()!=null) {
			queryWrapper.likeLeft("shop_name", shopIdListVO.getShopName());
		}
		if(shopIdListVO.getOutTradeNo() != null  && !shopIdListVO.getOutTradeNo().equals("")) {
			queryWrapper.likeLeft("out_trade_no", shopIdListVO.getOutTradeNo());
		}
		if(shopIdListVO.getUserName()!=null && !shopIdListVO.getUserName().equals("")) {
			UserVO userVO = userService.getUserByUsername(shopIdListVO.getUserName());
			if(userVO==null) {
				return null;
			}
			queryWrapper.eq("user_id", userVO.getId());
		}
		if(shopIdListVO.getOrderId()!=null) {
			queryWrapper.eq("order_id", shopIdListVO.getOrderId());
		}
		if(shopIdListVO.getShopIdList()!=null) {
			queryWrapper.in("shop_id", shopIdListVO.getShopIdList());
		}
		if(shopIdListVO.getWinList()!=null) {
			queryWrapper.in("win", shopIdListVO.getWinList());
		}
		if(shopIdListVO.getPayChannelList()!=null) {
			queryWrapper.in("pay_channel", shopIdListVO.getPayChannelList());
		}
		if(shopIdListVO.getOrderTypeList()!=null) {
			queryWrapper.in("order_type", shopIdListVO.getOrderTypeList());
		}
		if(shopIdListVO.getStartDate()!=null) {
			queryWrapper.ge("create_date", shopIdListVO.getStartDate());
		}
		if(shopIdListVO.getEndDate()!=null) {
			queryWrapper.lt("create_date", shopIdListVO.getEndDate());
		}
		queryWrapper.and(func ->func.gt("amount_total", 0).or().gt("game_amount", 0));
		queryWrapper.orderByDesc("create_date");
		return getOrderDetail(queryWrapper,pageNum,pageSize);
	}

	@Override
	public OrderVO getOrderById(String orderId) {
		AssertUtil.isNull(orderId, "OrderServiceImpl|getOrderById|传入参数orderId为空");
		Order order = orderMapper.selectById(orderId);
		if (Objects.nonNull(order)) {
			return CopyBeanUtil.copyBean(order, OrderVO.class);
		}
		return null;
	}

    /**
     * 修改订单状态
     * @param orderId
     * @param status
     * @return
     */
	@Override
	public Integer setOrderStatus(String orderId, Byte status) {
		Order order = orderMapper.selectById(orderId);
		if(order == null) {
			throw new QmdException("该订单不存在");
		}
		order.setOrderStatus(status);
		int i = orderMapper.updateById(order);
		if(i !=1) {
			throw new QmdException("修改失败");
		}
		return i;
	}

	/**
	 * 更新订单
	 */
	@Override
	public Integer updateOrder(OrderVO orderVO) {
		if(orderVO==null || orderVO.getOrderId()==null) {
			throw new QmdException("订单参数错误");
		}
		Order order = CopyBeanUtil.copyBean(orderVO, Order.class);
		int i = orderMapper.updateById(order);
		if(i!=1) {
			throw new QmdException("更新失败");
		}
		return i;
	}

	
    /**
     * 根据用户id判断用户是否是新用户(未下过单及位新用户默认返回true)
     * @param userId
     * @return
     */
	@Override
	public Boolean estimateUserHasOrder(Long userId) {
		//判断用户是否有已支付的订单，订单状态3，4，5，6，7，8，9
		List<Byte> statusList = new ArrayList<>();
		statusList.add(OrderStatusEnum.GamePayed.getCode());
		statusList.add(OrderStatusEnum.Paid.getCode());
		statusList.add(OrderStatusEnum.TradeComplete.getCode());
		statusList.add(OrderStatusEnum.RefundApply.getCode());
		statusList.add(OrderStatusEnum.Refunded.getCode());
		statusList.add(OrderStatusEnum.RefuseRefund.getCode());
		statusList.add(OrderStatusEnum.TradeClose.getCode());
		Integer num = orderMapper.selectCount(new QueryWrapper<Order>().eq("user_id", userId).in("order_status", statusList));
		boolean flag = true;
		if (num != null && num != 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void updateOrderInfoAndGameOrderInfo(OrderVO order, GameExtendVO gameOrder) {
		AssertUtil.isNull(order, "订单参数不能为空");
		AssertUtil.isNull(gameOrder, "游戏参数不能为空");
		gameOrderService.updateGameOrderById(gameOrder);
		// 更新订单信息
		updateOrder(order);
	}

    /**
     * 根据核核销码订单详情
     * @param ticketId
     * @return
     */
	@Override
	public OrderQueryVO getOrderByValTicketCode(String tickeCode) {
		ValidationTicketVO valTicket = validationTicketService.getValTicketByTicketCode(tickeCode);
		if (valTicket == null) {
			log.info("根据核核销码订单详情，核销码不存在");
			return null;
		}
		OrderQueryVO orderVO = getOrderDetailById(valTicket.getOrderId());
		return orderVO;
	}

	/**
	 * 取消支付
	 */
	@Override
	public void cancelPay(String orderId, Long userId) {
		Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_id", orderId).eq("user_id", userId));
		if(order == null) {
			log.info("cancelPay|该订单不存在：orderId：" + orderId + ";userId:" + userId);
			throw new QmdException("该订单不存在");
		}
		if(!OrderStatusEnum.Paying.getCode().equals(order.getOrderStatus())) {
			log.info("cancelPay|该状态下的订单不能取消支付：orderId：" + orderId + ";userId:" + userId);
			throw new QmdException("该状态下的订单不能取消支付");
		}
		order.setOrderStatus(OrderStatusEnum.NotPay.getCode());
		int  i  = orderMapper.updateById(order);
		if(i != 1) {
			log.info("cancelPay|取消支付失败：orderId：" + orderId + ";userId:" + userId);
			throw new QmdException("取消支付失败");
		}
	}
}
