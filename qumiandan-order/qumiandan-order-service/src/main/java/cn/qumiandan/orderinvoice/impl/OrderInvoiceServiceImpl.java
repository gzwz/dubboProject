package cn.qumiandan.orderinvoice.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.orderinvoice.api.IOrderInvoiceService;
import cn.qumiandan.orderinvoice.entity.OrderInvoice;
import cn.qumiandan.orderinvoice.mapper.OrderInvoiceMapper;
import cn.qumiandan.orderinvoice.vo.OrderInvoiceVO;
import cn.qumiandan.utils.CopyBeanUtil;
/**
 * 发票管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IOrderInvoiceService.class)
public class OrderInvoiceServiceImpl implements IOrderInvoiceService
{
	@Autowired
	private OrderInvoiceMapper  orderInvoiceMapper;
	/**
	 * 
	 * 添加发票
	 * @param orderInvoiceVO
	 * @return
	 */
	@Override
	public int addOrderInvoice(OrderInvoiceVO orderInvoiceVO) throws QmdException{
		OrderInvoice orderInvoice = orderInvoiceMapper.getOrderInvoiceOrderId(orderInvoiceVO.getOrderId());
		if(orderInvoice != null) {
			throw new QmdException("该订单发票已存在");
		}
		orderInvoice = CopyBeanUtil.copyBean(orderInvoiceVO, OrderInvoice.class);
		orderInvoice.setCreatedDate(new Date());
		return orderInvoiceMapper.insertSelective(orderInvoice);
	}

	/**
	 * 
	 * 修改发票
	 * @param orderInvoiceVO
	 * @return
	 */
	@Override
	public int updateOrderInvoiceByOrderId(OrderInvoiceVO orderInvoiceVO) {
		OrderInvoice orderInvoice = null;
		if(orderInvoiceVO != null) {
			orderInvoice = CopyBeanUtil.copyBean(orderInvoiceVO, OrderInvoice.class);
		}
		return orderInvoiceMapper.updateByPrimaryKeySelective(orderInvoice);
	}

	/**
	 * 
	 * 删除发票
	 * @param orderId
	 * @return
	 */
	@Override
	public int deleteOrderInvoice(String orderId) throws QmdException {
		OrderInvoice orderInvoice = orderInvoiceMapper.getOrderInvoiceOrderId(orderId);
		if(orderInvoice==null) {
			throw new QmdException("该订单发票不存在");
		}
		orderInvoice.setStatus(StatusEnum.deleted.getCode());
		return orderInvoiceMapper.updateByPrimaryKeySelective(orderInvoice);
		
	}

	/**
	 * 根据订单号查询发票详情
	 * @param orderId
	 * @return
	 */
	@Override
	public OrderInvoiceVO getOrderInvoiceOrderId(String orderId) {
		OrderInvoiceVO orderInvoiceVO = null;
		OrderInvoice orderInvoice = orderInvoiceMapper.getOrderInvoiceOrderId(orderId);
		if(orderInvoice!=null) {
			orderInvoiceVO = CopyBeanUtil.copyBean(orderInvoice, OrderInvoiceVO.class);
		}
		return orderInvoiceVO!=null?orderInvoiceVO:null;
	}

}
