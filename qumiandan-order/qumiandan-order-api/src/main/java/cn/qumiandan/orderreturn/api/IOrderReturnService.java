package cn.qumiandan.orderreturn.api;
/**
 * 订单退款接口
 * @author W
 *
 */

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.orderreturn.vo.OrderReturnVO;
import cn.qumiandan.orderreturn.vo.RefundResultVO;

public interface IOrderReturnService extends IBaseService {
	/**
	 * 退款申请接口
	 * @param orderReturnVO
	 * @return
	 */
//	OrderReturnVO applyReturn(OrderReturnVO orderReturnVO,Long userId);
	
	/**
	 * 退款申请
	 * @param orderReturnVO
	 * @return
	 */
	OrderReturnVO applyReturn(OrderReturnVO orderReturnVO);
	/**
	 * 退款申请审核通过接口
	 * @param orderReturnVO
	 * @return
	 */
	OrderReturnVO auditPass(Long id);
	/**
	 * 退款申请审核未通过接口
	 * @param orderReturnVO
	 * @return
	 */
	OrderReturnVO auditUnPass(OrderReturnVO orderReturnVO);
	/**
	 * 支付回调接口
	 * @param orderReturnVO
	 * @param userId
	 * @return
	 */
	/* OrderReturnVO payCallBack(OrderReturnVO orderReturnVO); */
	/**
	 * 根据订单id查询退款申请
	 * @param orderId
	 * @return
	 */
	 OrderReturnVO selectByOrderId(String orderId); 
	
	
	/**
	 * 根据申请id 获取退款申请信息
	 * @param id
	 * @return
	 */
	OrderReturnVO getOrderReturnInfoById(Long id);
	
	/**
	 * 更新退款信息
	 * @param vo
	 */
	void updateOrderReturnInfo(OrderReturnVO vo);
	
	/**
	 * 更新订单信息和退款申请信息
	 * @param vo
	 */
	void updateOrderAndRefundInfo(RefundResultVO vo);
	
	
	

	
	

	
	

}
