package test.orderunprocessed;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.orderunprocessed.api.IOrderUnprocessedService;
import cn.qumiandan.orderunprocessed.vo.OrderUnprocessedVO;
import test.BaseTest;
/**
 * 未处理成功的订单测试类
 * @author lrj
 *
 */
public class OrderUnprocessedTest extends BaseTest{

	@Autowired
	private IOrderUnprocessedService orderUnprocessedService;
	
	@Test
	public void addOrderUnprocessed() {
		System.out.println("=====addOrderUnprocessed======");
		OrderUnprocessedVO orderUnprocessedVO = new OrderUnprocessedVO();
		orderUnprocessedVO.setGameOrderId("111111111111111");
		orderUnprocessedVO.setOrderId("12222222222222");
		orderUnprocessedVO = orderUnprocessedService.addOrderUnprocessed(orderUnprocessedVO);
		System.out.println(orderUnprocessedVO);
	}
	
	
	@Test
	public void updateOrderUnprocessedById() {
		System.out.println("=====updateOrderUnprocessedById======");
		OrderUnprocessedVO orderUnprocessedVO = new OrderUnprocessedVO();
		orderUnprocessedVO.setId(1L);
		orderUnprocessedVO.setGameOrderId("111111111111111");
		orderUnprocessedVO.setOrderId("12222222222222");
		orderUnprocessedService.updateOrderUnprocessedById(orderUnprocessedVO);
		System.out.println("成功");
	}
	
	@Test
	public void deleteOrderUnprocessedById() {
		System.out.println("=====deleteOrderUnprocessedById======");
		orderUnprocessedService.deleteOrderUnprocessedById(1L);
	}
	
	
	@Test
	public void getOrderUnprocessedById() {
		System.out.println("=====getOrderUnprocessedById======");
		OrderUnprocessedVO orderUnprocessedById = orderUnprocessedService.getOrderUnprocessedById(1L);
		System.out.println(orderUnprocessedById);
	}
	
	
	@Test
	public void queryOrderUnprocessed() {
		System.out.println("=====queryOrderUnprocessed======");
		CommonParams commonParams = new CommonParams();
		PageInfo<OrderUnprocessedVO> queryOrderUnprocessed = orderUnprocessedService.queryOrderUnprocessed(commonParams);
		System.out.println(queryOrderUnprocessed);
	}
}
