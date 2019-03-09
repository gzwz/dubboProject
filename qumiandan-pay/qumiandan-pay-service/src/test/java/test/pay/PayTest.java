package test.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.saobei.model.request.pay.JSRefundReq;
import cn.qumiandan.pay.saobei.vo.response.pay.JSRefundRes;
import cn.qumiandan.utils.GsonHelper;
import lombok.extern.slf4j.Slf4j;
import test.BaseTest;

@Slf4j
public class PayTest extends BaseTest {

	

	
/*//	@Autowired
//	private ISaoBeiMerchantService saoBeiService;
//	
//	@Autowired
//	private ISaoBeiPayService paySeBeiPayService;
	
	@Autowired
	private ISaoBeiPayService paySeBeiPayService;
	
	@Test
	public void checMerchantName() {
		ChecMerchantNameReqVO vo = new ChecMerchantNameReqVO();
		vo.setMerchantName("云岩区小明的店铺");
		saoBeiService.checMerchantName(vo);
	}
	
	@Test
	public void createMerchant() {
		SmallMerchantReqVO vo = new SmallMerchantReqVO();
		vo.setImgPrivateIdcardA("https://img.zcool.cn/community/014f555a38b41da80121db80b057a2.jpg@1280w_1l_2o_100sh.jpg")
		.setImgPrivateIdcardB("https://img.zcool.cn/community/014f555a38b41da80121db80b057a2.jpg@1280w_1l_2o_100sh.jpg")
		.setImgUdcardHolding("https://img.zcool.cn/community/014f555a38b41da80121db80b057a2.jpg@1280w_1l_2o_100sh.jpg")
		.setImgLogo("https://img.zcool.cn/community/014f555a38b41da80121db80b057a2.jpg@1280w_1l_2o_100sh.jpg")
		.setImgIndoor("https://img.zcool.cn/community/014f555a38b41da80121db80b057a2.jpg@1280w_1l_2o_100sh.jpg")
		.setImgBankcardA("https://img.zcool.cn/community/014f555a38b41da80121db80b057a2.jpg@1280w_1l_2o_100sh.jpg")
		.setMerchantIdNo("332526199502154510")
		.setMerchantName("zsk测试第一家1")
		.setMerchantAlias("测试商户")
		.setMerchantCompany("测试商户注册名称/公司全称")
		.setMerchantProvince("贵州")
		.setMerchantProvinceCode("520")
		.setMerchantCity("贵阳")
		.setMerchantCityCode("7010")
		.setMerchantCounty("云岩区")
		.setMerchantCountyCode("7012")
		.setMerchantAddress("贵阳市云岩区小十字XXXX")
		.setMerchantPerson("胡凯彬 ")
		.setMerchantPhone("13087804770")
		.setMerchantEmail("794393354@qq.com")
		.setBusinessName("食品")
		.setBusinessCode("292")
		.setLicenseType(2)
		.setAccountName("胡凯彬")
		.setAccountNo("6210286020189716")
		.setBankName("稠州银行丽水壶镇支行")
		.setBankNo("104701071057")
		.setSettleType("1")
		.setSettleAmount(1)
		.setRateCode("M0025");
		saoBeiService.createMerchant(vo);
	}
	
	@Test
	public void checMerchantName() {
		TerminalReqVO vo = new TerminalReqVO();
		vo.setMerchantNo("870100292000016");
		saoBeiService.createTerminal(vo);
	}
	
	@Test
	public void queryTerminal() {
		QueryTerminalReqVO vo = new QueryTerminalReqVO();
		vo.setTerminalId("30055538");
		saoBeiService.queryTerminal(vo);
	}
	
	@Test
	public void checMerchantName() {
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		WechatAuthopenIdReq vo = new WechatAuthopenIdReq();
		vo.setMerchantNo("870100292000016")
		.setTerminalNo("30055538")
		.setAccessToken("be80974f76424e35ac8053ecbf8f0b03");
		vo.setRedirectUri("http://www.baidu.com");
		paySeBeiPayService.getAuthopenId(vo);
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}
	
	
	@Test
	public void checMerchantName() {
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		WechatAuthopenIdVO vo = new WechatAuthopenIdVO();
		vo.setRedirectUri("https://www.baidu.com");
		WechatAuthopenIdResVO authopenId = paySeBeiPayService.getAuthopenId(vo);
		System.out.println(authopenId.getUrl());
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
	}*/
	
	
	@Test
	public void refund() {
		JSRefundReq req = new JSRefundReq();
		req.setPayType("010");
		req.setMerchantNo("834300206000001");
		req.setTerminalId("10741888");
		req.setTerminalTrace("1");
		req.setRefundFee("10");
		req.setOutTradeNo("107418889521318123018254408941");
		req.setAccessToken("36d93a4ea4134da0848e8a4d4514e43d");
		req.getSign();
		
		long start = System.currentTimeMillis();
		log.info("-----------------------------------开始------------------------------------------");
		Map<String, String> condition = req.getRequestParameter();
		
		log.info(new StringBuilder("SaoBeiPayServiceImpl|").append("doJSRefund|").append("request:").append(req.toJson()).toString());
		String resultStr = doPostForJson("http://pay.lcsw.cn/lcsw/pay/100/refund", condition);
		log.info(new StringBuilder("SaoBeiPayServiceImpl|").append("doJSRefund|").append("response:").append(resultStr).toString());
		JSRefundRes result = null;
		if (StringUtils.isNotBlank(resultStr)) {
			result = GsonHelper.fromJson(resultStr, JSRefundRes.class);
		} else {
			log.error("请求扫呗退款申请 返回空字符串");
			throw new QmdException("系统异常");
		}
		long consumeTime = System.currentTimeMillis() - start;
		log.info("-----------------------------------结束 耗时:" + consumeTime + " ------------------------------------------");
		System.out.println(result.toString() + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	public static String doPostForJson(String url, Map<String, String> condition) {
		StringBuilder result = new StringBuilder();
		CloseableHttpClient httpclient = HttpClients.custom().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("charset", "UTF-8");
			String json = GsonHelper.toJson(condition);
			StringEntity stentity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			stentity.setContentEncoding("UTF-8");
			stentity.setContentType("application/json");
			httpPost.setEntity(stentity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result.append(text);
					}
				}
				EntityUtils.consume(entity);
				return result.toString();
			}  finally {
				response.close();
			}
		} catch (Exception e) {
			
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
}
