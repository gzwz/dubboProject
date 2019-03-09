package test.product;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductSpecificationService;
import cn.qumiandan.product.vo.SpecificationVO;
import test.BaseTest;

public class ProductSpecificationServiceTest extends BaseTest {

	@Autowired
	private IProductSpecificationService service;
	@Test
	public void add() throws QmdException {
		SpecificationVO param = new SpecificationVO();
		param.setCostPrice(BigDecimal.valueOf(12));
		param.setMarketPrice(BigDecimal.valueOf(3));
		param.setName("大杯");
		param.setPrice(BigDecimal.valueOf(10));
		param.setProductId(2441234L);
		param.setStock(100);
		param.setUnits("份");
		param.setValue("1");
		
		service.addSpecification(param );
	}
	
	@Test
	public void query() {
		SpecificationVO aaa = new SpecificationVO();
		aaa.setProductId(123L);
		List<SpecificationVO> list = service.querySpecification(aaa );
		System.out.println(list);
		aaa.setProductId(null);
		aaa.setName("大杯");
		list = service.querySpecification(aaa );
		System.out.println(list);
		
	}
}
