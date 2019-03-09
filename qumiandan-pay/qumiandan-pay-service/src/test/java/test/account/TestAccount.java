package test.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;
@Slf4j
public class TestAccount extends BaseTest{

	@Autowired
	private IPayAccountService payAccountService;

	/*@Test
	public void checMerchantName() {
		PayAccountVO v = new PayAccountVO();
		v.setAccount("xxxx");
		v.setCreateId(1L);
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		PayAccountVO addPayAccount = payAccountService.addPayAccount(v);
		log.info(addPayAccount.getId() + "");
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}*/
/*	
	
	@Test
	public void checMerchantName() {
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		payAccountService.calcPayedCallbackProfitOrderAndIncreaseBalance(2L, 10l, "2018122612246263726604288", null, "107418884021318122619022000814", new BigDecimal(10), new Date());
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	
	@Test
	public void getAcountMoneyByShopAdminId() {
		List<AcountMoneyVO> acountMoneyByShopAdminId = payAccountService.getAcountMoneyByShopAdminId(1L);
		System.out.println(acountMoneyByShopAdminId);
	}*/
	
	
	
	@Test
	@Transactional(rollbackFor = {Exception.class , QmdException.class})
	public void calc() {
		// 28L, 98L, "201901059983834766442496", "2019010510346309319917568", "4200000227201901056121157090", new BigDecimal(100), new Date()
		/*TradeDataVO vo = new TradeDataVO();
		vo.setShopAccountId(54L);
		vo.setShopId(113L);
		vo.setOrderId("20190114612842189357056");
		vo.setGameOrderId("20190114975268336369664");
		vo.setOutTradeNo("4200000227201901056121157090");
		vo.setIncreaseValue(new BigDecimal(100));
		vo.setOperateDate(new Date());
		payAccountService.calcPayedCallbackProfitGameOrderAndIncreaseBalance(vo);*/
		BigDecimal base = BigDecimal.ZERO;
		PayAccountVO v = new PayAccountVO();
		v.setAccount("333");
		v.setAccountBalance(base);
		v.setBalance(base);
		v.setCreateDate(new Date());
		v.setCreateId(1L);
		v.setName("ddd");
		v.setSecurityMoney(base);
		v.setShopId(8882L);
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		payAccountService.addPayAccount(v);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	
	
	@Test
	public void getAccountByShopIds() {
		List<PayAccountVO> payAccountByShopIds = payAccountService.getPayAccountByShopIds(Lists.newArrayList(109L));
		System.out.println(payAccountByShopIds);
	}
}
