package test.address;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.assertj.core.util.Lists;
import org.junit.Test;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.vo.AddressAreaCode;
import cn.qumiandan.address.vo.AddressVO;
import test.BaseTest;
/**
 * @description：省市县单元测试
 * @author：lrj
 * @date: 2018/11/20 14:13
 */
public class AddressTest extends BaseTest{
	@Resource
    private IAddressService reginService;
	
	@Test
	public void querlistaa() {
		//code=330000, level=2, isOpen=1
		reginService.addressOpenOrCloseService(330000, 2, (byte)1);
	}
	@Test
	public void querlist() {
		Set<Integer> codeList = new HashSet<Integer>();
		codeList.add(110101);
		codeList.add(110101001);
		List<AddressVO> byCode = reginService.getAddressListByCode(codeList);
		System.out.println(byCode);
	}
	
	@Test
    public void getProvince(){
        System.out.println("---reginService.getProvince()---");
        System.out.println(reginService.getProvince());
    }
	
//	getCityByProvinceCode
	
	@Test
    public void getCityByProvinceCode(){
        System.out.println("---reginService.getProvince()---");
        System.out.println(reginService.getCityByProvinceCode(120000));
    }
	
	@Test
    public void getDistrictByCityCode(){
        System.out.println("---reginService.getDistrictByCityCode()---");
        System.out.println(reginService.getDistrictByCityCode(130300));
    }
	
	@Test
    public void getTownByDistrictCode(){
        System.out.println("---reginService.getTownByDistrictCode()---");
        System.out.println(reginService.getTownByDistrictCode(130302));
    }

	
	@Test
    public void getAddressByCode(){
        System.out.println("---reginService.getAddressByCode()---");
        System.out.println(reginService.getAddressByCode(110101001,4));
    }
	

	@Test
    public void getAddressByName(){
        System.out.println("---reginService.getNameByCode()---");
        AddressAreaCode addressAreaCode = new AddressAreaCode();
        addressAreaCode.setProvince("重庆市");
        addressAreaCode.setCity("市辖区");
        System.out.println(reginService.getAddressByName(addressAreaCode,2));
    }
	

	@Test
    public void getCurrentCityIsOpen(){
        System.out.println("---reginService.getCurrentCityIsOpen()---");
        System.out.println(reginService.getCurrentCityIsOpen(110100));
    }
	@Test
    public void getOpenCity(){
        System.out.println("---reginService.getOpenCity()---");
        System.out.println(reginService.getOpenCity());
    }
	
	
	@Test
    public void getAddressByDistrictCodeList(){
		List<AddressVO> addressByDistrictCodeList = reginService.getAddressByDistrictCodeList(Lists.newArrayList(520102));
		System.out.println(addressByDistrictCodeList);
	}
}
