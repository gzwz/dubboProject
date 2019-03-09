package test.pay;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.pay.saobei.model.request.helper.AttachHelper;
import cn.qumiandan.pay.saobei.service.ISaobeiCallbackService;
import cn.qumiandan.pay.saobei.vo.response.pay.callback.JSPayCallbackVO;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;

@Slf4j
public class PayCallbackTest extends BaseTest {

	@Autowired
	private ISaobeiCallbackService saobeiCallbackService;
	
	/*@Test
	public void test() {
		JSPayCallbackVO vo = new JSPayCallbackVO();
		vo.setResultCode("01");
		vo.setReturnCode("01");
		vo.setReturnMsg("123");
		vo.setPayType("010");
		vo.setUserId("123123123123");
		vo.setMerchantName("zsk");
		vo.setMerchantNo("1234441");
		vo.setTerminalId("123123");
		vo.setPayTrace("123123333123");
		vo.setPayTime("20181220111111");
		vo.setTotalFee("100");
		vo.setOutTradeNo("201812103494086778552320");
		vo.setChannelTradeNo("201812103494086778552320");
		AttachHelper attach = new AttachHelper();
		attach.setGameOrderId("1");
		attach.setOrderId("201812103494086778552320");
		attach.setTradeId(1L);
		vo.setAttach(GsonHelper.toJson(attach));
		vo.setReceiptFee("111");
		vo.setKeySign("1");
		log.info("-----------------------------------开始------------------------------------------");
		long start = System.currentTimeMillis();
		saobeiCallbackService.jsPayCallback(vo);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}*/
	
	
	@Test
	public void checMerchantName1() {
		JSPayCallbackVO v = new JSPayCallbackVO();
		v.setReturnCode("01");
		v.setReturnMsg("支付成功");
		v.setResultCode("01");
		v.setPayType("010");
		v.setUserId("112312312312313213213");
		v.setMerchantName("智售客1号");
		v.setMerchantNo("834300206000001");
		v.setTerminalId("10741888");
		v.setTerminalTrace("123132123");
		v.setTerminalTime("20181227102511");
		v.setPayTrace("01123132");
		v.setPayTime("20181227102511");
		v.setTotalFee("500");
		v.setEndTime("20181227102511");
		v.setOutTradeNo("107418883721318122620173505652");
		v.setChannelTradeNo("4200000215201812263885237054");
		AttachHelper a = new AttachHelper();
		a.setOrderId("2018122457117097011970048");
		a.setTradeId(1L);
		System.out.println(a.toJson());
		v.setAttach(a.toJson());
		v.setReceiptFee("20181227102511");
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		saobeiCallbackService.jsPayCallback(v);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	
/*	
	@Test
	public void checMerchantName2() {
		JSPayCallbackVO v = new JSPayCallbackVO();
		v.setReturnCode("01");
		v.setReturnMsg("支付成功");
		v.setResultCode("01");
		v.setPayType("010");
		v.setUserId("112312312312313213213");
		v.setMerchantName("智售客1号");
		v.setMerchantNo("834300206000001");
		v.setTerminalId("10741888");
		v.setTerminalTrace("123132123");
		v.setTerminalTime("20181227102511");
		v.setPayTrace("01123132");
		v.setPayTime("20181227102511");
		v.setTotalFee("500");
		v.setEndTime("20181227102511");
		v.setOutTradeNo("107418883721318122620173505652");
		v.setChannelTradeNo("4200000215201812263885237054");
		AttachHelper a = new AttachHelper();
		a.setOrderId("2018122457117097011970048");
		a.setGameOrderId("2018121737227517924343864");
		a.setTradeId(1L);
		System.out.println(a.toJson());
		v.setAttach(a.toJson());
		v.setReceiptFee("20181227102511");
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		saobeiCallbackService.jsPayCallbackGame(v);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}*/
}
