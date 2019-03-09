package cn.qumiandan.order.mapper;

import cn.qumiandan.order.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail>{
    int deleteByPrimaryKey(Long id);

    //int insert(OrderDetail record);

    //int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    /**
     * 根据订单编号查询订单商品详情列表
     * @param orderId
     * @return
     */
    List<OrderDetail> getOrderDetailListByOrderId(String orderId);
}