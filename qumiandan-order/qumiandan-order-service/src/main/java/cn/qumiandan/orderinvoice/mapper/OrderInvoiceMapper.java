package cn.qumiandan.orderinvoice.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.qumiandan.orderinvoice.entity.OrderInvoice;
@Mapper
public interface OrderInvoiceMapper {
    int insertSelective(OrderInvoice record);

    OrderInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInvoice record);
    
    /**
	 * 根据订单号查询发票详情
	 * @param orderId
	 * @return
	 */
    OrderInvoice getOrderInvoiceOrderId(String orderId);
}