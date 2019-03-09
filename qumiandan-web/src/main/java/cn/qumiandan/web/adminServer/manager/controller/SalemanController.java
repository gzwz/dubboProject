package cn.qumiandan.web.adminServer.manager.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.saleman.vo.AddSalmanVO;
import cn.qumiandan.saleman.vo.SalemanAndRoleVO;
import cn.qumiandan.saleman.vo.SalemanAndUserParamVO;
import cn.qumiandan.saleman.vo.SalemanAndUserVO;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.saleman.vo.UpdateSalmanVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.adminServer.manager.entity.request.AddSaleManParams;
import cn.qumiandan.web.adminServer.manager.entity.request.CityAgentAddSalemanParams;
import cn.qumiandan.web.adminServer.manager.entity.request.QuerySalemanAndUserParams;
import cn.qumiandan.web.adminServer.manager.entity.request.UnBindSalemanParams;
import cn.qumiandan.web.adminServer.manager.entity.request.UpdateSalemanParams;
import cn.qumiandan.web.adminServer.manager.entity.request.UserIdParams;

@RestController
@RequestMapping("/saleman/")
public class SalemanController {

	@Reference()
	private ISalemanService salemanService;
	
	/**
	 * 添加系统管理员或者业务人员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addSaleman")
	public Result<SalemanVO> addSaleman(@Valid @RequestBody(required = false)AddSaleManParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		AddSalmanVO addSalmanVO = CopyBeanUtil.copyBean(params, AddSalmanVO.class);
		SalemanVO salemanVO = salemanService.addSaleman(addSalmanVO);
		Result<SalemanVO> result = ResultUtils.success("添加业务员成功");
		result.setData(salemanVO);
		return result;
	}
	
	
	/**
	 * 总后台查询业务员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("querySalemanAndUser")
	public Result<PageInfo<SalemanAndUserVO>> querySalemanAndUser(@Valid @RequestBody(required = false)QuerySalemanAndUserParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanAndUserParamVO salemanAndUserVO = CopyBeanUtil.copyBean(params, SalemanAndUserParamVO.class);
		PageInfo<SalemanAndUserVO> pageInfo = salemanService.querySalemanAndUser(salemanAndUserVO);
		return ResultUtils.success(pageInfo);
	}
	
	/**
	 * 省级代理添加业务员或市、区级代理
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("proAgentAddSaleman")
	public Result<SalemanVO> proAgentAddSaleman(@Valid @RequestBody(required = false)AddSaleManParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanVO salemanByUserId = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
		//判断用户是否是省级代理
		if(!salemanByUserId.getType().equals(SalemanTypeEnums.ProAgent.getCode())) {
			ResultUtils.error("该用户不为省级代理，无添加资格");
		}
		//判断省级代理procode和传入code是否相同
		if(!salemanByUserId.getProCode().equals(params.getProCode())) {
			ResultUtils.error("无法为其他省添加市级代理或业务员");
		}
		//判断传入type是否为业务员或市代理
		if(!params.getType().equals(SalemanTypeEnums.CityAgent.getCode()) &&
				!params.getType().equals(SalemanTypeEnums.Saleman.getCode()) &&
				!params.getType().equals(SalemanTypeEnums.CountryAgent.getCode())) {
			ResultUtils.error("该用户只能添加市代理、区代理或业务员");
		}
		AddSalmanVO addSalmanVO = CopyBeanUtil.copyBean(params, AddSalmanVO.class);
		addSalmanVO.setCreateId(ShiroUtils.getUserId());
		SalemanVO salemanVO = salemanService.addSaleman(addSalmanVO);
		Result<SalemanVO> result = ResultUtils.success("添加市代理、区代理、或业务员成功");
		result.setData(salemanVO);
		return result;
	}
	
	/**
	 * 市级代理添加区代理或业务员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("cityAgentAddSaleman")
	public Result<SalemanVO> cityAgentAddSaleman(@Valid @RequestBody(required = false)CityAgentAddSalemanParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanVO salemanByUserId = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
		//判断用户是否是省级代理
		if(!salemanByUserId.getType().equals(SalemanTypeEnums.CityAgent.getCode())) {
			ResultUtils.error("该用户不为市级代理，无添加资格");
		}
		//判断省级代理procode和传入code是否相同
		if(!salemanByUserId.getCityCode().equals(params.getCityCode())) {
			ResultUtils.error("无法为其他市添加区代理或业务员");
		}
		//判断传入type是否为业务员或市代理
		if(!params.getType().equals(SalemanTypeEnums.Saleman.getCode())
				&& !params.getType().equals(SalemanTypeEnums.CountryAgent.getCode())) {
			ResultUtils.error("该用户只能添加区代理或业务员");
		}
		AddSalmanVO addSalmanVO = CopyBeanUtil.copyBean(params, AddSalmanVO.class);
		addSalmanVO.setCreateId(ShiroUtils.getUserId());
		SalemanVO salemanVO = salemanService.addSaleman(addSalmanVO);
		Result<SalemanVO> result = ResultUtils.success("添加区代理或业务员成功");
		result.setData(salemanVO);
		return result;
	}
	
	/**
	 * 区级代理添加业务员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("countryAgentAddSaleman")
	public Result<SalemanVO> countryAgentAddSaleman(@Valid @RequestBody(required = false)CityAgentAddSalemanParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanVO salemanByUserId = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
		//判断用户是否是省级代理
		if(!salemanByUserId.getType().equals(SalemanTypeEnums.CountryAgent.getCode())) {
			ResultUtils.error("该用户不为区级代理，无添加资格");
		}
		//判断区级代理CountryCode和传入code是否相同
		if(!salemanByUserId.getCountryCode().equals(params.getCountryCode())) {
			ResultUtils.error("无法为其他市添加业务员");
		}
		//判断传入type是否为业务员
		if(params.getType().equals(SalemanTypeEnums.Saleman.getCode())) {
			ResultUtils.error("该用户只能添加业务员");
		}
		AddSalmanVO addSalmanVO = CopyBeanUtil.copyBean(params, AddSalmanVO.class);
		addSalmanVO.setCreateId(ShiroUtils.getUserId());
		SalemanVO salemanVO = salemanService.addSaleman(addSalmanVO);
		Result<SalemanVO> result = ResultUtils.success("添加业务员成功");
		result.setData(salemanVO);
		return result;
	}
	
	
	/**
	 * 省代理查询业务员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("proAgentQuerySalemanAndUser")
	public Result<PageInfo<SalemanAndUserVO>> proAgentQuerySalemanAndUser(@Valid @RequestBody(required = false)QuerySalemanAndUserParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanAndUserParamVO salemanAndUserVO = CopyBeanUtil.copyBean(params, SalemanAndUserParamVO.class);
		SalemanVO salemanVO = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
		salemanAndUserVO.setProCode(salemanVO.getProCode());
		salemanAndUserVO.setTypeList(Lists.newArrayList(SalemanTypeEnums.CityAgent.getCode(),SalemanTypeEnums.CountryAgent.getCode(),SalemanTypeEnums.Saleman.getCode()));
		PageInfo<SalemanAndUserVO> pageInfo = salemanService.querySalemanAndUser(salemanAndUserVO);
		return ResultUtils.success(pageInfo);
	}
	
	/**
	 * 市代理查询业务员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("cityAgentQuerySalemanAndUser")
	public Result<PageInfo<SalemanAndUserVO>> cityAgentQuerySalemanAndUser(@Valid @RequestBody(required = false)QuerySalemanAndUserParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanAndUserParamVO salemanAndUserVO = CopyBeanUtil.copyBean(params, SalemanAndUserParamVO.class);
		SalemanVO salemanVO = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
		salemanAndUserVO.setProCode(salemanVO.getProCode());
		salemanAndUserVO.setCityCode(salemanVO.getCityCode());
		salemanAndUserVO.setTypeList(Lists.newArrayList(SalemanTypeEnums.CountryAgent.getCode(),SalemanTypeEnums.Saleman.getCode()));
		PageInfo<SalemanAndUserVO> pageInfo = salemanService.querySalemanAndUser(salemanAndUserVO);
		return ResultUtils.success(pageInfo);
	}
	
	/**
	 * 区代理查询业务员
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("countryAgentQuerySalemanAndUser")
	public Result<PageInfo<SalemanAndUserVO>> countryAgentQuerySalemanAndUser(@Valid @RequestBody(required = false)QuerySalemanAndUserParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanAndUserParamVO salemanAndUserVO = CopyBeanUtil.copyBean(params, SalemanAndUserParamVO.class);
		SalemanVO salemanVO = salemanService.getSalemanByUserId(ShiroUtils.getUserId());
		salemanAndUserVO.setProCode(salemanVO.getProCode());
		salemanAndUserVO.setCityCode(salemanVO.getCityCode());
		salemanAndUserVO.setCountryCode(salemanVO.getCountryCode());
		salemanAndUserVO.setTypeList(Lists.newArrayList(SalemanTypeEnums.Saleman.getCode()));
		PageInfo<SalemanAndUserVO> pageInfo = salemanService.querySalemanAndUser(salemanAndUserVO);
		return ResultUtils.success(pageInfo);
	}
	
	/**
	 * 查询省、市、区代理、业务员信息及其角色信息
	 * @return
	 */
	@RequestMapping("getSalemanAndRoleByUserId")
	public Result<SalemanAndRoleVO>  getSalemanAndRoleByUserId(){
		SalemanAndRoleVO salemanAndRoleByUserId = salemanService.getSalemanAndRoleByUserId(ShiroUtils.getUserId());
		return ResultUtils.success(salemanAndRoleByUserId);
	}
	
	
	/**
	 * 根据用户id查询业务员信息
	 * @return
	 */
	@RequestMapping("getSalemanAndUserByUserId")
	public Result<SalemanAndUserVO>  getSalemanAndUserByUserId(@Valid @RequestBody(required = false)UserIdParams params, 
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		SalemanAndUserVO andUserVO =  salemanService.getSalemanAndUserByUserId(params.getUserId());
		return ResultUtils.success(andUserVO);
	}
	
	/**
	 * 
	 * 目前只支持更改 
	 * 1.费率 
	 * 2.银行卡信息
	 * 更新业务员级的 基础信息
	 * @return
	 */
	@RequestMapping("updateSalemanInfo")
	public Result<Void> updateSalemanInfo(@Valid @RequestBody(required = false)UpdateSalemanParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UpdateSalmanVO vo = CopyBeanUtil.copyBean(params, UpdateSalmanVO.class);
		salemanService.updateSalemanInfo(vo);
		return ResultUtils.success();
	}
	
	/**
	 * 删除业务员 代理角色信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("unBindSaleman")
	public Result<Void> unBindSaleman(@Valid @RequestBody(required = false)UnBindSalemanParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		salemanService.unBindSaleman(params.getId(), ShiroUtils.getUserId());
		return ResultUtils.success();
	}
	
}
