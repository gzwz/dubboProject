package cn.qumiandan.product.api;

import java.util.List;

import cn.qumiandan.product.vo.ProductBrandVO;

/**
 * 商品品牌管理接口
 * @author lrj
 *
 */
public interface IProductBrandService {
	
	/**
	 * 添加商品品牌
	 * @param productBrandVO
	 * @return
	 */
	int addProductBrand(ProductBrandVO productBrandVO);
	
	/**
	 * 更新商品品牌
	 * @param productBrandVO
	 * @return
	 */
	int updateProductBrand(ProductBrandVO productBrandVO);
	
	/**
	 * 根据主键删除商品品牌（逻辑删除）
	 * @param id
	 * @return
	 */
	int deleteProductBrandById(Long id);
	
	/**
	 * 根据主键查询商品品牌信息
	 * @return
	 */
	ProductBrandVO getProductBrandById(Long id);
	
	/**
	 * 查询所有品牌信息
	 * @return
	 */
	List<ProductBrandVO> getAllProductBrand();
	
	
}
