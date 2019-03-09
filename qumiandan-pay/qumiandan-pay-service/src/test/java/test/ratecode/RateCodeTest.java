package test.ratecode;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.common.collect.Lists;

import cn.qumiandan.ratecode.api.IRateCodeService;
import cn.qumiandan.ratecode.vo.RateCodeVO;
import test.BaseTest;
/**
 * 费率测试
 * @author lrj
 *
 */
public class RateCodeTest extends BaseTest{
	
	@Resource
    private  IRateCodeService rateCodeService;
	
	@Test
	public void getRateCode() {
		System.out.println("----getRateCode-----");
		System.out.println(rateCodeService.getRateCode("T010020"));
	}
	

	@Test
	public void getAllRateCode() {
		System.out.println("----getAllRateCode-----");
		List<RateCodeVO> allRateCode = rateCodeService.getAllRateCode(Lists.newArrayList(new Byte("1"),new Byte("2") ));
		System.out.println(allRateCode);
	}
	

	@Test
	public void addRateCode() {
		System.out.println("----addRateCode-----");
		RateCodeVO codeVO = new RateCodeVO();
		codeVO.setCode("M11011");
		codeVO.setRate(new BigDecimal(0.1));
		System.out.println(rateCodeService.addRateCode(codeVO));
	}
	
	@Test
	public void updateRateCode() {
		System.out.println("----updateRateCode-----");
		RateCodeVO codeVO = new RateCodeVO();
		codeVO.setId(171L);
		codeVO.setCode("M11011");
		codeVO.setRate(new BigDecimal(0.1));
		System.out.println(rateCodeService.updateRateCode(codeVO));
	}
	
	@Test
	public void deleteRateCode() {
		System.out.println("----deleteRateCode-----");
		System.out.println(rateCodeService.deleteRateCode(170L));
	}
}
