package test.deliveryaddress;

import javax.annotation.Resource;

import cn.qumiandan.utils.ToolUtil;
import org.junit.Test;
import cn.qumiandan.deliveryaddress.api.IDeliveryAddressService;
import cn.qumiandan.deliveryaddress.vo.DeliveryAddressVO;
import test.BaseTest;

/**
 * 收获地址测试类
 * @author lrj
 *
 */
public class DeliveryAddressTest extends BaseTest{
	@Resource
    private IDeliveryAddressService deliveryAddressService;

    @Test
    public void addDeliveryAddress() {
    	System.out.println("----addDeliveryAddress------");
    	DeliveryAddressVO deliveryAddressVO = new DeliveryAddressVO();
    	deliveryAddressVO.setUserId(1L);
    	deliveryAddressVO.setRealName("李四");
    	deliveryAddressVO.setMobile("18888888888");
    	deliveryAddressVO.setCountry("中国");
    	deliveryAddressVO.setProvince("贵州");
    	deliveryAddressVO.setCity("贵阳");
    	deliveryAddressVO.setArea("南明区");
    	deliveryAddressVO.setIsDefaultAddress(ToolUtil.intToByte(1));
    	System.out.println(deliveryAddressService.addDeliveryAddress(deliveryAddressVO));

    }
    
    @Test
    public void updateDeliveryAddress() {
    	System.out.println("----updateDeliveryAddress------");
    	DeliveryAddressVO deliveryAddressVO = new DeliveryAddressVO();
    	deliveryAddressVO.setId(1L);
    	deliveryAddressVO.setUserId(1L);
    	deliveryAddressVO.setRealName("张三");
    	deliveryAddressVO.setMobile("18888888888");
    	deliveryAddressVO.setCountry("中国");
    	deliveryAddressVO.setProvince("贵州");
    	deliveryAddressVO.setCity("贵阳");
    	deliveryAddressVO.setArea("南明区");
    	deliveryAddressVO.setIsDefaultAddress(ToolUtil.intToByte(1));
    	System.out.println(deliveryAddressService.updateDeliveryAddress(deliveryAddressVO));
    }
    
    @Test
    public void deleteDeliveryAddress() {
    	System.out.println("----deleteDeliveryAddress------");
    	System.out.println(deliveryAddressService.deleteDeliveryAddress(1L));
    }
    

    @Test
    public void getDeliveryAddressById() {
    	System.out.println("----getDeliveryAddressById------");
    	System.out.println(deliveryAddressService.getDeliveryAddressById(1L));
    }
    
    
    
    @Test
    public void getDeliveryAddressByUserId() {
    	System.out.println("----getDeliveryAddressByUserId------");
    	System.out.println(deliveryAddressService.getDeliveryAddressByUserId(1L));
    }
}
