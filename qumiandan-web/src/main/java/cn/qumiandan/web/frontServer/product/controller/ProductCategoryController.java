package cn.qumiandan.web.frontServer.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.product.api.IProductCategoryService;
import cn.qumiandan.product.vo.ProductCategoryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.product.entity.request.AddProductCategoryParams;
import cn.qumiandan.web.frontServer.product.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.product.entity.request.UpdateProductCategoryParams;

/**
 * 商品分类管理
 * @author lrj
 *
 */
@RestController
@RequestMapping("/productCategory/")
public class ProductCategoryController {
	@Reference()
	private IProductCategoryService productCategoryService;
	
	/**
	 * 添加商品分类
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("addProductCategory")
	public Result<Integer> addProductCategory(@Valid @RequestBody(required = false) AddProductCategoryParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ProductCategoryVO productCategoryVO =
		CopyBeanUtil.copyBean(params, ProductCategoryVO.class);
		productCategoryService.addProductCategory(productCategoryVO);
		return ResultUtils.success("添加商品分类成功");
	}
	
	/**
	 * 更新商品分类
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("updateProductCategory")
	public Result<Integer> updateProductCategory(@Valid @RequestBody(required = false) UpdateProductCategoryParams params, BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ProductCategoryVO productCategoryVO =
		CopyBeanUtil.copyBean(params, ProductCategoryVO.class);
		productCategoryService.updateProductCategory(productCategoryVO);
		return ResultUtils.success("更新商品分类成功");
	}
	
	/**
	 * 删除商品分类
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("deleteProductCategoryById")
	public Result<Integer> deleteProductCategoryById(@Valid @RequestBody(required = false) GetIdParams params, BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
//		ProductCategoryVO productCategoryVO =
//		CopyBeanUtil.copyBean(params, ProductCategoryVO.class);
		productCategoryService.deleteProductCategoryById(params.getId());
		return ResultUtils.success("删除商品分类成功");
	}
	
	/**
	 * 查询一级分类
	 * @return
	 */
	@RequestMapping("getLevelOneProductCategory")
	public Result<List<ProductCategoryVO>> getLevelOneProductCategory(){
		List<ProductCategoryVO> productCategoryVOList = productCategoryService.getLevelOneProductCategory();
		Result<List<ProductCategoryVO>> result = ResultUtils.success();
		result.setData(productCategoryVOList);
		return result;
	}
	
	/**
	 * 根据一级分类id查询二级分类
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("getLevelTwoProductCategory")
	public Result< List<ProductCategoryVO>> getLevelTwoProductCategory(@Valid @RequestBody(required = false) GetIdParams params, BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		 List<ProductCategoryVO> list = productCategoryService.getLevelTwoProductCategory(params.getId());
		 Result< List<ProductCategoryVO>>  result = ResultUtils.success();
		 result.setData(list);
		 return result;
	}
	
	/**
	 * 根据id查询分类详情
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException
	 */
	@RequestMapping("getProductCategoryById")
	public Result<ProductCategoryVO> getProductCategoryById(@Valid @RequestBody(required = false) GetIdParams params, BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		 ProductCategoryVO  productCategoryVO= productCategoryService.getProductCategoryById(params.getId());
		 Result< ProductCategoryVO>  result = ResultUtils.success();
		 result.setData(productCategoryVO);
		 return result;
	}
}
