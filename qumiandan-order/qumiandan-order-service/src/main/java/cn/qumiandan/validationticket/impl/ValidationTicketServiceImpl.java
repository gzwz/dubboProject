package cn.qumiandan.validationticket.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.constant.GameWinEnum;
import cn.qumiandan.order.constant.OrderStatusEnum;
import cn.qumiandan.order.vo.OrderUpdateStatusVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.api.process.IAccountProcess;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.payaccount.vo.TradeDataVO;
import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.trade.enums.TradePayStatusEnum;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.DateUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.utils.ValidationTicketUtils;
import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.entity.ValidationTicket;
import cn.qumiandan.validationticket.enums.ValidationTicketStatus;
import cn.qumiandan.validationticket.mapper.ValidationTicketMapper;
import cn.qumiandan.validationticket.vo.CreateValidationTicketVO;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 核销券接口实现类
 * @author: WLZ
 * @date: 2018/12/26 14:24
 */
@Slf4j
@Component
@Service(interfaceClass = IValidationTicketService.class)
public class ValidationTicketServiceImpl implements IValidationTicketService {

    @Autowired
    private ValidationTicketMapper validationTicketMapper;
    
    @Autowired
    private IOrderService orderService;
    
    @Reference
    private ISysPropertiesService sysPropertiesService;
    
    @Reference
    private IPayAccountService accountService;
    
    @Reference
    private IShopUserService shopUserService;
    
    @Reference
	private IAccountProcess accountProcess;
    
