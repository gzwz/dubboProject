package cn.qumiandan.product.api;

import java.util.List;

import cn.qumiandan.product.vo.ProductLogVO;

/**
 * 商品日志管理接口
 * @author lrj
 *
 */
public interface IProductLogService {

	/**
	 * 添加商品日志信息
	 * @param productLogVO
	 * @return
	 */
	int addProductLog(ProductLogVO productLogVO);
	
	/**
	 * 根据主键删除商品日志信息
	 * @param id
	 * @return
	 */
	int deleteProductLog(Long id);
	
	/**
	 * 根据主键查询商品日志信息
	 * @param id
	 * @return
	 */
	ProductLogVO getProductLogById(Long id);
	
	
	/**
	 * 
	 * 根据商品id查询日志信息
	 * @param productId
	 * @return
	 */
	List<ProductLogVO> getProductLogByProductId(Long productId);
	
}
