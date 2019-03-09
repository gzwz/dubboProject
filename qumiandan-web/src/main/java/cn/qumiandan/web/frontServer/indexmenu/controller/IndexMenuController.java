package cn.qumiandan.web.frontServer.indexmenu.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.indexmenu.api.IIndexMenuService;
import cn.qumiandan.indexmenu.vo.IndexMenuVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.indexmenu.entity.request.AddIndexMenuParams;
import cn.qumiandan.web.frontServer.indexmenu.entity.request.GetIndexMenuIdParams;
import cn.qumiandan.web.frontServer.indexmenu.entity.request.UpdateIndexMenuParams;

@RestController
@RequestMapping("/indexMenu/")
public class IndexMenuController {
	@Reference()
	private IIndexMenuService indexMenuService;
	
	/**
	 * 添加首页菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addIndexMenu")
	public Result<Integer> addIndexMenu(@Valid @RequestBody(required = false) AddIndexMenuParams params,  BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		IndexMenuVO indexMenuVO =
		CopyBeanUtil.copyBean(params, IndexMenuVO.class);
		indexMenuService.addIndexMenu(indexMenuVO);	
		return ResultUtils.success("添加首页菜单成功");
	}
	
	/**
	 * 修改首页菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateIndexMenu")
	public Result<Integer> updateIndexMenu(@Valid @RequestBody(required = false) UpdateIndexMenuParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		IndexMenuVO indexMenuVO =
		CopyBeanUtil.copyBean(params, IndexMenuVO.class);
		indexMenuService.updateIndexMenu(indexMenuVO);	
		return ResultUtils.success("修改首页菜单成功");
	}
	
	/**
	 * 删除首页菜单
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteIndexMenu")
	public Result<Integer> deleteIndexMenu(@Valid @RequestBody(required = false) GetIndexMenuIdParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		indexMenuService.deleteIndexMenu(params.getId());	
		return ResultUtils.success("删除首页菜单成功");
	}
	
	/**
	 * 根据主键查询首页菜单信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getIndexMenuById")
	public Result<IndexMenuVO> getIndexMenuById(@Valid @RequestBody(required = false) GetIndexMenuIdParams params,  BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		IndexMenuVO indexMenuVO = indexMenuService.getIndexMenuById(params.getId());	
		Result<IndexMenuVO> result =  ResultUtils.success();
		result.setData(indexMenuVO);
		return result;
	}
	
	/**
	 * 查询所有首页菜单信息
	 * @return
	 */
	@RequestMapping("getAllIndexMenu")
	public Result<List<IndexMenuVO>> getAllIndexMenu(){
		List<IndexMenuVO> list= indexMenuService.getAllIndexMenu();	
		Result<List<IndexMenuVO>> result =  ResultUtils.success();
		result.setData(list);
		return result;
	}
}
