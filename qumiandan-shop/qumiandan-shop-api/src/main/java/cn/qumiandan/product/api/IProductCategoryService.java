package cn.qumiandan.product.api;


import java.util.List;

import cn.qumiandan.product.vo.ProductCategoryVO;

/**
 * 商品分类接口
 * @author lrj
 *
 */
public interface IProductCategoryService {

    /**
     * 添加商品分类
     * @param productCategoryVO
     * @return
     */
    int addProductCategory(ProductCategoryVO productCategoryVO);

    /**
     * 修改商品分类
     * @param productCategoryVO
     * @return
     */
    int updateProductCategory(ProductCategoryVO productCategoryVO);
    
    /**
     * 根据主键删除商品分类
     * @param id
     * @return
     */
    int deleteProductCategoryById(Long id);
    
    /**
     * 查询一级分类
     * @return
     */
    List<ProductCategoryVO> getLevelOneProductCategory();
    
    /**
     * 根据一级分类查询二级分类
     * @param id
     * @return
     */
    List<ProductCategoryVO> getLevelTwoProductCategory(Long id);
    
    /**
     * 根据主键查询分类详情
     * @param id
     * @return
     */
    ProductCategoryVO getProductCategoryById(Long id);

}
