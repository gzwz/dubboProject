package cn.qumiandan.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.order.entity.Order;
import cn.qumiandan.order.vo.OrderVO;

@Mapper
public interface OrderMapper extends BaseMapper<Order>{
    int deleteByPrimaryKey(String orderId);

    Order selectByPrimaryKey(String orderId);

    OrderVO selectOrderById(String orderId);

}