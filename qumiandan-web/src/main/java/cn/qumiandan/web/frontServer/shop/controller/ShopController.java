package cn.qumiandan.web.frontServer.shop.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.annotation.ValidateShopManager;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.shop.api.IDyShopService;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.enums.ShopAuditStatusEnum;
import cn.qumiandan.shop.enums.ShopPictureTypeEnum;
import cn.qumiandan.shop.vo.ManagerShopUpdateVO;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.shop.vo.ShopBasicInfoVO;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shop.vo.ShopDetailVO;
import cn.qumiandan.shop.vo.ShopDynamicVO;
import cn.qumiandan.shop.vo.ShopInfoVO;
import cn.qumiandan.shop.vo.ShopPictureVO;
import cn.qumiandan.shop.vo.ShopQueryParamVO;
import cn.qumiandan.shop.vo.ShopStatisticVO;
import cn.qumiandan.shop.vo.ShopUpdateVO;
import cn.qumiandan.shop.vo.ShopVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.user.vo.ShopUserRoleVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;
import cn.qumiandan.web.frontServer.shop.entity.request.AuditUpdateShopParams;
import cn.qumiandan.web.frontServer.shop.entity.request.GetCountyCodeParams;
import cn.qumiandan.web.frontServer.shop.entity.request.GetShopBySalemanIdParams;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopDynamicParams;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopIdParams;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopInfoParams;
import cn.qumiandan.web.frontServer.shop.entity.request.ShopQueryParams;
import cn.qumiandan.web.frontServer.shop.entity.request.addshop.AddShopParams;
import cn.qumiandan.web.frontServer.shop.entity.request.addshop.SalemanAddShopParams;
import cn.qumiandan.web.frontServer.shop.entity.request.shopaudit.ShopAuditRecordParams;
import cn.qumiandan.web.frontServer.shop.entity.request.updateshop.ManagerShopUpdateParams;
import cn.qumiandan.web.frontServer.shop.entity.request.updateshop.SalemanUpdateShopParams;
import cn.qumiandan.web.frontServer.shop.entity.request.updateshop.SetIsOpenShutParams;
import cn.qumiandan.web.frontServer.shop.entity.request.updateshop.UpdateShopParams;
import cn.qumiandan.web.frontServer.shop.entity.request.updateshop.UpdateShopStatusParams;

/**
 * 店铺管理
 * @author lrj
 * @version 创建时间：2018年11月12日 14:00
 */
@RestController
@RequestMapping("/shop/")
public class ShopController {
	@Reference()
	private IShopService shopService;
	
	@Reference()
	private IShopUserService  shopUserService;
	
	@Reference()
	private IDyShopService dyShopService;

	/**
	 * 添加店铺信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	/*@RequestMapping("addShop")
	public Result<Integer> addShop(@Valid @RequestBody(required = false) ShopAddParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if(!params.getOpenTime().matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
			return ResultUtils.paramsError("开始营业时间格式不正确");
		}
		if(!params.getRestTime().matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
			return ResultUtils.paramsError("结束营业时间格式不正确");
		}
		ShopVO shopVO = new ShopVO();
		BeanUtils.copyProperties(shopVO, params);
		int addRet = shopService.addShop(shopVO);
		Result<Integer> result = ResultUtils.success("添加店铺成功");
		result.setData(addRet);
		return result;
	}*/


	/**
	 * 更新店铺信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	/*@RequestMapping("updateShopById")
	public Result<Integer> updateShopById(@Valid @RequestBody(required = false) UpdateShopParams params, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if(params.getOpenTime() != null && !params.getOpenTime().matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
			return ResultUtils.paramsError("开始营业时间格式不正确");
		}
		if(params.getRestTime() != null && !params.getRestTime().matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
			return ResultUtils.paramsError("结束营业时间格式不正确");
		}
		ShopUpdateVO shopUpdateVO = new ShopUpdateVO();
		BeanUtils.copyProperties(shopUpdateVO, params);
		shopService.updateShopById(shopUpdateVO);
		return ResultUtils.success();
	}*/
	
