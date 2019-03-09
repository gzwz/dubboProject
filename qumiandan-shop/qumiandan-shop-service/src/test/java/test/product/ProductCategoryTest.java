package test.product;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductCategoryService;
import cn.qumiandan.product.vo.ProductCategoryVO;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

public class ProductCategoryTest extends BaseTest{
	@Resource
	private IProductCategoryService productCategoryService;
	
	@Test
	public void addProductCategory() throws QmdException {
		System.out.println("------------addProductCategory------------------");
		ProductCategoryVO productCategoryVO = new ProductCategoryVO();
		productCategoryVO.setName("商品分类");
		productCategoryVO.setLevel(ToolUtil.intToByte(1));
		System.out.println(productCategoryService.addProductCategory(productCategoryVO));
	}
	
	@Test
	public void updateProductCategory() throws QmdException {
		System.out.println("--------------updateProductCategory----------------");
		ProductCategoryVO productCategoryVO = new ProductCategoryVO();
		productCategoryVO.setId(2L);
		productCategoryVO.setName("商品分类");
		productCategoryVO.setLevel(ToolUtil.intToByte(1));
		System.out.println(productCategoryService.updateProductCategory(productCategoryVO));
	}
	
	@Test
	public void deleteProductCategoryById() {
		System.out.println("------------deleteProductCategoryById------------------");
		System.out.println(productCategoryService.deleteProductCategoryById(2L));

	}
	
	@Test
	public void getLevelOneProductCategory() {
		System.out.println("--------------getLevelOneProductCategory----------------");
		System.out.println(productCategoryService.getLevelOneProductCategory());

	}
	
	@Test
	public void getLevelTwoProductCategory() {
		System.out.println("-------------getLevelTwoProductCategory-----------------");
		System.out.println(productCategoryService.getLevelTwoProductCategory(1L));
	}
	
	@Test
	public void getProductCategoryById() {
		System.out.println("---------------getProductCategoryById---------------");
		System.out.println(productCategoryService.getProductCategoryById(1L));

	}
}
