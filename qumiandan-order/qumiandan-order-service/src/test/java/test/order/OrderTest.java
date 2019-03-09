package test.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.order.api.IOrderService;
import cn.qumiandan.order.entity.Order;
import cn.qumiandan.order.mapper.OrderMapper;
import cn.qumiandan.order.vo.CreateOrderResponseParamsVO;
import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderProductVO;
import cn.qumiandan.order.vo.OrderQueryParamsVO;
import cn.qumiandan.order.vo.OrderQueryVO;
import cn.qumiandan.order.vo.OrderStatusListVO;
import cn.qumiandan.order.vo.OrderUpdateStatusVO;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

public class OrderTest extends BaseTest {
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private OrderMapper mapper;
    
    @Test
    public void testWapper(){
    	mapper.selectList(new QueryWrapper<Order>()
    	.and(func -> func.eq("", "")));
    }
    @Test
    public void testAddOrder(){
        System.out.println("---orderService.addOrder()---");
        OrderAddVO orderAddVO = new OrderAddVO();
        orderAddVO.setShopId(10L);
        orderAddVO.setUserId(1L);
        orderAddVO.setOrderType(ToolUtil.intToByte(1));
        orderAddVO.setBilling(ToolUtil.intToByte(2));
        orderAddVO.setProCode("520000");
        orderAddVO.setCityCode("520100");
        orderAddVO.setCountyCode("520102");
        orderAddVO.setCreateId(111L);
        orderAddVO.setCreateDate(new Date());
        orderAddVO.setShopName("去免单6号店铺");
        orderAddVO.setCouponId(16L);
        List<OrderProductVO> orderProductList = new ArrayList<>();
        OrderProductVO e = new OrderProductVO();
		e.setProductId(60L);
		e.setProductName("测试商品名11");
		e.setProductPrice(new BigDecimal("1000.0"));
		e.setProductMarque("小份");
		e.setProductModeDesc("小份");
		e.setProductImgUrl("http://phr2czqqv.bkt.clouddn.com/test1126-2");
		//e.setDiscountRate(new BigDecimal("0.5"));
		//e.setDiscountAmount(new BigDecimal("5.0"));
		e.setNumber(3);
		e.setRemark("急用！！！");
		orderProductList.add(e );
		orderAddVO.setOrderProductList(orderProductList );
	    //System.out.println(JSON.toJSONString(orderAddVO));
		CreateOrderResponseParamsVO createOrder = orderService.createOrder(orderAddVO);
		System.out.println(createOrder);
    }

    @Test
    public void testUpdateOrderStatusById(){
        System.out.println("---orderService.addOrder()---");
        OrderUpdateStatusVO orderUpdateStatusVO = new OrderUpdateStatusVO();
        orderUpdateStatusVO.setOrderId("201812043550645650980864");
        orderUpdateStatusVO.setOrderStatus(ToolUtil.intToByte(2));
        orderUpdateStatusVO.setUpdateId(1L);
        OrderVO orderVO = orderService.updateOrderStatusById(orderUpdateStatusVO);
        System.out.println(orderVO);
    }

    @Test
    public void testGetOrderDetailById(){
        System.out.println("---orderService.addOrder()---");
        OrderQueryVO orderDetailById = orderService.getOrderDetailById("201901081009132943446114304");
        System.out.println(orderDetailById);
    }
    
//    public static void main(String[] args) {
//    	OrderAddVO orderAddVO = new OrderAddVO();
//        orderAddVO.setShopId(10L);
//        orderAddVO.setUserId(2L);
//        orderAddVO.setOrderType(ToolUtil.intToByte(1));
//        orderAddVO.setBilling(ToolUtil.intToByte(2));
//        orderAddVO.setProCode("520000");
//        orderAddVO.setCityCode("520100");
//        orderAddVO.setCountyCode("520102");
//        orderAddVO.setCreateId(111L);
//        orderAddVO.setCreateDate(new Date());
//        orderAddVO.setShopName("去免单1号店铺");
//        orderAddVO.setCouponId(1L);
//        List<OrderProductVO> orderProductList = new ArrayList<>();
//        OrderProductVO e = new OrderProductVO();
//		e.setProductId(36L);
//		e.setProductName("测试商品名11");
//		e.setProductPrice(new BigDecimal("1.0"));
//		e.setProductMarque("小份");
//		e.setProductModeDesc("小份");
//		e.setProductImgUrl("http://phr2czqqv.bkt.clouddn.com/test1126-2");
//		e.setDiscountRate(new BigDecimal("0.5"));
//		e.setDiscountAmount(new BigDecimal("5.0"));
//		e.setNumber(3);
//		e.setRemark("急用！！！");
//		orderProductList.add(e );
//		orderAddVO.setOrderProductList(orderProductList );
//	   // System.out.println(JSON.toJSONString(orderAddVO));
//	}
    

    @Test
    public void getOrderByOrderQueryVO(){
        System.out.println("---orderService.getOrderByUserId()---");
        OrderStatusListVO orderQueryVO = new OrderStatusListVO();
        orderQueryVO.setUserId(1L);
        PageInfo<OrderQueryVO> info = orderService.getOrderByorderStatusList(orderQueryVO,1,10);
        System.out.println(info);
    }
    

    @Test
    public void getOrderDetailById(){
        System.out.println("---orderService.getOrderDetailById()---");
        OrderQueryVO info = orderService.getOrderDetailById("201901081009495331974414336");
        System.out.println(info);
    }
    
    
    @Test
    public void getOrderByShopIdListVO(){
        System.out.println("---orderService.getOrderByShopIdListVO()---");
        OrderQueryParamsVO idListVO = new OrderQueryParamsVO();
//        idListVO.setOrderTypeList(Lists.newArrayList(new Byte("1")));
//        idListVO.setWinList(Lists.newArrayList(new Byte("1")));
//        idListVO.setStatusList(Lists.newArrayList(new Byte("4")));;
//        idListVO.setShopIdList(Lists.newArrayList(10L));
//        idListVO.setStartDate(DateUtil.getDateByStr("2018-12-17 17:01:17"));
//        idListVO.setUserName("13087804777");
        PageInfo<OrderQueryVO> info = orderService.getOrderByShopIdList(idListVO,1,30);
        System.out.println(1);
        System.out.println(info);
    }
    
    @Test
    public void setOrderStatus(){
        System.out.println("---orderService.getOrderDetailById()---");
        int i = orderService.setOrderStatus("201812103494086778552320",ToolUtil.intToByte(3));
        System.out.println(i);
    }
    
    
    @Test
    public void estimateUserHasOrder(){
        System.out.println("---orderService.estimateUserHasOrder()---");
        boolean flag = orderService.estimateUserHasOrder(1L);
        System.out.println(flag);
    }
    
    @Test
    public void  getOrderByValTicketCode(){
    	OrderQueryVO orderByValTicketCode = orderService.getOrderByValTicketCode("3cde7af75ed0977ca40dc336f3c19325");
    	System.out.println(orderByValTicketCode);
    }
}
