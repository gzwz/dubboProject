package test.shopmember;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.shopmember.api.IShopMemberService;
import cn.qumiandan.shopmember.vo.PurchaseShopMemberVO;
import cn.qumiandan.shopmember.vo.ShopMemberVO;
import test.BaseTest;

public class ShopMemberTest extends BaseTest{

	@Resource
	private IShopMemberService shopMemberService;
	
	@Test
	public void purchaseShopMember() {
		System.out.println("--------purchaseShopMember--------");
		PurchaseShopMemberVO purchaseShopMemberVO = new PurchaseShopMemberVO(); 
		purchaseShopMemberVO.setUserId(1L);
		purchaseShopMemberVO.setShopId(2L);
		purchaseShopMemberVO.setDays(1.0);
		int  i = shopMemberService.purchaseShopMember(purchaseShopMemberVO);
		System.out.println(i);
	}
		
	@Test
	public void isShopMember() {
		System.out.println("--------isShopMember--------");
		ShopMemberVO shopMemberVO = shopMemberService.isShopMember(1L,1L);
		System.out.println(shopMemberVO);
	}
	
	
	@Test
	public void getShopMemberDiscount() {
		BigDecimal bigDecimal  = shopMemberService.getShopMemberDiscount(1L,1L);
		System.out.println(bigDecimal);
	}
}
