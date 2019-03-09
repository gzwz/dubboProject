package cn.qumiandan.product.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.qumiandan.product.entity.ProductExtend;

@Mapper
public interface ProductExtendMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ProductExtend record);

    ProductExtend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductExtend record);

    int updateByProductIdSelective(ProductExtend record);

    ProductExtend selectByProductId(Long productId);
}