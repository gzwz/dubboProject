package test.shopmember;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.shopmember.api.IVipDiscountInfoService;
import cn.qumiandan.shopmember.vo.VipDiscountInfoVO;
import test.BaseTest;

public class VipDiscountInfoTest extends BaseTest{
	@Autowired
	private IVipDiscountInfoService discountInfoService ;
	
	@Test
	public void addVipDiscountInfo() {
		VipDiscountInfoVO discountInfoVO = new VipDiscountInfoVO();
		discountInfoVO.setShopId(1L);
		discountInfoVO.setDiscountMoney(new BigDecimal(1));
		discountInfoVO.setType(new Byte("1"));
		discountInfoVO = discountInfoService.addVipDiscountInfo(discountInfoVO);
		System.out.println(discountInfoVO);
	}
	
	
	
	@Test
	public void setVipDiscountInfoStatus() {
		VipDiscountInfoVO discountInfoVO = new VipDiscountInfoVO();
		discountInfoVO = discountInfoService.setVipDiscountInfoStatus(1L,new Byte("0"));
		System.out.println(discountInfoVO);
	}
	
	
	
	@Test
	public void getVipDiscountInfoByShopId() {
		System.out.println("--getVipDiscountInfoByShopId--");
		VipDiscountInfoVO discountInfoVO = new VipDiscountInfoVO();
		discountInfoVO = discountInfoService.getVipDiscountInfoByShopId(1L);
		System.out.println(discountInfoVO);
	}
}
