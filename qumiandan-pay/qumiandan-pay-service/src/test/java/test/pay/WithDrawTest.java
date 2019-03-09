package test.pay;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.pay.withdraw.api.IWithdrawCashService;
import cn.qumiandan.pay.withdraw.vo.QueryResponseParamsVO;
import cn.qumiandan.pay.withdraw.vo.QueryWithdrawCashParamsVO;
import cn.qumiandan.pay.withdraw.vo.WithdrawCashVo;
import test.BaseTest;

public class WithDrawTest extends BaseTest{
	@Autowired
	private IWithdrawCashService withdrawCashService;
	
	@Test
	public void applyCash() {
//		
//		WithdrawCashVo withdrawCashVo=new WithdrawCashVo();
//		
//		withdrawCashVo.setBankName("中国银行");
//		withdrawCashVo.setAuditorDate(new Date());
//		withdrawCashVo.setHolderName("zhangsan");
//		Long sLong=(long) 999;
//		 withdrawCashVo.setAccountId(sLong); 
//		WithdrawCashVo vo = withdrawCashService.applyCash(withdrawCashVo,3L);
//		System.out.println(vo.getId());
	}
	@Test
	public void testAudit() {
		WithdrawCashVo Vo=new WithdrawCashVo();
		Vo.setAuditorDate(new Date());
//		Vo.setStatus(2);
		Vo.setId(8L);
		withdrawCashService.applyAudit(Vo);
	}
	
	
	@Test
	public void queryWithdrawCash() {
		QueryWithdrawCashParamsVO cashParamsVO = new QueryWithdrawCashParamsVO();
//		cashParamsVO.setAccountType(new Byte("1"));
		QueryResponseParamsVO queryWithdrawCash = withdrawCashService.queryWithdrawCash(cashParamsVO);
		System.out.println(queryWithdrawCash);
	}
}