	/**
	 * 根据店铺ID获取店铺基础信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopBasicById")
	 public Result<ShopBasicVO> getShopBasicById(@Valid @RequestBody(required = false) ShopIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		Result<ShopBasicVO> result = ResultUtils.success();
		ShopBasicVO shopBasicVO = shopService.getShopBasicById(params.getId());
		result.setData(shopBasicVO);
		return result;
	 }

	/**
	 * 根据店铺ID获取店铺详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopDetailById")
	 public Result<ShopDetailVO> getShopDetailById(@Valid @RequestBody(required = false) ShopIdParams params, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		if(null == params) {
			return ResultUtils.error("参数不能为空！");
		}
		Result<ShopDetailVO> result = ResultUtils.success();
		ShopDetailVO shopDetailVO = shopService.getShopDetailById(params.getId());
		result.setData(shopDetailVO);
		return result;
	 }
	
	/**
	 * 商家主动申请关闭店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("closeShopById")
	public Result<Integer> closeShopById(@Valid @RequestBody(required = false)ShopIdParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		shopService.closeShopById(params.getId());
		return ResultUtils.success("商家主动申请关闭店铺成功");
	}
	
	
	/**
	 * 根据用户id查询店铺列表
	 * @return
	 */
	@RequestMapping("getShopByManager")
	public Result<List<ShopBasicVO>> getShopByManager() {
		/*if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		//根据用户id查询店铺id集合
		List<ShopUserVO> shopUserVOList = shopUserService.getShopUserListByUserId(params.getUserId());
		List<ShopUserInfoVO> shopUserInfoList = null;
		List<ShopBasicVO> ShopBasicVOList = new ArrayList<>();
		if(shopUserVOList.size() > 0){
			shopUserInfoList = CopyBeanUtil.copyList(shopUserVOList,ShopUserInfoVO.class);
			//根据店铺id集合查询店铺信息
			ShopBasicVOList = shopService.getUserShopByManager(shopUserInfoList);
		}*/
		Result<List<ShopBasicVO>> result = ResultUtils.success();
		result.setData(shopService.getShopBasicInfoByMangerUserId(ShiroUtils.getUserId()));
		return result;
	}
	
