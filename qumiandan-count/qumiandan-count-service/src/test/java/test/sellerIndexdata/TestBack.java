package test.sellerIndexdata;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.common.collect.Lists;

import cn.qumiandan.sellerIndexData.api.ISellerBackGroundDataService;
import cn.qumiandan.sellerIndexData.vo.GetAllDataParamsVO;
import cn.qumiandan.sellerIndexData.vo.GetAllDataVO;
import cn.qumiandan.utils.DateUtil;
import test.BaseTest;

public class TestBack extends BaseTest{
	
	@Resource
	private ISellerBackGroundDataService backGroundDataService ;
	/*
	 * @Test public void tradeAmountTest() { List<Long> shopId=new
	 * ArrayList<Long>(); shopId.add(2L); shopId.add(10L); Date date = new Date();
	 * String str = DateUtil.getStrFormTime("2019-01-02", date);
	 * 
	 * date = DateUtil.getDateByStr(str);
	 * 
	 * BigDecimal tradeAmount = backGroundDataService.getTradeAmount(shopId, date);
	 * 
	 * System.out.println(tradeAmount);
	 * 
	 * }
	 */
	/*
	 * @Test public void receivableAmountTest() { List<Long> shopId=new
	 * ArrayList<Long>(); shopId.add(2L); shopId.add(10L); Date date = new Date();
	 * String str = DateUtil.getStrFormTime("2019-01-02", date);
	 * 
	 * date = DateUtil.getDateByStr(str);
	 * 
	 * BigDecimal tradeAmount = backGroundDataService.getReceivableAmount(shopId,
	 * date);
	 * 
	 * System.out.println(tradeAmount);
	 * 
	 * }
	 */
	
	@Test 
	public void getAllData(){
		GetAllDataParamsVO allDataParamsVO = new GetAllDataParamsVO();
		allDataParamsVO.setShopIds(Lists.newArrayList(108L,109L,100L,101l));
		allDataParamsVO.setEndDate(new Date());
		allDataParamsVO.setStartDate(DateUtil.StringToDate("2018-01-01 10:00:00"));
		GetAllDataVO allData = backGroundDataService.getAllData(allDataParamsVO);
		System.out.println(allData);
	}
}
