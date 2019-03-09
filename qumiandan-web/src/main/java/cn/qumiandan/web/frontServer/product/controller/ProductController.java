package cn.qumiandan.web.frontServer.product.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.annotation.ValidateShopManager;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.product.api.IProductService;
import cn.qumiandan.product.vo.ProductAndShopVO;
import cn.qumiandan.product.vo.ProductDetailVO;
import cn.qumiandan.product.vo.ProductUpdateVO;
import cn.qumiandan.product.vo.ProductVO;
import cn.qumiandan.product.vo.ShopProductListVO;
import cn.qumiandan.product.vo.ShopProductQueryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.product.entity.request.AddProductParams;
import cn.qumiandan.web.frontServer.product.entity.request.AuditProductParams;
import cn.qumiandan.web.frontServer.product.entity.request.CustomProductListParams;
import cn.qumiandan.web.frontServer.product.entity.request.DeleteProductParams;
import cn.qumiandan.web.frontServer.product.entity.request.ProductDetailParams;
import cn.qumiandan.web.frontServer.product.entity.request.QueryProductParams;
import cn.qumiandan.web.frontServer.product.entity.request.SetIsShelfParams;
import cn.qumiandan.web.frontServer.product.entity.request.ShopProductListParams;
import cn.qumiandan.web.frontServer.product.entity.request.UpdateProductParams;

/**
 * 商品管理
 * @author lrj
 * @version 创建时间：2018年11月13日 10:30
 */
@RestController
@RequestMapping("/product/")
public class ProductController {
	
	@Reference()
	private IProductService  productService;
	
	/**
	 * 添加商品信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws SHException 
	 */
	@RequestMapping("addProduct")
	public Result<Integer> addProduct(@Valid @RequestBody(required = false) AddProductParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		productService.addProduct(CopyBeanUtil.copyBean(params, ProductVO.class));	
		return ResultUtils.success("添加商品成功");
	}

	/**
	 * 商品上下架
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws SHException 
	 */
	@RequestMapping("setIsShelf")
	public Result<Integer> setIsShelf(@Valid @RequestBody(required = false) SetIsShelfParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		productService.setIsShelf(params.getProductId(),params.getShopId(),params.getStatus());
		return ResultUtils.success("成功");
	}

	/**
	 * 根据id查询商品详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getProductDetailById")
	public Result<ProductDetailVO> getProductDetailById(@Valid @RequestBody(required=false) ProductDetailParams params, BindingResult bindingResult ) {
		if(bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ProductDetailVO productDetailVO = productService.getProductDetailById(params.getId());
		Result<ProductDetailVO> result =ResultUtils.success();
		result.setData(productDetailVO);
		return result;
	}

	/**
	 * 更新商品信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateProductById")
	public Result<Integer> updateProductById(@Valid @RequestBody(required = false) UpdateProductParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ProductUpdateVO productUpdateVO =
		CopyBeanUtil.copyBean(params,ProductUpdateVO.class);
		productService.updateProductById(productUpdateVO);
		return ResultUtils.success("更新商品成功");
	}
	
    /**
     * 根据店铺id获取商品分页列表
     * @param params
     * @return
     */
	@RequestMapping("getProductPageListByShopId")
	public Result<PageInfo<ShopProductListVO>> getProductPageListByShopId(@Valid @RequestBody(required = false) ShopProductListParams params, BindingResult bindingResult ){
		if(bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopProductQueryVO shopProductQueryVO = 
		CopyBeanUtil.copyBean(params,ShopProductQueryVO.class);
		PageInfo<ShopProductListVO> pageInfo = productService.getProductPageListByShopId(shopProductQueryVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopProductListVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
		
	}


	/**
	 * 根据店铺id获取商品列表，按自定义分类(菜单)分组
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws SHException
	 */
	@RequestMapping("getCusProductListByShopId")
	public Result<List<Map<String,Object>>> getCustomProductListByShopId(@Valid @RequestBody(required = false)CustomProductListParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		List<Map<String,Object>> customProductList = productService.getCustomProductListByShopId(params.getShopId());
		//根据用户id查询店铺id集合
		Result<List<Map<String,Object>>> result = ResultUtils.success();
		result.setData(customProductList);
		return result;
	}


	/**
	 * 商品审核
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("auditProduct")
	public Result<Boolean> auditProduct(@Valid @RequestBody(required = false) AuditProductParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		productService.auditProduct(params.getProductId(),params.getStatus());
		Result<Boolean> result = ResultUtils.success();
		if(params.getStatus()==1) {
			result.setMessage("审核完成；审核结果：审核通过");
			result.setData(true);
		}else {
			result.setMessage("审核完成；审核结果：审核未通过");
			result.setData(false);
		}
		return result;
	}
	
	/**
	 * 商品审核
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryProduct")
	public Result<PageInfo<ProductAndShopVO>> queryProduct(@Valid @RequestBody(required = false) QueryProductParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopProductQueryVO productQueryVO = CopyBeanUtil.copyBean(params, ShopProductQueryVO.class);
		PageInfo<ProductAndShopVO> pageInfo = productService.queryProduct(productQueryVO);
		return ResultUtils.success(pageInfo);
	}
	
	/**
	 * 删除商品
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@ValidateShopManager
	@RequestMapping("deleteProduct")
	public Result<PageInfo<ProductAndShopVO>> deleteProduct(@Valid @RequestBody(required = false) DeleteProductParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		productService.deleteProduct(params.getProductId());
		return ResultUtils.success("删除商品成功");
	}
}
