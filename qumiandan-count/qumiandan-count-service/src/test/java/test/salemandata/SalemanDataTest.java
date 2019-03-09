package test.salemandata;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.salemandata.api.ISalemanDataService;
import test.BaseTest;

public class SalemanDataTest extends BaseTest{
	
	@Autowired
	private ISalemanDataService dataService;
	
	@Test
	public void todayProfit() {
		BigDecimal todayProfit = dataService.todayProfit(64L);
		System.out.println(todayProfit);
	}
}
