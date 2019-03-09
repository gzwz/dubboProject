package cn.qumiandan.validationticket.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.order.vo.OrderVO;
import cn.qumiandan.validationticket.vo.CreateValidationTicketVO;
import cn.qumiandan.validationticket.vo.ValidationTicketVO;

/**
 * @description: 核销券接口
 * @author: WLZ
 * @date: 2018/12/26 14:37
 */
public interface IValidationTicketService extends IBaseService {

    /**
     * 创建核销券
     * @param validationTicketVO
     * @return
     */
	CreateValidationTicketVO addValidationTicket(CreateValidationTicketVO validationTicketVO);
    
    /**
     * 根据核销券id查询核销券
     * @param ticketId
     * @return
     */
    ValidationTicketVO getValTicketById(Long ticketId);
    /**
     * 
     *  根据核销券码查询核销券
     * (合并创建核销卷 和 修改订单状态 两个操作, 方便rpc调用 本库操作避免分布式事务)
     * @param order
     * @return
     */
    ValidationTicketVO getValTicketByTicketCode(String ticketCode);
    /**
     * 根据订单id查询核销券
     * @param ticketId
     * @return
     */
    ValidationTicketVO getValTicketByOrderId(String orderId);
    
    /**
     * 用户查询自己的核销券
     * @param ticketId
     * @return 分页信息
     */
    PageInfo<ValidationTicketVO> getValTicketsForUserId(Long userId,int pageNum,int pageSize);
    
   
    
  
    /**
     * 
     * 创建核销卷并更新订单状态
     * (合并创建核销卷 和 修改订单状态 两个操作, 方便rpc调用 本库操作避免分布式事务)
     * @param order
     * @return
     */
    int createValidationTicketAndUpdateOrderStatus(OrderVO order);
    
    
    /**
     * 根据核销卷编号查询未使用的核销卷
     * @param ticketCode
     * @return
     */
    ValidationTicketVO getUnuseValidationTicketByTicketCode(String ticketCode);
    
    /**
     * 修改核销卷
     * @param vo
     */
    void updateValidationTicket(ValidationTicketVO vo);
    
    /**********************************业务方法********************************************/
    /**
     * 同时修改核销卷和订单信息
     * @param ticket
     * @param order
     */
    void updateValidationTicketAndOrderInfo(ValidationTicketVO ticket, OrderVO order);
    
    /**
     * 核销-核销券
     * @param ticketCode 核销码
     * @param userId 当前核销商家用户的id
     * @param  ticketCode 核销码
     * @return
     */
    ValidationTicketVO useValTicketsForMerchentUserIdAndTicketCode(Long userId,String ticketCode);
    
}