	/**
	 * 动态查询店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopDynamic")
	public Result<PageInfo<ShopBasicVO>> getShopDynamic(@Valid @RequestBody(required = false)ShopDynamicParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopDynamicVO shopDynamicVO =
		CopyBeanUtil.copyBean(params, ShopDynamicVO.class);
		//根据用户id查询店铺id集合
		PageInfo<ShopBasicVO> page = shopService.getShopDynamic(shopDynamicVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(page);
		return result;
	}

	/**
	 * 根据店铺id获取商品列表，按自定义分类(菜单)分组
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getCusProductListByShopId")
	public Result<PageInfo<ShopBasicVO>> getCustomProductListByShopId(@Valid @RequestBody(required = false)ShopDynamicParams params,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopDynamicVO shopDynamicVO =
		CopyBeanUtil.copyBean(params, ShopDynamicVO.class);
		//根据用户id查询店铺id集合
		PageInfo<ShopBasicVO> page = shopService.getShopDynamic(shopDynamicVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(page);
		return result;
	}

	/**
	 * 根据区code查询店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopByCountyCode")
	public Result<PageInfo<ShopBasicVO>> getShopByCountyCode(@Valid @RequestBody(required = false)GetCountyCodeParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
//		ShopDynamicVO shopDynamicVO =  
//		CopyBeanUtil.copyBean(params, ShopDynamicVO.class);
		//根据用户id查询店铺id集合
		PageInfo<ShopBasicVO> page = shopService.getShopByCode(params.getCode(),params.getLevel(),params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(page);
		return result;
	}

	/**
	 * 店铺更新审核
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("auditUpdateShop")
	public Result<Boolean> auditUpdateShop(@Valid @RequestBody(required = false)AuditUpdateShopParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		shopService.auditUpdateShop(params.getShopId(),params.getStatus());
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
	 * 店铺审核
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("auditAddShop")
	public Result<Boolean> auditAddShop(@Valid @RequestBody(required = false)ShopAuditRecordParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopAuditRecordVO auditRecordVO = CopyBeanUtil.copyBean(params, ShopAuditRecordVO.class);
		auditRecordVO.setAuditor(ShiroUtils.getUserId());
		shopService.auditAddShop(auditRecordVO);
		Result<Boolean> result = ResultUtils.success();
		if(params.getStatus().equals(ShopAuditStatusEnum.PASS.getCode())) {
			result.setMessage("审核完成；审核结果：审核通过");
			result.setData(true);
		}if(params.getStatus().equals(ShopAuditStatusEnum.UNPASS.getCode())) {
			result.setMessage("审核完成；审核结果：审核未通过");
			result.setData(false);
		}
		return result;
	}
	
	
	/**
	 * 添加店铺(业务员端)
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addShop")
	public Result<Integer> addShop(@Valid @RequestBody(required = false)SalemanAddShopParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopVO vo = CopyBeanUtil.copyBean(params, ShopVO.class);
		vo.setSalemanId(ShiroUtils.getUserId());
		int addRet = shopService.addShop(vo);
		Result<Integer> result = ResultUtils.success("添加店铺成功");
		result.setData(addRet);
		return result;
	}
	
	/**
	 * 修改店铺(业务员端)
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateShop")
	public Result<Integer> updateShop(@Valid @RequestBody(required = false)SalemanUpdateShopParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopUpdateVO vo = CopyBeanUtil.copyBean(params, ShopUpdateVO.class);
		shopService.updateShopById(vo);
		return ResultUtils.success("修改店铺信息成功");
	}
	
	/**
	 * 创建小微商户对私结算类型店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("addSmallBusinesses")
	public Result<Integer> addSmallBusinesses(@Valid @RequestBody(required = false)AddSmallBusinessesParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopVO vo = params.createShopVO();
		int addRet = shopService.addShop(vo);
		Result<Integer> result = ResultUtils.success("添加店铺成功");
		result.setData(addRet);
		return result;
	}
	
	*//**
	 * 创建个体户对私非法人结算类型店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("addIndividualUnincorp")
	public Result<Integer> addIndividualUnincorp(@Valid @RequestBody(required = false)AddIndividualUnincorpParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopVO vo = params.createShopVO();
		int addRet = shopService.addShop(vo);
		Result<Integer> result = ResultUtils.success("添加店铺成功");
		result.setData(addRet);
		return result;

	}
	
	*//**
	 * 创建个体户对私法人结算类型店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("addIndividualCorp")
	public Result<Integer> addIndividualCorp(@Valid @RequestBody(required = false)AddIndividualCorpParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopVO vo = params.createShopVO();
		int addRet = shopService.addShop(vo);
		Result<Integer> result = ResultUtils.success("添加店铺成功");
		result.setData(addRet);
		return result;
	}
	
	
	
	*//**
	 * 创建小微商户对私结算类型店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("updateSmallBusinesses")
	public Result<Integer> updateSmallBusinesses(@Valid @RequestBody(required = false)UpdateSmallBusinessesParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopUpdateVO vo = params.createShopVO();
		shopService.updateShopById(vo);
		return ResultUtils.success("修改店铺信息成功");
	}
	
	*//**
	 * 创建个体户对私非法人结算类型店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("updateIndividualUnincorp")
	public Result<Integer> updateIndividualUnincorp(@Valid @RequestBody(required = false)UpdateIndividualUnincorpParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopUpdateVO vo = params.createShopVO();
		shopService.updateShopById(vo);
		return ResultUtils.success("修改店铺信息成功");
	}
	
	*//**
	 * 创建个体户对私法人结算类型店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 *//*
	@RequestMapping("updateIndividualCorp")
	public Result<Integer> updateIndividualCorp(@Valid @RequestBody(required = false)UpdateIndividualCorpParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopUpdateVO vo = params.createShopVO();
		shopService.updateShopById(vo);
		return ResultUtils.success("修改店铺信息成功");
	}*/
	
	

