package cn.qumiandan.web.frontServer.shirofilter.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.params.PageInfoParams;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shirofilter.api.IShiroFilterService;
import cn.qumiandan.shirofilter.vo.ShiroFilterVO;
import cn.qumiandan.system.initializer.service.SystemStartInitializerService;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.shirofilter.entity.request.AddShiroFilterParams;
import cn.qumiandan.web.frontServer.shirofilter.entity.request.UpdateShiroFilterParams;

/**
 * shiro拦截权限添加
 * @author yuleidian
 * @version 创建时间：2019年1月7日 下午6:31:26
 */
@RestController
@RequestMapping("/shiroFilterController/")
public class ShiroFilterController {

	@Reference
	private IShiroFilterService shiroFilterService;
	
	@Autowired
	private SystemStartInitializerService systemStartInitializerService;
	
	/**
	 * 查询方法
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getShiroFilterList")
	public Result<PageInfo<ShiroFilterVO>> getShiroFilterList(@RequestBody(required = false)PageInfoParams params) throws Exception  {
		if (Objects.nonNull(params)) {
			PageInfo<ShiroFilterVO> shiroFilterList = shiroFilterService.getShiroFilterList(params.getPageNum(), params.getPageSize());
			if (Objects.nonNull(shiroFilterList)) {
				return ResultUtils.success(shiroFilterList);
			}
		}
		return ResultUtils.success();
	}
	
	/**
	 * 添加方法
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addShiroFilter")
	public Result<Void> addShiroFilter(@RequestBody(required = false)AddShiroFilterParams params) throws Exception  {
		if (Objects.nonNull(params)) {
			shiroFilterService.addShiroFilter(CopyBeanUtil.copyBean(params, ShiroFilterVO.class));
		}
		return ResultUtils.success();
	}
	
	/**
	 * 更新方法
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateShiroFilter")
	public Result<Void> updateShiroFilter(@RequestBody(required = false)UpdateShiroFilterParams params) throws Exception  {
		if (Objects.nonNull(params)) {
			shiroFilterService.updateShiroFilterVOById(CopyBeanUtil.copyBean(params, ShiroFilterVO.class));
		}
		return ResultUtils.success();
	}
	
	/**
	 * 动态刷新shiro拦截权限
	 * @return
	 */
	@RequestMapping("refreshShiroFilterChian")
	public Result<Void> refreshShiroFilterChian() {
		systemStartInitializerService.initShiroFilterChain();
		return ResultUtils.success();
	}
}
