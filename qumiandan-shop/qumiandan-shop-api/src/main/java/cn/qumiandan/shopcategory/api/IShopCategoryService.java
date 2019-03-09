package cn.qumiandan.shopcategory.api;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.shopcategory.vo.ShopCategoryPageListVO;
import cn.qumiandan.shopcategory.vo.ShopCategoryVO;

public interface IShopCategoryService {

    /**
     * 创建分类
     * @param cagegoryVO
     * @return
     */
    int addShopCategory(ShopCategoryVO cagegoryVO);

    /**
     * 修改分类
     * @param cagegoryVO
     * @return
     */
    int updateShopCategoryById(ShopCategoryVO cagegoryVO);

    /**
     * 根据id删除店铺分类
     * @param id
     * @return
     */
    int deleteShopCategoryById(Long id);

    /**
     * 根据id获取店铺分类详情
     * @param id
     * @return
     */
    ShopCategoryVO getShopCategoryById(Long id) ;

    /**
     * 根据一级分类id获取店铺分类分页列表
     * @param shopCategoryPageListVO
     * @return
     */
    PageInfo<ShopCategoryVO> getShopCategoryPageListByParentId(ShopCategoryPageListVO shopCategoryPageListVO) ;

    /**
     * 获取店铺分类所有数据列表
     * @return
     */
    List<Map<String,Object>> getAllShopCategoryList() ;

}
