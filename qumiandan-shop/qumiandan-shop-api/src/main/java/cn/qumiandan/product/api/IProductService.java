package cn.qumiandan.product.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.product.vo.ProductAndShopVO;
import cn.qumiandan.product.vo.ProductBasicVO;
import cn.qumiandan.product.vo.ProductDetailVO;
import cn.qumiandan.product.vo.ProductUpdateVO;
import cn.qumiandan.product.vo.ProductVO;
import cn.qumiandan.product.vo.ShopProductListVO;
import cn.qumiandan.product.vo.ShopProductQueryVO;

public interface IProductService {

    /**
     * 添加商品信息
     * @param productVO
     * @return
     */
    int addProduct(ProductVO productVO);

    /**
     * 更新商品信息
     * @param productUpdateVO
     * @return
     */
    void updateProductById(ProductUpdateVO productUpdateVO);

    /**
     * 商品上下架
     * @param productId
     * @param shopId
     * @param status
     * @return
     * @throws SHException
     */
    int setIsShelf(Long productId ,Long shopId,Byte status ) ;

    /**
     * 根据商品Id获取商品详情
     * @param id
     * @return
     */
    ProductDetailVO getProductDetailById(Long id);

    /**
     *  根据店铺id获取商品分页列表
     * @param shopProductQueryVO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ShopProductListVO> getProductPageListByShopId(ShopProductQueryVO shopProductQueryVO,int pageNum,int pageSize);

    /**
     * 根据店铺id获取商品列表，按自定义(菜单)分类分组
     * @param shopId
     * @return
     */
    List<Map<String,Object>> getCustomProductListByShopId(Long shopId);

    /**
     * 根据id查询商品基本信息
     * @param id
     * @return
     */
    ProductBasicVO getProductBasicById(Long id);
    
    /**
     * 商品审核
     * @param id
     * @param status
     * @return
     */
    int auditProduct(Long id,Byte status);
    
    /**
     * 根据商品id集合查询商品详情
     * @param idSet
     * @return
     */
    List<ProductDetailVO> getProductByProductIdSet(Set<Long> idSet);
    
    PageInfo<ProductAndShopVO> queryProduct(ShopProductQueryVO productQueryVO);
    
    /**
     * 删除商品
     * @param productId
     * @param shopId
     */
    void deleteProduct(Long productId ); 
}
