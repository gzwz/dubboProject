package test.validationticket;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.validationticket.api.IValidationTicketService;
import cn.qumiandan.validationticket.vo.CreateValidationTicketVO;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;
import test.BaseTest;

/**
 * @description: 核销券测试类
 * @author: zhuayngyong
 * @date: 2018/12/5 14:59
 */
public class ValidationTicketTest extends BaseTest {

    @Resource
    private IValidationTicketService validationTicketService;
    
    
    @Test
    public void getValTicketByTicketCode() {
    	ValidationTicketVO ticketVO = validationTicketService.getValTicketByTicketCode("7e8738c0a75c6a86cf35df211d53cb70");
    	System.out.println(ticketVO);
    }

    @Test
    public void testAddValidationTicket(){
        System.out.println("---validationTicketService.addValidationTicket()---");
        CreateValidationTicketVO vo = new CreateValidationTicketVO();
        vo.setOrderId("201812043550645650980864");
        vo.setShopId(10L);
        vo.setUserId(1L);
        vo.setUpdateId(1L);
        System.out.println(validationTicketService.addValidationTicket(vo));
    }
    
    @Test
    public void  getValTicketById() {
    	ValidationTicketVO ticket = validationTicketService.getValTicketById(5L);
    	System.out.println(ticket);
    }
    
    @Test
    public void getValTicketsForUserId() {
    	PageInfo<ValidationTicketVO> pageInfo = validationTicketService.getValTicketsForUserId(1L, 2, 1);
    	System.out.println(pageInfo);
    }
    
    @Test
    public void useValTicketsForUserIdAndShopId() {
    	ValidationTicketVO ticketVO = validationTicketService.useValTicketsForMerchentUserIdAndTicketCode(null,"120181086410");
    	System.out.println(ticketVO);
    }
}
