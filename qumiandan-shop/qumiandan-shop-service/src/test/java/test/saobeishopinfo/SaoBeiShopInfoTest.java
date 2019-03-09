package test.saobeishopinfo;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.saobeishopinfo.api.ISaoBeiShopInfoServicce;
import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;
import test.BaseTest;

public class SaoBeiShopInfoTest extends BaseTest{

	@Resource
	private ISaoBeiShopInfoServicce saoBeiShopInfoServicce;
	
	@Test
	public void addSaoBeiShopInfo() {
		System.out.println("-----addSaoBeiShopInfo-----");
		SaoBeiShopInfoVO saoBeiShopInfoVO = new SaoBeiShopInfoVO();
		saoBeiShopInfoVO.setShopId(1L);
		int i = saoBeiShopInfoServicce.addSaoBeiShopInfo(saoBeiShopInfoVO);
		System.out.println(i);
	}


	@Test
	public void updateSaoBeiShopInfo() {
		System.out.println("-----updateSaoBeiShopInfo-----");
		SaoBeiShopInfoVO saoBeiShopInfoVO = new SaoBeiShopInfoVO();
		saoBeiShopInfoVO.setShopId(1L);
		int i = saoBeiShopInfoServicce.updateSaoBeiShopInfo(saoBeiShopInfoVO);
		System.out.println(i);
	}
	
//	getSaoBeiShopInfo
	@Test
	public void getSaoBeiShopInfo() {
		System.out.println("-----getSaoBeiShopInfo-----");
		SaoBeiShopInfoVO beiShopInfoVO = saoBeiShopInfoServicce.getSaoBeiShopInfo(1L);
		System.out.println(beiShopInfoVO);
	}
	
	
	
	@Test
	public void deleteSaoBeiShopInfo() {
		System.out.println("-----deleteSaoBeiShopInfo-----");
		int i  = saoBeiShopInfoServicce.deleteSaoBeiShopInfo(1L);
		System.out.println(i);
	}
}
