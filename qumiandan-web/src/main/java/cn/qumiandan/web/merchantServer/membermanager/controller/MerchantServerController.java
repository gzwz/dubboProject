package cn.qumiandan.web.merchantServer.membermanager.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopSimpleVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.AddShopUserVO;
import cn.qumiandan.user.vo.ShopUserDetailVO;
import cn.qumiandan.user.vo.ShopUserVO;
import cn.qumiandan.user.vo.UpdateShopUserRoleVO;
import cn.qumiandan.user.vo.UpdateShopUserVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import cn.qumiandan.verifycode.vo.VerifyCodeAndImgVO;
import cn.qumiandan.web.commonServer.verifycode.ISendVerifyService;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.AddMemberMobileCodeParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.AddMemberPicCodeParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.AddMemberShopInfoParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.MemberByShopIdParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.MemberInfoDetailParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.MemberInfoParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.UnbindShopUserParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.UpdateMemberManageShopParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.UpdateMemberRoleParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.request.VerifyUserExistParams;
import cn.qumiandan.web.merchantServer.membermanager.entity.response.VerifyUserExistResult;


/**
 * 店铺成员管理
 * @author yuleidian
 * @version 创建时间：2018年11月23日 下午4:56:36
 */
@RestController
@RequestMapping("/merchantServerController/")
public class MerchantServerController {

	@Reference(check = false)
	private IShopUserService shopUserService;

	@Reference(check = false)
	private IRoleService roleService;
	
	@Reference
	private IShopService ShopService;
	
	@Reference
	private IVerifyCodeService verifyCodeService;
	
	@Reference
	private IUserService userService;
	
	@Autowired
	private ISendVerifyService sendVerifyService;
	
	/**
	 * 成员管理获取分页列表
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getMemberInfoList")
	public Result<PageInfo<ShopUserVO>> getMemberInfoList(@Valid @RequestBody(required = false) MemberInfoParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		CommonParams param = new CommonParams();
		BeanUtils.copyProperties(param, params);
		param.setUserId(ShiroUtils.getUserId());
		Result<PageInfo<ShopUserVO>> result = ResultUtils.success();
		result.setData(shopUserService.getShopUserPageListByUserId(param));
		return result;
	}
	
	/**
	 * 根据店铺查询店铺成员信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMemberByShopId")
	public Result<PageInfo<ShopUserVO>> getMemberByShopId(@Valid @RequestBody(required = false) MemberByShopIdParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		Result<PageInfo<ShopUserVO>> result = ResultUtils.success();
		result.setData(shopUserService.getShopUserPageListByShopId(CopyBeanUtil.copyBean(params, CommonParams.class)));
		return result;
	}
	
	/**
	 * 获取平台开放角色列表
	 * @return
	 */
	@RequestMapping("getPlatformOpenRolesList")
	public Result<List<RoleVO>> getPlatformOpenRolesList() {
		List<RoleVO> reusltList = roleService.getPlatformOpenRolesList();
		return ResultUtils.success(reusltList);
	}
	
	/**
	 * 获取可管理的店铺列表
	 * @return
	 */
	@RequestMapping("getManagerShopList")
	public Result<List<ShopSimpleVO>> getManagerAbleShopList() throws Exception {
		List<ShopSimpleVO> resultList = ShopService.getShopSimpleInfoByMangerUserId(ShiroUtils.getUserId());
		return ResultUtils.success(resultList);
	}
	
	/**
	 * 获取成员详细信息
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMemberDetail")
	public Result<ShopUserDetailVO> getMemberDetail(@Valid @RequestBody(required = false) MemberInfoDetailParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		CommonParams param = new CommonParams();
		BeanUtils.copyProperties(param, params);
		param.setUserId(ShiroUtils.getUserId());
		return ResultUtils.success(shopUserService.getShopUserDetailInfo(param));
	}
	
	/**
	 * 获取添加店铺成员图片验证码
	 * @return
	 */
	@RequestMapping("getAddMemberPicCode")
	public Result<VerifyCodeAndImgVO> getAddMemberPicCode(@Valid @RequestBody(required = false) AddMemberPicCodeParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		return ResultUtils.success(verifyCodeService.getVerifyCodeAndImg(params.getSign()));
	}
	
	/**
	 * 验证用户是否存在
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("verifyUserExist")
	public Result<VerifyUserExistResult> verifyUserExist(@Valid @RequestBody(required = false) VerifyUserExistParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UserVO user = userService.getUserByUsername(params.getMobile());
		VerifyUserExistResult result = new VerifyUserExistResult();
		if (Objects.isNull(user)) {
			return ResultUtils.success(result);
		}
		result.setAccountFlag(true);
		result.setUserId(user.getId());
		if (!shopUserService.memberExist(user.getId())) {
			result.setShopMemberFlag(true);
		}
		return ResultUtils.success(result);
	}
	
	/**
	 * 获取添加店铺成员手机验证码
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendAddMemberMobileCode")
	public Result<Void> sendAddMemberMobileCode(@Valid @RequestBody(required = false) AddMemberMobileCodeParams params, HttpServletRequest req, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if (!verifyCodeService.validationImgVerifyCode(params.getSign(), params.getCode())) {
			return ResultUtils.success("验证码错误");
		}
		return sendVerifyService.getSmsVerifyCode(params.getMobile(), req.getRemoteHost());
	}
	
	/**
	 * 添加店铺成员
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addMemberShopInfo")
	public Result<Void> addMemberShopInfo(@Valid @RequestBody(required = false) AddMemberShopInfoParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		AddShopUserVO vo = new AddShopUserVO();
		BeanUtils.copyProperties(vo, params);
		shopUserService.addMemberShopInfo(vo);
		return ResultUtils.success();
	}
	
	/**
	 * 修改店铺成员角色
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMemberRole")
	public Result<Void> updateMemberRole(@Valid @RequestBody(required = false) UpdateMemberRoleParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UpdateShopUserRoleVO vo = new UpdateShopUserRoleVO();
		BeanUtils.copyProperties(vo, params);
		shopUserService.updateMemberRole(vo);
		return ResultUtils.success();
	}
	
	/**
	 * 修改店铺成员所管理的店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMemberManageShop")
	public Result<Void> updateMemberManageShop(@Valid @RequestBody(required = false) UpdateMemberManageShopParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		UpdateShopUserVO updateShopUserVO = new UpdateShopUserVO();
		BeanUtils.copyProperties(updateShopUserVO, params);
		shopUserService.updateMemberManageShop(updateShopUserVO);
		return ResultUtils.success();
	}
	
	/**
	 * 解除店铺和成员的绑定关系
	 * @param params
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("unbindMemberShopUser")
	public Result<Void> unbindMemberShopUser(@Valid @RequestBody(required = false) UnbindShopUserParams params, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		shopUserService.unbindMemberShopUser(params.getShopUserId(), params.getUpdateId());
		return ResultUtils.success();
	}
}
