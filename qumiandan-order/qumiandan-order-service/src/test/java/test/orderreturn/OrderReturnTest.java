package test.orderreturn;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.orderreturn.api.IOrderReturnService;
import cn.qumiandan.orderreturn.vo.OrderReturnVO;
import test.BaseTest;

public class OrderReturnTest extends BaseTest{
	@Autowired
	private IOrderReturnService orderReturnService;
	@Test
	public void applyReturn() {
		OrderReturnVO VO=new OrderReturnVO();
		VO.setOrderId("201812302045489805524992");
		VO.setApplyRemark("1233333");		
		VO.setApplyReturnAmount(new BigDecimal("10"));
		VO.setUserId(27L);
		OrderReturnVO applyReturn = orderReturnService.applyReturn(VO);
		System.out.println(applyReturn.getId());
	}
	@Test
	public void auditPass() {
		OrderReturnVO auditPass = orderReturnService.auditPass(1L);
		System.out.println(auditPass.getApplyRemark()+"--"+auditPass.getAuditlStatus());
	}
}
