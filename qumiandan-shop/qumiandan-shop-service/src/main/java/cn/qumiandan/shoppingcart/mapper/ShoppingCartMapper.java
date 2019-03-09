package cn.qumiandan.shoppingcart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.product.vo.ProductBasicVO;
import cn.qumiandan.shoppingcart.entity.ShoppingCart;
import cn.qumiandan.shoppingcart.vo.ShopingCartDetailVO;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart>{
	    
    /**
     * 根据用户id和商品id查询商品信息
     * @param userId
     * @param productId
     * @return
     */
    ShoppingCart getProductByUserIdAndProductId(@Param("userId")Long userId,@Param("productId")Long productId);
    
    /**
     * 根据用户id获取购物车表里的相关的店铺信息
     * @param userId
     * @return
     */
    List<ShopingCartDetailVO> getShopListByUserId(Long userId);
    
    /**
     * 根据用户id查询购物车商品
     * @param userId
     * @return
     */
    List<ProductBasicVO> getProductByUserId(@Param("userId")Long userId);
}