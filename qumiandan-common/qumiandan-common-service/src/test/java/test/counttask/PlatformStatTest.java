package test.counttask;

import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.util.Lists;
import org.junit.Test;

import cn.qumiandan.counttask.api.IDlorSaleStatsService;
import cn.qumiandan.counttask.api.IPlatformStatsService;
import cn.qumiandan.counttask.vo.AdminStatQueryVO;
import cn.qumiandan.counttask.vo.DLStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatQueryVO;
import cn.qumiandan.utils.DateUtil;
import test.BaseTest;

public class PlatformStatTest extends BaseTest{

	@Resource
	private IPlatformStatsService service;
	@Resource
	private IDlorSaleStatsService dlorService;
	
	
	@Test
	public void getall() {
		dlorService.taskQuery();
	}
	
	@Test
	public void getAllMaintainRecord() {
		AdminStatQueryVO vo = new AdminStatQueryVO();
		vo.setStartTime(DateUtil.getDate(-20));
		vo.setEndTime(DateUtil.getDate(-17));
		vo.setProCode("520000");
		List<PlatformStatQueryVO> page = service.getPlatformStatPage(vo);
		System.out.println(page);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void getShopStatPageForDL() {
		DLStatQueryVO vo = new DLStatQueryVO();
		vo.setShopIds(Lists.newArrayList(108L,109L));
		vo.setStartTime(DateUtil.StringToDate("2019-01-01 08:00:00"));
		vo.setEndTime(DateUtil.StringToDate("2019-01-31 08:00:00"));
		List<PlatformStatQueryVO> shopStatPageForDL = service.getShopStatPageForDL(vo);
		System.out.println(shopStatPageForDL);
	}
}
