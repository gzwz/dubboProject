package cn.qumiandan.product.api;

import java.util.List;

import cn.qumiandan.product.vo.SpecificationVO;

/**
 * 商品规格增删改查接口
 * @author WLZ
 * 2018年11月21日
 */
public interface IProductSpecificationService {

	/**添加商品规格属性*/
	void addSpecification(SpecificationVO param);
	
	/**查询商品规格属性*/
	List<SpecificationVO> querySpecification(SpecificationVO param);
	
	/**添加商品规格属性*/
	void deleteSpecification(String id);
}
