package cn.qumiandan.orderreturnserviceimpl.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.GameWinEnum;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.orderreturn.api.IOrderReturnService;
import cn.qumiandan.orderreturn.enums.RefundStatusEnum;
import cn.qumiandan.orderreturn.vo.OrderReturnVO;
import cn.qumiandan.orderreturn.vo.RefundResultVO;
import cn.qumiandan.orderreturnserviceimpl.entity.OrderReturn;
import cn.qumiandan.orderreturnserviceimpl.mapper.OrderReturnMapper;
import cn.qumiandan.pay.saobei.service.ISaoBeiPayService;
import cn.qumiandan.pay.saobei.vo.JSRefundVO;
import cn.qumiandan.pay.saobei.vo.response.pay.JSRefundRes;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.trade.api.ITradeDetailService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Service(interfaceClass=IOrderReturnService.class)
public class OrderReturnServiceImpl implements IOrderReturnService {
	@Autowired
	private OrderReturnMapper orderReturnMapper;
	@Autowired
	private IOrderService orderService;
	@Reference
	private IPayAccountService payAccountService; 
	@Reference
	private ISaoBeiPayService saoBeiPayService;
	@Reference
	private ITradeDetailService tradeDetailService;
	
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public OrderReturnVO applyReturn(OrderReturnVO orderReturnVO) {
		OrderVO orderVO = orderService.getOrderById(orderReturnVO.getOrderId());
		if (orderVO == null) {
			throw new QmdException("订单不存在");
		}

		if (orderReturnVO.getApplyReturnAmount().doubleValue() > orderVO.getAmountTotal().doubleValue()) {
			throw new QmdException("退款金额不能大于付款金额");
		}

		if (!orderVO.getOrderStatus().equals(OrderStatusEnum.Paid.getCode())
				&& !orderVO.getOrderStatus().equals(OrderStatusEnum.TradeComplete.getCode())
				&& !orderVO.getOrderStatus().equals(OrderStatusEnum.RefuseRefund.getCode())) {
			throw new QmdException("该状态下的订单不能退款");
		}
		if (orderVO.getWin() != null && GameWinEnum.Win.getCode().equals(orderVO.getWin())) {
			throw new QmdException("该状态下的订单不能退款");
		}
		
		OrderReturnVO orderReturnVO2 = selectByOrderId(orderVO.getOrderId());
		if (null != orderReturnVO2) {
			throw new QmdException("改订单已在申请退款中，请重复申请！");
		}
		
		OrderReturn orderReturn = CopyBeanUtil.copyBean(orderReturnVO, OrderReturn.class);
		orderReturn.setApplyDate(new Date());
		orderReturn.setAuditlStatus(StatusEnum.normal.getCode());
		orderReturn.setShopId(orderVO.getShopId());
		orderReturn.setParentId(0L);
		int i = orderReturnMapper.insert(orderReturn);
		OrderVO orderById = new OrderVO();
		orderById.setOrderId(orderReturnVO.getOrderId());
		orderById.setOrderStatus(OrderStatusEnum.RefundApply.getCode());
		int j = orderService.updateOrder(orderById);
		if (i + j != 2) {
			throw new QmdException("申请退款失败");
		}
		orderReturnVO.setId(orderReturn.getId());
		return orderReturnVO;
	}

