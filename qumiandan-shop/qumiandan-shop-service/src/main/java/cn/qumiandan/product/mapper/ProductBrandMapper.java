package cn.qumiandan.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.product.entity.ProductBrand;
import cn.qumiandan.product.vo.ProductBrandVO;

@Mapper
public interface ProductBrandMapper extends BaseMapper<ProductBrand>{
    
    List <ProductBrandVO> selectAllProductBrand();
    
    int getMaxSort();
}