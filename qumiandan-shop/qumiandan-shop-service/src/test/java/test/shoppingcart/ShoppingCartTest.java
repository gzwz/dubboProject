package test.shoppingcart;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.shoppingcart.api.IShoppingCartService;
import cn.qumiandan.shoppingcart.vo.ShoppingCartVO;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

public class ShoppingCartTest extends BaseTest {

	@Resource 
	private IShoppingCartService shoppingCartService;
	
//	addShoppingCart
	@Test
	public void addShoppingCart() {
		System.out.println("---------addShoppingCart----------");
		ShoppingCartVO  shoppingCart = new ShoppingCartVO ();
		shoppingCart.setUserId(1L);
		shoppingCart.setProductId(1L);
		shoppingCart.setShopId(1L);
		shoppingCart.setNumber(1);
		shoppingCart.setProductExists(ToolUtil.intToByte(1));
		
		System.out.println(shoppingCartService.addShopingCart(shoppingCart));
	}
	
//	getShopingCartByUserId
	@Test
	public void getShopingCartByUserId() {
		System.out.println("---------getShopingCartByUserId----------");
		System.out.println(shoppingCartService.getShopingCartByUserId(1L));
	}
	
	@Test
	public void deleteShoppingCart() {
		System.out.println("---------deleteShoppingCart----------");
		System.out.println(shoppingCartService.deleteShopingCart(1L));
	}
}
