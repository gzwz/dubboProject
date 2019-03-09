package test.backgrounddata;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.backgrounddata.api.IDlorSaleStatQueryService;
import cn.qumiandan.backgrounddata.vo.DlorSaleStatQueryVO2;
import test.BaseTest;

public class DlorSaleStatQueryTest extends BaseTest{
	
	@Autowired
	private IDlorSaleStatQueryService dlorSaleStatQueryService;
	
	@Test
	public void queryDlorSaleStat() {
		List<DlorSaleStatQueryVO2> queryDlorSaleStat = dlorSaleStatQueryService.queryDlorSaleStat();
		System.out.println(queryDlorSaleStat);
	}
}
