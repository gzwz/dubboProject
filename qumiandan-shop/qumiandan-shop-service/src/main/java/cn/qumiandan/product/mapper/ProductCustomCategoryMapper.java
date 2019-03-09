package cn.qumiandan.product.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.qumiandan.product.entity.ProductCustomCategory;
import cn.qumiandan.product.vo.ProductCustomCategoryVO;

@Mapper
public interface ProductCustomCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ProductCustomCategory record);

    ProductCustomCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCustomCategory record);

    ProductCustomCategory selectByProductId(Long productId);

    ProductCustomCategoryVO selectCustomCategoryVOByProductId(Long productId);
}