    @Override
    @Transactional(rollbackFor = {Exception.class, QmdException.class})
    public CreateValidationTicketVO addValidationTicket(CreateValidationTicketVO ticket){
    	AssertUtil.isNull(ticket, "ValidationTicketServiceImpl|addValidationTicket|传入");
    	ValidationTicket validationTicket = validationTicketMapper.selectOne(new QueryWrapper<ValidationTicket>().eq("order_id", ticket.getOrderId()));
    	if(validationTicket != null) {
    		throw new QmdException("该订单的核销券已存在");
    	}
    	validationTicket = CopyBeanUtil.copyBean(ticket,ValidationTicket.class);
        //核销码  （用户名+订单号+店铺编号）md5
        String code = ValidationTicketUtils.getValidationTicket(validationTicket.getUserId(), validationTicket.getShopId(), validationTicket.getOrderId());
        validationTicket.setTicketCode(code);
        //核销券到期时间
        SysPropertiesVO sysPropertiesVO = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.VALIDATIONTICKETENDTIME.getId());
        if(sysPropertiesVO != null){
            Date addDay = DateUtil.addDay(new Date(), Integer.parseInt(sysPropertiesVO.getValue()));
            validationTicket.setEndDate(addDay);
        }
        if (!checkCUD(validationTicketMapper.insert(validationTicket))) {
        	throw new QmdException("创建核销卷失败");
        } 
        ticket.setId(validationTicket.getId());
        return ticket;
    }
	@Override
	public ValidationTicketVO getValTicketById(Long ticketId) {
		AssertUtil.isNull(ticketId, "id不能为空");
		ValidationTicket ticket = validationTicketMapper.selectById(ticketId);
		if (ObjectUtils.isEmpty(ticket)) {
			return null;
		}
		ValidationTicketVO target = CopyBeanUtil.copyBean(ticket, ValidationTicketVO.class);
		return target;
	}

	@Override
	public PageInfo<ValidationTicketVO> getValTicketsForUserId(Long userId,int pageNum,int pageSize) {
		AssertUtil.isNull(userId, "用户id不能为空");
		PageHelper.startPage(pageNum, pageSize);
		List<ValidationTicket> list = validationTicketMapper.selectList(new QueryWrapper<ValidationTicket>()
				.eq("user_id", userId));
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		List<ValidationTicketVO> couponTakeRecordVOs = CopyBeanUtil.copyList(list, ValidationTicketVO.class);
		PageInfo<ValidationTicketVO> pageInfo = new PageInfo<>(couponTakeRecordVOs);
		return pageInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ValidationTicketVO useValTicketsForMerchentUserIdAndTicketCode(Long userId,String ticketCode) {
		AssertUtil.isNull(ticketCode, "核销券不能为空");
		AssertUtil.isNull(userId, "当前用户不能为空");
		
		// 订单状态必须为【已支付】才能进行核销
		ValidationTicketVO ticket = getUnuseValidationTicketByTicketCode(ticketCode);
		if (ObjectUtils.isEmpty(ticket)) {
			throw new QmdException("该核销券不存在或者已经过期");
		}
		
		// 查询该登陆用户的id所管理的店铺id
		List<Long> shopidList = shopUserService.getShopIdListByUserId(userId);
		// 不包含次管理店铺 直接抛出异常
		if (!shopidList.contains(ticket.getShopId())) {
			throw new QmdException("该用户不是此核销券所在商家的管理人员，无法核销！");
		}
		
		OrderVO orderVO = orderService.getOrderById(ticket.getOrderId());
		if (orderVO == null) {
			throw new QmdException("此核销券对应的订单不存在");
		}
		
		if (orderVO.getOrderStatus() != OrderStatusEnum.Paid.getCode()) {
			throw new QmdException("此核销券对应的订单状态不能核销");
		}
		
		Date date = new Date();
		int i = validationTicketMapper.update(new ValidationTicket(), new UpdateWrapper<ValidationTicket>()
				.eq("ticket_code", ticketCode)
				.eq("status", ValidationTicketStatus.UnUse.getCode())
				.set("status", ValidationTicketStatus.Used.getCode())
				.set("update_date", date));
		if (i != 1) {
			throw new QmdException("核销失败！");
		}
		
		// 更改订单状态为消费完毕
		OrderUpdateStatusVO orderUpdateStatusVO = new OrderUpdateStatusVO();
		orderUpdateStatusVO.setOrderId(ticket.getOrderId());
		orderUpdateStatusVO.setOrderStatus(OrderStatusEnum.TradeComplete.getCode());
		orderVO = orderService.updateOrderStatusById(orderUpdateStatusVO );
		PayAccountVO account = accountService.getPayAccountByShopId(orderVO.getShopId());
		if (account == null) {
			throw new QmdException("商家商户异常，请及时联系系统服务商！");
		}
		//为中奖，且 订单实付 > 0 才能调用账户余额增加
		if (GameWinEnum.NotWin.getCode().equals(orderVO.getWin())
				&& orderVO.getNeedPayAmount().doubleValue() > 0) {
			// 核销后增加对应个账户的余额
			// 商家-省代理-市代理-业务员
			TradeDataVO data = new TradeDataVO();
			data.setProcessType(TradePayStatusEnum.PAYED);
			data.setOrderId(orderVO.getOrderId());
			data.setShopId(orderVO.getShopId());
			data.setTicketCode(ticketCode);
			data.setOperateDate(date);
			data.setUserId(userId);
			data.setIsVT(true);
			//try {
				// accountService.calcValidationTicketProfitIncreaseAccountBalance(orderVO.getShopId(), orderVO.getOrderId(), date);
				accountService.calcValidationTicketProfitIncreaseAccountBalance(data);
			/*} catch (QmdException e) {
				AccountExceptionTaskVO task = new AccountExceptionTaskVO();
				TradeDataVO data = new TradeDataVO();
				data.setProcessType(TradePayStatusEnum.PAYED);
				data.setOrderId(orderVO.getOrderId());
				data.setShopId(orderVO.getShopId());
				data.setTicketCode(ticketCode);
				data.setOperateDate(date);
				data.setUserId(userId);
				data.setIsVT(true);
				task.setTradeData(data);
				accountProcess.add(task);
				throw e;
			}*/
		}else {
			log.error("为中奖，且 订单实付 > 0 才能调用账户余额增加");
		}
		ValidationTicketVO ticketVO = CopyBeanUtil.copyBean(ticket, ValidationTicketVO.class);
		return ticketVO;
	}
	
	@Override
	public ValidationTicketVO getUnuseValidationTicketByTicketCode(String ticketCode) {
		ValidationTicket ticket = validationTicketMapper.selectOne(new QueryWrapper<ValidationTicket>()
				.eq("ticket_code", ticketCode)
				.eq("status", ValidationTicketStatus.UnUse.getCode()));
		if (Objects.nonNull(ticket)) {
			return CopyBeanUtil.copyBean(ticket, ValidationTicketVO.class);
		}
		return null;
	}
    
	
	@Override
	public ValidationTicketVO getValTicketByOrderId(String orderId) {
		AssertUtil.isNull(orderId, "订单不能为空");
		ValidationTicket ticket = validationTicketMapper.selectOne(new QueryWrapper<ValidationTicket>()
				.eq("order_id", orderId));
		ValidationTicketVO ticketVO = CopyBeanUtil.copyBean(ticket, ValidationTicketVO.class);
		return ticketVO;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public int createValidationTicketAndUpdateOrderStatus(OrderVO order) {
		AssertUtil.isNull(order, "ValidationTicketServiceImpl|createValidationTicketAndUpdateOrderStatus|传入参数order为空");
		CreateValidationTicketVO ticket = new CreateValidationTicketVO();
		ticket.setOrderId(order.getOrderId());
		ticket.setShopId(order.getShopId());
		ticket.setUserId(order.getUserId());
		Date date = new Date();
		ticket.setCreateDate(date);
		ticket.setUpdateDate(date);
		ticket.setStatus(ValidationTicketStatus.UnUse.getCode());
		addValidationTicket(ticket);
		orderService.updateOrder(order);
		return 1;
	}
	@Override
	public ValidationTicketVO getValTicketByTicketCode(String ticketCode) {
		AssertUtil.isNull(ticketCode, "传入参数ticketCode为空");
		ValidationTicket ticket = validationTicketMapper.selectOne(new QueryWrapper<ValidationTicket>()
				.eq("ticket_code", ticketCode)
				);
		if (ticket == null) {
			return null;
		}
		ValidationTicketVO ticketVO = CopyBeanUtil.copyBean(ticket, ValidationTicketVO.class);
		return ticketVO;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void updateValidationTicket(ValidationTicketVO vo) {
		AssertUtil.isNull(vo, "ValidationTicketServiceImpl|updateValidationTicket|传入参数vo为空");
		ValidationTicket info = CopyBeanUtil.copyBean(vo, ValidationTicket.class);
		if (!checkCUD(validationTicketMapper.updateById(info))) {
			log.error("更新核销卷信息失败|id:" + vo.getId());
			throw new QmdException("更新核销卷信息失败");
		}
	}
	
	
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void updateValidationTicketAndOrderInfo(ValidationTicketVO ticket, OrderVO order) {
		updateValidationTicket(ticket);
		orderService.updateOrder(order);
	}
	
	/*
	 * private String getTicketCodeByUUId(){ String str =
	 * String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())); int
	 * length = str.length(); if (length >= 12) { str =
	 * str.substring(length-12,length); return str; }else { str =
	 * String.format("%012d",str); return str; } }
	 */
    
}
