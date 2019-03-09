package test.ticket;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.ticket.api.IQualificationTicketService;
import cn.qumiandan.ticket.api.ITicketUseRecordService;
import cn.qumiandan.ticket.vo.AgentQueryTicketByUserIdVO;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.ticket.vo.QueryRecordVO;
import cn.qumiandan.ticket.vo.QueryTicketParamsVO;
import test.BaseTest;

public class TicketTest extends BaseTest {
	@Autowired
	private IQualificationTicketService service;
	
	@Autowired
	private ITicketUseRecordService ticketRecordTest;
	
	@Test
	public void delticket() {
		Set<String> ids = new HashSet<String>();
		ids.add("1087282395196993543"); 
		ids.add("1087282395196993544");
		service.delQualificationTicketByIds(ids );
	}
	
	@Test
	public void createTest() {
		QualificationTicketVO param = new QualificationTicketVO();
		param.setName("资格券");
		param.setPresentPrice(new BigDecimal(200));
		param.setReturnMoney(new BigDecimal(10));
		List<QualificationTicketVO> tickets = service.createTicket(10L, param);
		System.out.println(tickets);
	}
	
	@Test
	public void getTicket() {
		QualificationTicketVO ticketVO = service.getTicketById("1076021102817640450");
		System.out.println(ticketVO);
	}
	
	@Test
	public void setTicketStatusToUsing() {
		System.out.println("---setTicketStatusToUsing---");
		int i = service.setTicketStatusToUsing("1076021112867192834",100L);
		System.out.println(i);
	}
	
	
	@Test
	public void useTicketByShopId() {
		System.out.println("---useTicketByShopId---");
		service.useTicketByShopId(100L);
		
	}
	
	
	@Test
	public void queryTicket() {
		System.out.println("---queryTicket---");
		QueryTicketParamsVO paramsVO = new QueryTicketParamsVO();
		PageInfo<QualificationTicketVO> queryTicket = service.queryTicket(paramsVO, 1, 2);
		System.out.println(queryTicket);
	}
	
	
	@Test
	public void getTicketUseRecordByTicketId() {
		List<QueryRecordVO> ticketUseRecordByTicketId = ticketRecordTest.getTicketUseRecordByTicketId("1085058911393812481");
		System.out.println(ticketUseRecordByTicketId);
	}
	
	
	@Test
	public void agentQueryTicketByUserId() {
		AgentQueryTicketByUserIdVO agentQueryTicketByUserIdVO = new AgentQueryTicketByUserIdVO();
		agentQueryTicketByUserIdVO.setPageNum(1);
		agentQueryTicketByUserIdVO.setPageSize(10);
		agentQueryTicketByUserIdVO.setAgentUserId(67L);
		agentQueryTicketByUserIdVO.setOfflineUserId(70L);
		PageInfo<QualificationTicketVO> agentQueryTicketByUserId = service.agentQueryTicketByUserId(agentQueryTicketByUserIdVO);
		System.out.println(agentQueryTicketByUserId);
	}
}