	@Override
	@Transactional(rollbackFor = QmdException.class)
	public OrderReturnVO auditPass(Long id) {
		OrderReturn orderReturn = orderReturnMapper.selectById(id);
		if(orderReturn == null) {
			throw new QmdException("该订单没有申请退款记录");

		}
		PayAccountVO accountInfo = payAccountService.getPayAccountByShopId(orderReturn.getShopId());
		if(accountInfo == null) {
			throw new QmdException("账户不存在");
		}
		
		/*if(orderReturn.getApplyReturnAmount().doubleValue()>accountInfo.getAccountBalance().doubleValue()) {
			throw new QmdException("账户余额不足");
		}*/
		
		JSRefundVO jsRefundVO = new JSRefundVO();
		jsRefundVO.setRefundId(orderReturn.getId());
		JSRefundRes jsRefund = saoBeiPayService.jsRefund(jsRefundVO);
		if(!jsRefund.isSuccess()) {
			log.error("auditPass|退款失败,orderId:" + orderReturn.getOrderId()+",原因："+jsRefund.getReturnMsg());
			throw new QmdException("退款失败");

		}
		OrderReturnVO orderReturnVO = CopyBeanUtil.copyBean(orderReturn, OrderReturnVO.class);
		return orderReturnVO;
	}
	
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public OrderReturnVO auditUnPass(OrderReturnVO orderReturnVO) {
		OrderReturn orderReturn = orderReturnMapper.selectById(orderReturnVO.getId());
		if(orderReturn == null) {
			log.error("该订单没有申请退款记录,orderReturnVO.getId():" + orderReturnVO.getId());
			throw new QmdException("该订单没有申请退款记录");
		}
		//修改退款订单表订单状态
		orderReturnVO.setAuditDate(new Date());
		orderReturnVO.setAuditlStatus(RefundStatusEnum.UNPASS.getCode());
		OrderReturn bean = CopyBeanUtil.copyBean(orderReturnVO, OrderReturn.class);
		int orderReturnRet = orderReturnMapper.updateById(bean);
		//修改主订单状态
		OrderVO order = new OrderVO();
		order.setOrderId(orderReturn.getOrderId());
		order.setOrderStatus(OrderStatusEnum.RefuseRefund.getCode());
		order.setUpdateDate(new Date());
		int orderRet = orderService.updateOrder(order);
		if(orderReturnRet != 1 && orderRet !=1) {
			log.error("拒绝退款失败,orderId:" + orderReturn.getId());
			throw new QmdException("拒绝退款失败");
		}
		return orderReturnVO;
	}

	
	

	@Override
	public OrderReturnVO getOrderReturnInfoById(Long id) {
		AssertUtil.isNull(id, "OrderReturnServiceImpl|getOrderReturnInfoById|传入参数id为空");
		OrderReturn refund = orderReturnMapper.selectById(id);
		if (Objects.nonNull(refund)) {
			return CopyBeanUtil.copyBean(refund, OrderReturnVO.class);
		}
		return null;
	}
	

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void updateOrderReturnInfo(OrderReturnVO vo) {
		AssertUtil.isNull(vo, "OrderReturnServiceImpl|updateOrderReturnInfo|传入参数vo为空");
		AssertUtil.isNull(vo.getId(), "OrderReturnServiceImpl|updateOrderReturnInfo|传入参数Id为空");
		OrderReturn refund = CopyBeanUtil.copyBean(vo, OrderReturn.class);
		if (!checkCUD(orderReturnMapper.updateById(refund))) {
			log.error("更新退款信息失败|id:" + vo.getId());
			throw new QmdException("更新退款信息失败");
		}
	}
	
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void updateOrderAndRefundInfo(RefundResultVO vo) {
		AssertUtil.isNull(vo, "OrderReturnServiceImpl|updateOrderAndRefundInfo|传入参数vo为空");
		OrderReturnVO refund = getOrderReturnInfoById(vo.getApplyId());
		if (Objects.isNull(refund)) {
			throw new QmdException("申请退款记录不存在");
		}
		
		OrderVO order = orderService.getOrderById(vo.getOrderId());
		if (Objects.isNull(order)) {
			throw new QmdException("订单信息不存在");
		}
		
		// 退款成功
		if (vo.getFlag()) {
			refund.setAuditlStatus(RefundStatusEnum.FINISH.getCode());
			refund.setOutTrandNo(vo.getOutTrandNo());
			refund.setOutRefundNo(vo.getOutRefundNo());
			refund.setRefundFee(vo.getRefundFee());
			refund.setRefundDate(vo.getRefundDate());
			updateOrderReturnInfo(refund);
			
			// 修改订单状态
			order.setOrderStatus(OrderStatusEnum.Refunded.getCode());
			orderService.updateOrder(order);
		} else {
			// 目前退款失败 不会回调此接口
		}
	}

	// 根据订单号查询退款流程。只查询最新的一条申请记录
	@Override
	public OrderReturnVO selectByOrderId(String orderId) {
		QueryWrapper<OrderReturn> queryWrapper=new QueryWrapper<OrderReturn>();
		queryWrapper.eq("order_id", orderId);
		queryWrapper.orderByDesc("apply_date");
		//queryWrapper.eq("auditl_status", RefundStatusEnum.CREATE.getCode());
		List<OrderReturn> list = orderReturnMapper.selectList(queryWrapper);
		if (list == null || list.size() == 0 ) {
			return null;
		}
		OrderReturn orderReturn = list.get(0);
		//OrderReturn orderReturn = orderReturnMapper.selectOne(queryWrapper);
		OrderReturnVO bean = CopyBeanUtil.copyBean(orderReturn, OrderReturnVO.class);
		return bean;
	}

}
