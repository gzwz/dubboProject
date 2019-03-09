package test.shop;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.shop.api.IShopGameSwitchService;
import test.BaseTest;

public class ShopGameSwitchTest extends BaseTest{
	@Autowired
	private IShopGameSwitchService ShopGameSwitchService;
	@Test
	public void switchTest() {
		Byte b=0;
		ShopGameSwitchService.gameSwitch(1L, b);
	}
}