	/**
	 * 添加店铺(后台管理员使用)
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("addShopByAdminManager")
	public Result<Integer> addShopByAdminManager(@Valid @RequestBody(required = false)AddShopParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopVO vo = CopyBeanUtil.copyBean(params, ShopVO.class);
		int addRet = shopService.addShopByAdminManager(vo);
		Result<Integer> result = ResultUtils.success("添加店铺成功");
		result.setData(addRet);
		return result;
	}
	
	/**
	 * 修改店铺(后台管理员使用)
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateShopByAdminManager")
	public Result<Integer> updateShopByAdminManager(@Valid @RequestBody(required = false)UpdateShopParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopUpdateVO vo = CopyBeanUtil.copyBean(params, ShopUpdateVO.class);
		shopService.updateShopByAdminManager(vo);
		return ResultUtils.success("修改店铺信息成功");
	}
	
	/**
	 * 商家端修改店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateShopByShopManager")
	public Result<Integer> updateShopByShopManager(@Valid @RequestBody(required = false)ManagerShopUpdateParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ManagerShopUpdateVO vo = CopyBeanUtil.copyBean(params, ManagerShopUpdateVO.class);
		shopService.updateShopByShopManager(vo);
		Result<Integer> result = ResultUtils.success("修改店铺信息成功");
		return result;
	}
	
	/**
	 * 获取店铺详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopInfo")
	public Result<ShopInfoVO> getShopInfo(@Valid @RequestBody(required = false)ShopInfoParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		return ResultUtils.success(shopService.getShopInfoById(params.getShopId()));
		
	}
	
	/**
	 * 用户端获取店铺详情
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("clientGetShopInfo")
	public Result<ShopInfoVO> clientGetShopInfo(@Valid @RequestBody(required = false)ShopInfoParams params, BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		ShopInfoVO shopInfoById = shopService.getShopInfoById(params.getShopId());
		if (null == shopInfoById) {
			throw new QmdException("该店铺不存在！");
		}
		if (Objects.nonNull(shopInfoById) && !CollectionUtils.isEmpty(shopInfoById.getShopPictureVOList())) {
			List<ShopPictureVO> newList = shopInfoById.getShopPictureVOList().stream().filter(pic -> 
			!ShopPictureTypeEnum.LegalPersonHoldingIDCard.getCode().equals(pic.getImageType()) && 
			!ShopPictureTypeEnum.FrontOfCorporateIDCard.getCode().equals(pic.getImageType()) && 
			!ShopPictureTypeEnum.OppositeOfCorporateIDCard.getCode().equals(pic.getImageType()) && 
			!ShopPictureTypeEnum.FrontOfBankCard.getCode().equals(pic.getImageType()) && 
			!ShopPictureTypeEnum.FrontOfPrivateAccountIDCard.getCode().equals(pic.getImageType()) && 
			!ShopPictureTypeEnum.OppositePrivateAccountIDCard.getCode().equals(pic.getImageType()) 
			).collect(Collectors.toList());	
			shopInfoById.setShopPictureVOList(newList);
		}
		//若店铺没有设置会员折扣或关闭会员折扣，则返回1000
		if(shopInfoById.getDiscount() == null) {
			shopInfoById.setDiscount(new BigDecimal(1000));
		}
		return ResultUtils.success(shopInfoById);
		
	}
	
	
	/**
	 * 根据店铺状态、店铺名、业务员手机号查询店铺参数
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopByShopQuery")
	public Result<PageInfo<ShopBasicVO>> getShopByShopQuery (@Valid @RequestBody(required = false)ShopQueryParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopQueryParamVO shopQueryParamVO= CopyBeanUtil.copyBean(params, ShopQueryParamVO.class);
		PageInfo<ShopBasicVO> pageInfo = shopService.getShopByShopQuery(shopQueryParamVO);
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
	}
	
	/**
	 * 业务员端查询店铺列表
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopBySalemanId")
	public Result<PageInfo<ShopBasicVO>> getShopBySalemanId (@Valid @RequestBody(required = false)GetShopBySalemanIdParams params,BindingResult bindingResult)  {
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopQueryParamVO paramVO = CopyBeanUtil.copyBean(params, ShopQueryParamVO.class);
		paramVO.setSalemanId(ShiroUtils.getUserId());
		PageInfo<ShopBasicVO> pageInfo = shopService.getShopByShopQuery(paramVO);
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(pageInfo);
		return result;
	}
	
	/**
	 * 用户端查询附近店铺（100公里以内）
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getNearbyShop")
	public Result<PageInfo<ShopBasicVO>> getNearbyShop(@Valid @RequestBody(required = false)ShopDynamicParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopDynamicVO shopDynamicVO = CopyBeanUtil.copyBean(params, ShopDynamicVO.class);
		//附近店铺标识
		shopDynamicVO.setIsNearby(true);
		//根据用户id查询店铺id集合
		PageInfo<ShopBasicVO> page = shopService.getShopDynamic(shopDynamicVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(page);
		return result;
	}
	
	/**
	 * 根据店铺id查询店铺相关人员信息
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("getShopUserRoleByShopId")
	public Result<List<ShopUserRoleVO>> getShopUserRoleByShopId(@Valid @RequestBody(required = false)ShopIdParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		List<ShopUserRoleVO> shopUserRoleByShopId = shopUserService.getShopUserRoleByShopId(params.getId());
		return ResultUtils.success(shopUserRoleByShopId);
	}
	
	/**
	 * 业务员端查询店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("salemanQueryShop")
	public Result<PageInfo<ShopBasicVO>> salemanQueryShop(@Valid @RequestBody(required = false)ShopDynamicParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopDynamicVO shopDynamicVO = CopyBeanUtil.copyBean(params, ShopDynamicVO.class);
		shopDynamicVO.setSalemanId(ShiroUtils.getUserId());
		PageInfo<ShopBasicVO> page = shopService.getShopDynamic(shopDynamicVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(page);
		return result;
	}
	/**
	 * 业务员端查询店铺数量
	 * @return
	 */
	@RequestMapping("getShopNumBySalemanId")
	public Result<ShopStatisticVO> getShopNumBySalemanId(){ 
		ShopStatisticVO shopNumBySalemanId = dyShopService.getShopNumBySalemanId(ShiroUtils.getUserId());
		Result<ShopStatisticVO> result = ResultUtils.success();
		result.setData(shopNumBySalemanId);
		return result;
	}
	
