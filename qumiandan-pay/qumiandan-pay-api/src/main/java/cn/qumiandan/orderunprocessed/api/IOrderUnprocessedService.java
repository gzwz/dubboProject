package cn.qumiandan.orderunprocessed.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.orderunprocessed.vo.OrderUnprocessedVO;

/**
 * 未处理成功的订单接口类
 * @author lrj
 *
 */
public interface IOrderUnprocessedService {

	/**
	 * 添加未处理成功的订单信息
	 * @param orderUnprocessedVO
	 * @return
	 */
	OrderUnprocessedVO addOrderUnprocessed(OrderUnprocessedVO orderUnprocessedVO);

	/**
	 * 修改未处理成功的订单信息
	 * @param orderUnprocessedVO
	 * @return
	 */
	void updateOrderUnprocessedById(OrderUnprocessedVO orderUnprocessedVO);

	/**
	 * 删除未处理成功的订单信息
	 * @param id
	 */
	void deleteOrderUnprocessedById(Long id);
	
	/**
	 * 根据id查询未处理成功的订单信息
	 * @param id
	 * @return
	 */
	OrderUnprocessedVO getOrderUnprocessedById(Long id);
	
	/**
	 * 分页查询未处理成功的订单信息
	 * @param commonParams
	 * @return
	 */
	PageInfo<OrderUnprocessedVO> queryOrderUnprocessed(CommonParams commonParams);
}
