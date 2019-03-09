package test.supportpayment;

import java.util.List;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.supportpayment.api.ISupportPaymentService;
import cn.qumiandan.supportpayment.vo.SupportPaymentVO;
import test.BaseTest;

public class SupportPaymentTest extends BaseTest{

	@Reference
	private ISupportPaymentService supportPaymentService;
	
	@Test
	public void getAllSupportPayment() {
		System.out.println("------getAllSupportPayment------");
		List<SupportPaymentVO> list = supportPaymentService.getAllSupportPayment();
		System.out.println(list);
	}
	
	@Test
	public void getSupportPaymentByCode() {
		System.out.println("------getSupportPaymentByCode------");
		List<SupportPaymentVO> list = supportPaymentService.getSupportPaymentByCode("PAY101,PAY101");
		System.out.println("测试"+list);
	}
}
