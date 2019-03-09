package test.shopprofit;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.shopprofit.vo.ShopProfitDetailVO;
import cn.qumiandan.shopprofit.vo.ShopProfitVO;
import test.BaseTest;

public class ShopProfitTest extends BaseTest{

	@Resource
	private IShopProfitService profitService;
	
	@Test
	public void updateShopProfit() {
		System.out.println("---profitService.updateShopProfit()---");
		ShopProfitVO shopProfitVO = new  ShopProfitVO();
		shopProfitVO.setSbRateCode("T009010");
		shopProfitVO.setShopId(1L);
		System.out.println(profitService.updateShopProfit(shopProfitVO));
	}
	
	@Test
	public void getShopProfitByShopId() {
		System.out.println("---profitService.getShopProfitByShopId()---");
		ShopProfitDetailVO profitDetailVO = profitService.getShopProfitByShopId(1L);
		System.out.println(profitDetailVO);
	}
}
