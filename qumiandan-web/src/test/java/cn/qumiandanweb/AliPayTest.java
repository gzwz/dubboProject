package cn.qumiandanweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.qumiandan.MutualWebApplication;
import cn.qumiandan.system.ali.service.IAliPayService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutualWebApplication.class)
public class AliPayTest {

	@Autowired
	private IAliPayService aliPayService;
	
	
	@Test
	public void getAuthInfo() {
		log.info("-----------------------------------开始------------------------------------------");
		long start = System.currentTimeMillis();
		aliPayService.getAuthInfo("2e4248c2f50b4653bf18ecee3466UC18");
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
}
