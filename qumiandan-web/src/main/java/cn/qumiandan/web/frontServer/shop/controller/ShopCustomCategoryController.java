package cn.qumiandan.web.frontServer.shop.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shopcategory.api.IShopCustomCategoryService;
import cn.qumiandan.shopcategory.vo.ShopCustomCategoryVO;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shop.entity.request.AddShopCusCateParams;
import cn.qumiandan.web.frontServer.shop.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.shop.entity.request.UpdateShopCusCateParams;

/**
 * 店铺分类管理
 * @author lrj
 * @version 创建时间：2018年11月13日 9:52
 */

@RestController
@RequestMapping("/shopCustomCategory/")
public class ShopCustomCategoryController {
	@Reference()
	private IShopCustomCategoryService  customCategoryService;
	
	/**
	 * 添加店铺自定义分类信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addShopCustomCategory")
	public Result<Integer> addShopCustomCategory(@Valid @RequestBody(required = false) AddShopCusCateParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopCustomCategoryVO shopCusCategoryVO = new ShopCustomCategoryVO();
		BeanUtils.copyProperties(shopCusCategoryVO, params);
		customCategoryService.addShopCustomCategory(shopCusCategoryVO);	
		return ResultUtils.success("添加店铺自定义分类成功");
	}
	
	
	/**
	 * 更新店铺自定义分类信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateShopCustomCategory")
	public Result<Integer> updateShopCustomCategory(@Valid @RequestBody(required = false) UpdateShopCusCateParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopCustomCategoryVO shopCusCategoryVO=new ShopCustomCategoryVO();
		BeanUtils.copyProperties(shopCusCategoryVO, params);
		customCategoryService.updateShopCustomCategory(shopCusCategoryVO);		
		return ResultUtils.success("更新店铺自定义分类成功");
	}
	
	/**
     * 删除自定义分类
     * @param params
     * @return int
     */
	@RequestMapping("deleteShopCustomCategoryById")
	public Result<Integer> deleteShopCustomCategoryById(@Valid @RequestBody(required = false)GetIdParams params,BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}  
		customCategoryService.deleteShopCustomCategoryById(params.getId());		
		return ResultUtils.success("删除店铺自定义分类成功");
	}
	
	/**
	 * 根据店铺Id获取自定义类型列表
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getCustomCategoryListByShopId")
	public Result<List<ShopCustomCategoryVO>> getCustomCategoryListByShopId(@Valid @RequestBody(required = false)GetIdParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}  
		List<ShopCustomCategoryVO> list= customCategoryService.getCustomCategoryListByShopId(params.getId());
		Result<List<ShopCustomCategoryVO>> result=ResultUtils.success();
		result.setData(list);
		return result;
	}
	
	/**
	 * 根据id查询自定义分类详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopCustomCategoryById")
	public Result<ShopCustomCategoryVO> getShopCustomCategoryById(@Valid @RequestBody(required = false)GetIdParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}  
		ShopCustomCategoryVO shopCustomCategoryVO= customCategoryService.getShopCustomCategoryById(params.getId());
		Result<ShopCustomCategoryVO> result=ResultUtils.success();
		result.setData(shopCustomCategoryVO);
		return result;
	}
}
