package test.orderinvoice;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.orderinvoice.api.IOrderInvoiceService;
import cn.qumiandan.orderinvoice.vo.OrderInvoiceVO;
import test.BaseTest;

public class OrderInvoiceTest extends BaseTest{
	@Autowired
	public IOrderInvoiceService orderInvoiceService;
	
	@Test
	public void addOrderInvoice() {
		System.out.println("------addOrderInvoice------");
		OrderInvoiceVO orderInvoiceVO = new OrderInvoiceVO();
		orderInvoiceVO.setOrderId("ifqojpoaso");
		int i = orderInvoiceService.addOrderInvoice(orderInvoiceVO);
		System.out.println(i);
		
	}
	
	@Test
	public void updateOrderInvoiceByOrderId() {
		System.out.println("------updateOrderInvoiceByOrderId------");
		OrderInvoiceVO orderInvoiceVO = new OrderInvoiceVO();
		orderInvoiceVO.setOrderId("ifqojpoaso");
		int i = orderInvoiceService.updateOrderInvoiceByOrderId(orderInvoiceVO);
		System.out.println(i);
		
	}
	
	@Test
	public void deleteOrderInvoice() {
		System.out.println("------deleteOrderInvoice------");;
		int i = orderInvoiceService.deleteOrderInvoice("ifqojpoaso");
		System.out.println(i);
		
	}
	
	@Test
	public void getOrderInvoiceOrderId() {
		System.out.println("------getOrderInvoiceOrderId------");;
		OrderInvoiceVO orderInvoiceVO = orderInvoiceService.getOrderInvoiceOrderId("1");
		System.out.println(orderInvoiceVO);
		
	}
}
