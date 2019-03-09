package cn.qumiandan.product.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.product.entity.Product;
import cn.qumiandan.product.vo.CustomCategoryVO;
import cn.qumiandan.product.vo.ProductAndShopVO;
import cn.qumiandan.product.vo.ProductBasicInfoVO;
import cn.qumiandan.product.vo.ProductDetailVO;
import cn.qumiandan.product.vo.ShopProductListVO;
import cn.qumiandan.product.vo.ShopProductQueryVO;

@Mapper
public interface ProductMapper extends BaseMapper<Product>{

   // int insertSelective(Product record);

   // Product selectByPrimaryKey(Long id);

   // int updateByPrimaryKeySelective(Product record);
    
    int setIsShelf(@Param("productId")Long productId,@Param("status")Byte status);

    List<ShopProductListVO> getProductPageListByShopId(ShopProductQueryVO shopProductQueryVO);

    /**
     * 根据商品id查询商品详情
     * @param id
     * @return
     */
    ProductDetailVO getProductDetailById(Long id);

    /**
     * 根据店铺id获取商品列表
     * @param shopId
     * @return
     */
    List<ProductBasicInfoVO> getCustomProductListByShopId(Long shopId);

    /**
     * 根据店铺id获取商品列表
     * @param shopId
     * @return
     */
    List<CustomCategoryVO> getCustomCategoryListByShopId(Long shopId);
    
    /**
     * 根据商品id集合查询商品详情
     * @param idSet
     * @return
     */
    List<ProductDetailVO> getProductByProductIdSet(@Param("idSet")Set<Long> idSet);

    /**
     * 总后台查询商品
     * @param productQueryVO
     * @return
     */
    List<ProductAndShopVO> queryProduct(@Param("productQueryVO")ShopProductQueryVO productQueryVO);
}