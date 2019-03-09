package test.product;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductBrandService;
import cn.qumiandan.product.vo.ProductBrandVO;
import test.BaseTest;

public class ProductBrandTest extends BaseTest {

	@Autowired
	private IProductBrandService productBrandService;
	
	@Test
	public void addProductBrand() throws QmdException {
		System.out.println("---addProductBrand----");
		ProductBrandVO productBrandVO = new ProductBrandVO();
		productBrandVO.setName("测试");
		System.out.println(productBrandService.addProductBrand(productBrandVO));
	}

	@Test
	public void updateProductBrand() throws QmdException {
		System.out.println("---addProductBrand----");
		ProductBrandVO productBrandVO = new ProductBrandVO();
		productBrandVO.setName("测试");
		productBrandVO.setId(1L);
		System.out.println(productBrandService.updateProductBrand(productBrandVO));
	}
	
	
	@Test
	public void deleteProductBrandById() throws QmdException {
		System.out.println("---deleteProductBrandById----");
		System.out.println(productBrandService.deleteProductBrandById(1L));
	}
	
	
	@Test
	public void getProductBrandById() throws QmdException {
		System.out.println("---getProductBrandById----");
		System.out.println(productBrandService.getProductBrandById(1L));
	}
	
	@Test
	public void getAllProductBrandBy() throws QmdException {
		System.out.println("---getProductBrandById----");
		System.out.println(productBrandService.getAllProductBrand());
	}
}

