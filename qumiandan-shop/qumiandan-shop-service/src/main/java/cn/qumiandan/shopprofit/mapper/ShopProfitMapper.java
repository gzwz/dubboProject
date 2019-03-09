package cn.qumiandan.shopprofit.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shopprofit.entity.ShopProfit;
@Mapper
public interface ShopProfitMapper extends BaseMapper<ShopProfit> {


//    ShopProfit selectByPrimaryKey(Long id);
//
//    /**
//     * 根据店铺id查询店铺分润信息
//     * @param shopId
//     * @return
//     */
//    ShopProfit getShopProfitByShopId(Long shopId);
}