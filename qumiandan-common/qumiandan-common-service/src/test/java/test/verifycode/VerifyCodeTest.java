package test.verifycode;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.verifycode.vo.IdentifyingCodeVO;
import test.BaseTest;

public class VerifyCodeTest extends BaseTest {

	@Autowired
    StringRedisTemplate stringRedisTemplate;
	@Autowired
	IVerifyCodeService iVerifyCodeService;
    
	@Test
	public void redis() {
		
		BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps("150");
		Object num = boundValueOps.get();
		
		if (num == null) {
			boundValueOps.set("1");
		}else {
			boundValueOps.set(String.valueOf(Integer.valueOf(boundValueOps.get()) + 1),20,TimeUnit.SECONDS);
		}
		System.out.println(boundValueOps.get());
		boundValueOps.set(String.valueOf(Integer.valueOf(boundValueOps.get()) + 1),10,TimeUnit.SECONDS);
		System.out.println(boundValueOps.get());
		boundValueOps.set(String.valueOf(Integer.valueOf(boundValueOps.get()) + 1),10,TimeUnit.SECONDS);
		System.out.println(boundValueOps.get());
		
	}
	@Test
	public void addIdentifyingCode() {
		System.out.println("--------addIdentifyingCode-------");
		IdentifyingCodeVO identifyingCodeVO = new IdentifyingCodeVO();
		identifyingCodeVO.setCode("123456");
		identifyingCodeVO.setCreateDate(new Date());
		identifyingCodeVO.setMobile("18888888888");
		int i = iVerifyCodeService.addIdentifyingCode(identifyingCodeVO);
		System.out.println(i);
	}
}
