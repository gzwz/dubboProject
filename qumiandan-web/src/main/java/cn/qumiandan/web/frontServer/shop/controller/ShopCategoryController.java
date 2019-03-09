package cn.qumiandan.web.frontServer.shop.controller;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shopcategory.api.IShopCategoryService;
import cn.qumiandan.shopcategory.vo.ShopCategoryPageListVO;
import cn.qumiandan.shopcategory.vo.ShopCategoryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shop.entity.request.AddShopCategoryParams;
import cn.qumiandan.web.frontServer.shop.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopCategoryCommonListParams;
import cn.qumiandan.web.frontServer.shop.entity.request.UpdateShopCategoryParams;

/**
 * 店铺分类管理
 * @author lrj
 * @version 创建时间：2018年11月12日 14:07
 */
@RestController
@RequestMapping("/shopCategory/")
public class ShopCategoryController {
	@Reference()
	private IShopCategoryService shopCategoryService;
	
	/**
	 * 添加店铺分类信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException 
	 */
	@RequestMapping("addShopCategory")
	public Result<Integer> addShopCategory(@Valid @RequestBody(required = false) AddShopCategoryParams params, BindingResult bindingResult) throws QmdException{
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopCategoryVO shopCategoryVO=CopyBeanUtil.copyBean(params, ShopCategoryVO.class);
		shopCategoryService.addShopCategory(shopCategoryVO);
		return ResultUtils.success("添加店铺分类成功");
	}

	/**
	 * 更新店铺分类信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws QmdException 
	 */
	@RequestMapping("updateShopCategoryById")
	public Result<Integer> updateShopCategoryById(@Valid @RequestBody(required = false) UpdateShopCategoryParams params, BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopCategoryVO cagegoryVO = CopyBeanUtil.copyBean(params, ShopCategoryVO.class);
		shopCategoryService.updateShopCategoryById(cagegoryVO);			
		return ResultUtils.success("更新店铺分类成功");
	}
	
    /**
     * 根据id删除店铺分类
     * @param params
     * @return
     * @throws QmdException 
     */
	@RequestMapping("deleteShopCategoryById")
	public Result<Integer> deleteShopCategoryById(@Valid @RequestBody(required = false)GetIdParams params,BindingResult bindingResult) throws QmdException {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		shopCategoryService.deleteShopCategoryById(params.getId());	
		return ResultUtils.success("根据Id(主键)删除店铺分类成功");
	}
	
    /**
     * 根据id获取店铺分类详情
     * @param params
     * @return
     */
	@RequestMapping("getShopCategoryById")
	public Result<ShopCategoryVO> getShopCategoryById(@Valid @RequestBody(required = false)GetIdParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopCategoryVO shopCategoryVO = shopCategoryService.getShopCategoryById(params.getId());
		Result<ShopCategoryVO> result = ResultUtils.success("根据id获取店铺分类详情成功");
		result.setData(shopCategoryVO);
		return result;
	}
	
	/**
     * 根据上级分类id获取店铺分类分页列表
     * @param params
     * @return
     */
	@RequestMapping("getShopCategoryPageListByParentId")
	public Result<PageInfo<ShopCategoryVO>> getShopCategoryPageListByParentId(@Valid @RequestBody(required = false)ShopCategoryCommonListParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopCategoryPageListVO shopCategoryPageListVO = 
		CopyBeanUtil.copyBean(params, ShopCategoryPageListVO.class);
		PageInfo<ShopCategoryVO> categoryPageList = shopCategoryService.getShopCategoryPageListByParentId(shopCategoryPageListVO);
		Result<PageInfo<ShopCategoryVO>> result = ResultUtils.success();
		result.setData(categoryPageList);
		return result;
	}

	/**
     * 获取店铺分类所有数据列表
     * @return
     */
	@RequestMapping("getAllShopCategoryList")
	public Result<List<Map<String,Object>>> getAllShopCategoryList() {
		List<Map<String,Object>> categoryList = shopCategoryService.getAllShopCategoryList();
		Result<List<Map<String,Object>>> result = ResultUtils.success();
		result.setData(categoryList);
		return result;
	}
}
