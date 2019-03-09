package test.sellerIndexdata;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.sellerIndexData.api.ISellerIndexDataService;
import cn.qumiandan.sellerIndexData.vo.SellerIndexDataVO;
import test.BaseTest;

public class TestGetSellerData extends BaseTest{
	@Autowired
	private ISellerIndexDataService sellerIndexDataService;
	@Test
	public void testGetTradeData() {
		SellerIndexDataVO sellerIndexData = sellerIndexDataService.getSellerIndexData(65L, new Byte("0"));
		System.out.println(sellerIndexData);
		
	}
}
