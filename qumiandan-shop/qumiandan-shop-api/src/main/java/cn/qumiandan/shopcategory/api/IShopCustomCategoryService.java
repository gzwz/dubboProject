package cn.qumiandan.shopcategory.api;

import java.util.List;

import cn.qumiandan.shopcategory.vo.ShopCustomCategoryVO;

public interface IShopCustomCategoryService {
	/**
     * 添加自定义分类
     * @param shopCustomCategoryVO
     * @return int 
     */
    int addShopCustomCategory(ShopCustomCategoryVO shopCustomCategoryVO); 
    
    /**
     * 更新自定义分类
     * @param shopCustomCategoryVO
     * @return int
     */
    int updateShopCustomCategory(ShopCustomCategoryVO shopCustomCategoryVO); 
    
    /**
     * 删除自定义分类
     * @param id
     * @return int
     */
    int deleteShopCustomCategoryById(Long id);

    /**
     * 根据id查询店铺自定义分类信息
     * @param id
     * @return
     */
    ShopCustomCategoryVO getShopCustomCategoryById(Long id);
    
    /**
     * 根据店铺Id获取自定义分类列表（按排序数排序）
     * @param shopId
     * @return
     */
    List<ShopCustomCategoryVO> getCustomCategoryListByShopId(Long shopId);
}
