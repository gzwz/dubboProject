package cn.qumiandan.shopcategory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.shopcategory.entity.ShopCategory;
import cn.qumiandan.shopcategory.vo.ShopCategoryVO;

@Mapper
public interface ShopCategoryMapper extends BaseMapper<ShopCategory> {

    List<ShopCategoryVO> getFistShopCategoryList(Long parentId);

    List<ShopCategoryVO> getSecondShopCategoryList();

    List<ShopCategoryVO> getShopCategoryListByParentId(Long parentId);

    /**
     * 获取最大排序
     * @return
     */
    Integer getMaxSort();

    /**
     * 根据分类名获取分类信息
     * @param name
     * @return
     */
    ShopCategory selectByName(String name);
}