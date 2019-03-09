package cn.qumiandan.orderinvoice.api;

import cn.qumiandan.orderinvoice.vo.OrderInvoiceVO;

/**
 * 发票信息管理接口
 * 
 * @author lrj
 *
 */
public interface IOrderInvoiceService {

	/**
	 * 
	 * 添加发票
	 * 
	 * @param orderInvoiceVO
	 * @return
	 */
	int addOrderInvoice(OrderInvoiceVO orderInvoiceVO);

	/**
	 * 
	 * 修改发票
	 * 
	 * @param orderInvoiceVO
	 * @return
	 */
	int updateOrderInvoiceByOrderId(OrderInvoiceVO orderInvoiceVO);

	/**
	 * 
	 * 删除发票
	 * 
	 * @param orderId
	 * @return
	 */
	int deleteOrderInvoice(String orderId);

	/**
	 * 根据订单号查询发票详情
	 * 
	 * @param orderId
	 * @return
	 */
	OrderInvoiceVO getOrderInvoiceOrderId(String orderId);
}
