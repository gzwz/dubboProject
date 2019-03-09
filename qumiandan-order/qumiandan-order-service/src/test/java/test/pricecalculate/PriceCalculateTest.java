package test.pricecalculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderProductVO;
import cn.qumiandan.pricecalculate.api.IOrderPriceCalculateService;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

public class PriceCalculateTest extends BaseTest{

	@Resource
    private IOrderPriceCalculateService calculateService;
	
	@Test
    public void orderCalculateByCoupon(){
		OrderAddVO orderAddVO = new OrderAddVO();
        orderAddVO.setShopId(10L);
        orderAddVO.setUserId(2L);
        orderAddVO.setOrderType(ToolUtil.intToByte(1));
        orderAddVO.setBilling(ToolUtil.intToByte(2));
        orderAddVO.setProCode("520000");
        orderAddVO.setCityCode("520100");
        orderAddVO.setCountyCode("520102");
        orderAddVO.setCreateId(111L);
        orderAddVO.setCreateDate(new Date());
        orderAddVO.setShopName("去免单1号店铺");
//        orderAddVO.setCouponId(1L);
        List<OrderProductVO> orderProductList = new ArrayList<>();
        OrderProductVO e = new OrderProductVO();
		e.setProductId(60L);
		e.setProductName("测试商品名11");
		e.setProductPrice(new BigDecimal("1.0"));
		e.setProductMarque("小份");
		e.setProductModeDesc("小份");
		e.setProductImgUrl("http://phr2czqqv.bkt.clouddn.com/test1126-2");
		e.setDiscountRate(new BigDecimal("0.5"));
		e.setDiscountAmount(new BigDecimal("5.0"));
		e.setNumber(3);
		e.setRemark("急用！！！");
		orderProductList.add(e );
		orderAddVO.setOrderProductList(orderProductList );
		BigDecimal bigDecimal = calculateService.orderCalculateByCoupon(orderAddVO);
		System.out.println(bigDecimal);
	}
}
