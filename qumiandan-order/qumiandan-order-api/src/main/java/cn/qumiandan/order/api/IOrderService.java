package cn.qumiandan.order.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.order.vo.CreateOrderResponseParamsVO;
import cn.qumiandan.order.vo.GameExtendVO;
import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderQueryParamsVO;
import cn.qumiandan.order.vo.OrderQueryVO;
import cn.qumiandan.order.vo.OrderStatusListVO;
import cn.qumiandan.order.vo.OrderUpdateStatusVO;
import cn.qumiandan.order.vo.OrderVO;

/**
 * @description: 订单接口
 * @author: zhuayngyong
 * @date: 2018/12/4 11:37
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param orderAddVO
     * @return
     */
	CreateOrderResponseParamsVO createOrder(OrderAddVO orderAddVO);

    /**
     * 根据订单编号修改订单状态
     * @param orderUpdateStatusVO
     * @return
     */
	OrderVO updateOrderStatusById(OrderUpdateStatusVO orderUpdateStatusVO);

    /**
     * 根据订单编号查询订单详情
     * @param orderId
     * @return
     */
    OrderQueryVO getOrderDetailById(String orderId);
    
    /**
     * 根据条件查询订单
     * @param orderStatusListVO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<OrderQueryVO> getOrderByorderStatusList(OrderStatusListVO orderStatusListVO,int pageNum ,int pageSize);

    /**
     * 根据店铺id集合查询订单
     * @param shopIdListVO
     * @return
     */
    PageInfo<OrderQueryVO> getOrderByShopIdList(OrderQueryParamsVO orderQueryParamsVO,int pageNum,int pageSize);
    
    /**
     * 根据订单编号查询订单详情
     * @param orderId
     * @return
     */
    OrderVO getOrderById(String orderId);
    
    /**
     * 修改订单状态
     * @param orderId
     * @param status
     * @return
     */
    Integer setOrderStatus(String orderId,Byte status); 
    
    /**
     * 更新order信息
     * @param orderVO
     * @return
     */
    Integer updateOrder(OrderVO orderVO);
    
    /**
     * 根据用户id判断用户是否是新用户(未下过单及位新用户默认返回true)
     * @param userId
     * @return
     */
    Boolean estimateUserHasOrder(Long userId);
    
    /**
     * 处理业务
     * 同时修改订单信息和游戏订单信息
     */
    void updateOrderInfoAndGameOrderInfo(OrderVO order, GameExtendVO gameOrder);
    
    /**
     * 根据核销码订单详情
     * @param ticketId
     * @return
     */
    OrderQueryVO getOrderByValTicketCode(String ticketCode);

    /**
     * 用户取消支付
     * @param orderId
     * @param userId
     */
    void cancelPay(String orderId, Long userId);

}
