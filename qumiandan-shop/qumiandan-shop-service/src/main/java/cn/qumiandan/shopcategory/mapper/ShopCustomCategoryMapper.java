package cn.qumiandan.shopcategory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shopcategory.entity.ShopCustomCategory;
import cn.qumiandan.shopcategory.vo.ShopCustomCategoryVO;

@Mapper
public interface ShopCustomCategoryMapper extends BaseMapper<ShopCustomCategory>{

    /**
     * 添加自定义分类
     * @param shopCustomCategoryVO
     * @return int
     */
//    int addShopCustomCategory(ShopCustomCategoryVO shopCustomCategoryVO);

    /**
     * 更新自定义分类
     * @param shopCustomCategoryVO
     * @return int
     */
//    int updateShopCustomCategory(ShopCustomCategoryVO shopCustomCategoryVO);


    /**
     * 根据店铺Id获取自定义分类列表（按排序数排序）
     * @param shopId
     * @return
     */
    List<ShopCustomCategoryVO> getCustomCategoryByShopId(Long shopId);

    /**
     * 根据店铺id和自定义分类名查询自定义分类信息
     * @param shopId
     * @param name
     * @return
     */
    ShopCustomCategory selectByNameAndShopId(@Param("shopId")Long shopId,@Param("name")String name);
}