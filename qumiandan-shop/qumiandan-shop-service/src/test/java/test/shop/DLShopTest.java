package test.shop;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.shop.api.IDyShopService;
import cn.qumiandan.shop.vo.DLQueryShopVO;
import cn.qumiandan.shop.vo.ShopVO;
import test.BaseTest;

public class DLShopTest extends BaseTest {

	@Autowired
	private IDyShopService shopservice;
	
	@Test
	public void queryDlShopTest() {
		DLQueryShopVO vo = new DLQueryShopVO();
		vo.setDyUserId(67L);
		vo.setPageNum(1);
		vo.setPageSize(1);
		PageInfo<ShopVO> info = shopservice.getShopPageByDyId(vo );
		System.out.println(info);
	}
}
