package test.saleman;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.vo.AddSalmanVO;
import cn.qumiandan.saleman.vo.SalemanAndRoleVO;
import cn.qumiandan.saleman.vo.SalemanAndUserParamVO;
import cn.qumiandan.saleman.vo.SalemanAndUserVO;
import cn.qumiandan.saleman.vo.ShopAgentVO;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;

@Slf4j
public class ManagerTest extends BaseTest {

	@Autowired
	private ISalemanService service;
	@Test
	public void addSaleman() {
		AddSalmanVO managerVO = new AddSalmanVO();
		managerVO.setCardType(new Byte("2"));
		managerVO.setRate(new BigDecimal(500));
		managerVO.setProCode("520000");
		managerVO.setUserName("18602182076");
		managerVO.setCityCode("110100");
		managerVO.setType(new Byte("4"));
		managerVO.setRate(new BigDecimal(0.25));
		log.info("-----------------------------------开始------------------------------------------");
		long start = System.currentTimeMillis();
		System.out.println(service.addSaleman(managerVO));
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
		
	}
	
	@Test
	public void querySalemanAndUser() {
		System.out.println("=====querySalemanAndUser=====");
		SalemanAndUserParamVO params = new SalemanAndUserParamVO();
		params.setCountryCode("520102");
//		params.setId(29L);
		params.setPageNum(1);
		params.setPageSize(10);
		PageInfo<SalemanAndUserVO> querySalemanAndUser = service.querySalemanAndUser(params);
		System.out.println(querySalemanAndUser);
	}

	@Test
	public void getAgentAndSalemanByShopId() {
		ShopAgentVO agentAndSalemanByShopId = service.getAgentAndSalemanByShopId(108L);
		System.out.println(agentAndSalemanByShopId);
	}
	
	@Test
	public void  getSalemanAndRoleByUserId(){
		SalemanAndRoleVO salemanAndRoleByUserId = service.getSalemanAndRoleByUserId(67L);
		System.out.println(salemanAndRoleByUserId);
	}
	
	@Test
	public void   isOffline() {
		Boolean offline = service.isOffline(67L, 70L);
		System.out.println(offline);
	}
}
