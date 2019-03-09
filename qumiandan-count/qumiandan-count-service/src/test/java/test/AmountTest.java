package test;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.analyseData.api.IAnalyseDataService;
import cn.qumiandan.utils.DateUtil;

public class AmountTest extends BaseTest{
	@Autowired
	private IAnalyseDataService AnalyseDataService;
	@Test
	public void actualAmountTest() {
		
		//String str = DateUtil.formatDate("2018-12-25 00:00:00", "yyyy-MM-dd");
		Date date = new Date();
		String str = DateUtil.getStrFormTime("2018-12-28", date);
		
		date = DateUtil.getDateByStr(str);
		System.out.println(date);
		BigDecimal amount = AnalyseDataService.actualAmount(date);
		System.out.println(amount);
	}
	@Test
	public void payNumTest() {
		Long num = AnalyseDataService.totalPayNum();
		System.out.println(num);
	}
}