	/**
	 * 总后台动态查询店铺
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("backStageGetShopDynamic")
	public Result<PageInfo<ShopBasicVO>> backStageGetShopDynamic(@Valid @RequestBody(required = false)ShopDynamicParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		ShopDynamicVO shopDynamicVO =
		CopyBeanUtil.copyBean(params, ShopDynamicVO.class);
		//根据用户id查询店铺id集合
		PageInfo<ShopBasicVO> page = shopService.getShopDynamic(shopDynamicVO,params.getPageNum(),params.getPageSize());
		Result<PageInfo<ShopBasicVO>> result = ResultUtils.success();
		result.setData(page);
		return result;
	}
	
	/**
	 * 总后台修改店铺状态
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("updateShopStatus")
	public Result<Void> updateShopStatus(@Valid @RequestBody(required = false)UpdateShopStatusParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		shopService.updateShopStatus(params.getShopId(), params.getStatus());
		return ResultUtils.success();
	}
	
	/**
	 * 设置是否开启打烊及打烊时间参数 
	 * @param params
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("setIsOpenShut")
	@ValidateShopManager
	public Result<Void> setIsOpenShut(@Valid @RequestBody(required = false)SetIsOpenShutParams params,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		} 
		shopService.setIsOpenShut(params.getShopId(), params.getStatus(), params.getOpenTime(),params.getRestTime());
		return ResultUtils.success("设置是否开启打烊成功");
	}
	
	/**
	 * 业务员查询店铺集合
	 * @return
	 */
	@RequestMapping("getShopIdsBySalemanUserId")
	public Result<List<ShopBasicInfoVO>> getShopIdsBySalemanUserId(){
		List<ShopBasicInfoVO> shopIdsBySalemanUserId = shopService.getShopIdsBySalemanUserId(ShiroUtils.getUserId());
		return ResultUtils.success(shopIdsBySalemanUserId);
	}

}
