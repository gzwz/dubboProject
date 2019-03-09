package test.shopcategory;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.shopcategory.api.IShopCustomCategoryService;
import cn.qumiandan.shopcategory.vo.ShopCustomCategoryVO;
import test.BaseTest;

/**
 * @description：店铺自定义分类接口测试类
 * @author：zhuyangyong
 * @date: 2018/11/8 15:33
 */
public class ShopCustomCategoryTest extends BaseTest {

    @Resource
    private IShopCustomCategoryService shopCustomCategoryService;

    @Test
    public void addShopCustomCategory() throws Exception {
    	System.out.println("---addShopCustomCategory---");
    	ShopCustomCategoryVO categoryVO=new ShopCustomCategoryVO();
    	categoryVO.setName("测试自定义分类53");
    	categoryVO.setShopId(1l);
    	System.out.println(shopCustomCategoryService.addShopCustomCategory(categoryVO));
    }
    
    @Test
    public void updateShopCustomCategory() throws Exception {
    	System.out.println("---updateShopCustomCategory---");
    	ShopCustomCategoryVO categoryVO=new ShopCustomCategoryVO();
    	categoryVO.setId(3l);
    	categoryVO.setName("测试分类66");
    	categoryVO.setShopId(1L);
    	System.out.println(shopCustomCategoryService.updateShopCustomCategory(categoryVO));
    }
    
    @Test
    public void deleteShopCustomCategoryById() throws Exception {
    	System.out.println("---deleteShopCustomCategoryById---");
    	System.out.println(shopCustomCategoryService.deleteShopCustomCategoryById(3L));
    }
    
    @Test
    public void getCustomCategoryByShopId() throws Exception {
    	System.out.println("---getCustomCategoryByShopId---");
    	List<ShopCustomCategoryVO> list=shopCustomCategoryService.getCustomCategoryListByShopId(3l);
    	System.out.println(list);
    }
    
    @Test
    public void getShopCustomCategoryById() throws Exception {
    	System.out.println("---getShopCustomCategoryById- ---");
    	System.out.println(shopCustomCategoryService.getShopCustomCategoryById(3l));
    }
}
