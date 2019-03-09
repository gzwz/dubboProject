package test.shopcategory;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.shopcategory.api.IShopCategoryService;
import cn.qumiandan.shopcategory.vo.ShopCategoryPageListVO;
import cn.qumiandan.shopcategory.vo.ShopCategoryVO;
import test.BaseTest;

/**
 * @description：店铺分类接口测试类
 * @author：zhuyangyong
 * @date: 2018/11/8 15:33
 */
public class ShopCategoryTest extends BaseTest {

    @Resource
    private IShopCategoryService shopCategoryService;

    @Test
    public void getAllShopCategoryList() throws Exception{
        System.out.println("---shopCategoryService.getAllShopCategoryPageList()---");
        System.out.println(shopCategoryService.getAllShopCategoryList());
    }

    @Test
    public void deleteShopCategoryById() throws Exception{
        System.out.println("---shopCategoryService.deleteShopCategoryById()---");
        System.out.println(shopCategoryService.deleteShopCategoryById(2L));
    }
    @Test
    public void getShopCategoryById() throws Exception{
    	System.out.println("---getShopCategoryById---");
    	System.out.println(shopCategoryService.getShopCategoryById(new Long(2L)).toString());
    }

    @Test
    public void addShopCategory() throws Exception {
    	System.out.println("---addShopCategory---");
        ShopCategoryVO vo = new ShopCategoryVO();
        vo.setName("test");
        vo.setParentId(0L);
    	System.out.println(shopCategoryService.addShopCategory(vo));
    }

    @Test
    public void updateShopCategoryById() throws Exception {
    	System.out.println("---updateShopCategoryById---");
        ShopCategoryVO vo = new ShopCategoryVO();
        vo.setId(2L);
        vo.setName("test123");
    	System.out.println(shopCategoryService.updateShopCategoryById(vo));
    }

    @Test
    public void getShopCategoryPageListByParentId() throws Exception {
    	System.out.println("---getShopCategoryPageListByParentId---");
        ShopCategoryPageListVO shopCategoryPageListVO = new ShopCategoryPageListVO();
        shopCategoryPageListVO.setPageNum(1);
        shopCategoryPageListVO.setPageSize(5);
    	PageInfo<ShopCategoryVO> list=shopCategoryService.getShopCategoryPageListByParentId(shopCategoryPageListVO);
    	System.out.println(list);
    }
}
