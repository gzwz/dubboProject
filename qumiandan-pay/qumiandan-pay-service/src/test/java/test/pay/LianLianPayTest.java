package test.pay;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.pay.lianlian.api.ILianLianPayService;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;

@Slf4j
public class LianLianPayTest extends BaseTest {

	
	@Autowired
	private ILianLianPayService lianLianPayService;
	
	
	@Test
	public void getAuthInfo() {
		log.info("-----------------------------------开始------------------------------------------");
		long start = System.currentTimeMillis();
		lianLianPayService.withdraw(1L);
		long consumeTime = System.currentTimeMillis() - start;                                 
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}     
	
}
