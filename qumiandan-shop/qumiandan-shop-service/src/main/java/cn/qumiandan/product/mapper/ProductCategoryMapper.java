package cn.qumiandan.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.product.entity.ProductCategory;
import cn.qumiandan.product.vo.ProductCategoryVO;

@Mapper
public interface ProductCategoryMapper  extends BaseMapper<ProductCategory>{
    
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
     * 获取一级的最大排序数
     * @return
     */
    Integer getMaxlevelOneSort();
    
    /**
     * 获取二级的最大排序数
     * @return
     */
    Integer getMaxlevelTwoSort();
    
    /**
     * 根据分类名查询分类信息
     * @param name
     * @return
     */
    ProductCategoryVO getProductCategoryByName(String name);
}