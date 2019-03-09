package cn.qumiandan.deliveryaddress.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.deliveryaddress.entity.DeliveryAddress;
@Mapper
public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress>{

    /**
     * 根据用户id获取收货地址
     * @param userId
     * @return
     */
    List<DeliveryAddress> getDeliveryAddressByUserId(Long userId);
    
    /**
     * 设置用户收货地址为非默认地址
     * @param userId
     * @return
     */
    int setDefaultAddress(Long userId);
}