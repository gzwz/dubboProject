package test.bankinfo;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.bankinfo.api.IBankInfoService;
import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.bankinfo.vo.HeadBankInfoVO;
import test.BaseTest;

public class BankInfoTest extends BaseTest{
	@Autowired
	private IBankInfoService bankInfoService;
	
	@Test
	public void getHeadBankInfo() {
		System.out.println("--------getHeadBankInfo-------");
		List<HeadBankInfoVO> bankInfoVOs = bankInfoService.getHeadBankInfo(null);
		System.out.println(bankInfoVOs);
	}
	
	@Test
	public void getSubBankInfo() {
		System.out.println("--------getSubBankInfo-------");
		List<BankInfoVO> bankInfoVOs = bankInfoService.getSubBankInfo("105",null);
		System.out.println(bankInfoVOs);
	}

	@Test
	public void getAllSubBankInfo() {
		System.out.println("--------getAllSubBankInfo-------");
		PageInfo<BankInfoVO> bankInfoVOs = bankInfoService.getAllSubBankInfo("中国",1,10);
		System.out.println(bankInfoVOs);
	}
}
