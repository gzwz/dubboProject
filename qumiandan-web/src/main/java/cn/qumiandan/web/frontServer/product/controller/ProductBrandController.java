package cn.qumiandan.web.frontServer.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.product.api.IProductBrandService;
import cn.qumiandan.product.vo.ProductBrandVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.product.entity.request.AddProductBrandParams;
import cn.qumiandan.web.frontServer.product.entity.request.UpdateProductBrandParams;
import cn.qumiandan.web.frontServer.role.entity.request.GetIdParams;

/**
 * 商品品牌控制器
 * @author lrj
 *
 */

@RestController
@RequestMapping("/productBrand/")
public class ProductBrandController {
	
	@Reference()
	private IProductBrandService productBrandService ;

	/**
	 * 添加商品品牌
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addProductBrand")
	public Result<Integer> addProductBrand(@Valid @RequestBody(required = false) AddProductBrandParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ProductBrandVO productBrandVO = new ProductBrandVO();
		BeanUtils.copyProperties(productBrandVO, params);
		productBrandService.addProductBrand(productBrandVO);		
		return ResultUtils.success("添加商品品牌成功");
	}
	
	/**
	 * 更新商品品牌
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateProductBrand")
	public Result<Integer> updateProductBrand(@Valid @RequestBody(required = false) UpdateProductBrandParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ProductBrandVO productBrandVO = new ProductBrandVO();
		BeanUtils.copyProperties(productBrandVO, params);
		productBrandService.updateProductBrand(productBrandVO);		
		return ResultUtils.success("更新商品品牌成功");
	}
	
	
	
	/**
	 * 删除商品品牌
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException 
	 */
	@RequestMapping("deleteProductBrandById")
	public Result<Integer> deleteProductBrandById(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		productBrandService.deleteProductBrandById(params.getId());		
		return ResultUtils.success("删除商品品牌成功");
	}
	
	
	/**
	 * 根据主键查询商品品牌信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getProductBrandById")
	public Result<ProductBrandVO> getProductBrandById(@Valid @RequestBody(required = false) GetIdParams params,  BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ProductBrandVO productBrandVO = productBrandService.getProductBrandById(params.getId());
		Result<ProductBrandVO> result =ResultUtils.success();
		result.setData(productBrandVO);
		return result;
	}
	
	/**
	 * 
	 * 查询所有品牌信息
	 * @return
	 */
	@RequestMapping("getAllProductBrand")
	public Result<List<ProductBrandVO>> getAllProductBrand(){
		List<ProductBrandVO> productBrandVOList = productBrandService.getAllProductBrand();
		Result<List<ProductBrandVO>> result =ResultUtils.success();
		result.setData(productBrandVOList);
		return result;
	}
}
