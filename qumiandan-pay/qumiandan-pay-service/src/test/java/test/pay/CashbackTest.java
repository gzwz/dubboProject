package test.pay;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.pay.cashback.api.ICashbackService;
import cn.qumiandan.pay.cashback.enums.CashbackStatusEnum;
import cn.qumiandan.pay.cashback.vo.CashbackVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackRequestParamsVO;
import cn.qumiandan.pay.cashback.vo.QueryCashbackResPonseParamsVO;
import test.BaseTest;

public class CashbackTest extends BaseTest{
	
	@Autowired
	private ICashbackService cashbackService;
	
	@Test
	public void addCashback() {
		CashbackVO cashbackVO = new CashbackVO();
		cashbackVO.setTicketId("1082928816462004226");	
		CashbackVO addCashback = cashbackService.addCashback(cashbackVO);
		System.out.println(addCashback);
	}
	
	@Test
	public void updateCashback() {
		CashbackVO cashbackVO = new CashbackVO();
		cashbackVO.setId(2L);
		cashbackService.updateCashback(cashbackVO);
	}
	
	@Test
	public void queryCashback() {
		QueryCashbackRequestParamsVO params = new QueryCashbackRequestParamsVO();
		params.setStatusList(Lists.newArrayList(new Byte("1")));
		PageInfo<QueryCashbackResPonseParamsVO> queryCashback = cashbackService.queryCashback(params);
		System.out.println(queryCashback);
	}
	
	
	@Test
	public void auditCashback() {
		CashbackVO cashbackVO = new CashbackVO();
		cashbackVO.setId(2L);
		cashbackVO.setStatus(CashbackStatusEnum.FinishedWithdraw.getCode());
		cashbackService.auditCashback(cashbackVO);
	}
}
