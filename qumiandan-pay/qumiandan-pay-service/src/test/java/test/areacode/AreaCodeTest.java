package test.areacode;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.areacode.api.IAreaCodeService;
import test.BaseTest;

public class AreaCodeTest extends BaseTest{

	@Autowired
	private IAreaCodeService areaCodeService;
	
	/*@Test
	public void getSbAreaCodeByAddressCode() {
		System.out.println("-----getSbAreaCodeByAddressCode---");
		String code = areaCodeService.getSbAreaCodeByAddressCode(110000, 1);
		System.out.println(code);
	}*/
	
	@Test
	public void getSaobeiAreaInfoByCountryCode() {
		System.out.println("-----getSaobeiAreaInfoByCountryCode---");
		// 520102
		areaCodeService.getSaobeiAreaInfoByCountryCode("520102");
	}
}
