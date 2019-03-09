package cn.qumiandan.shop.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.qumiandan.areacode.api.IAreaCodeService;
import cn.qumiandan.bankinfo.api.IBankInfoService;
import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.exception.SHErrorCode;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.industry.api.IIndustryService;
import cn.qumiandan.industry.vo.IndustryVO;
import cn.qumiandan.pay.saobei.service.ISaoBeiMerchantService;
import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.BaseMerchantResVO;
import cn.qumiandan.pay.saobei.vo.response.merchant.CreateMerchantResVO;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.api.IPayAccountManagerService;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.enums.AccountTypeEnum;
import cn.qumiandan.payaccount.enums.WithdrawEnum;
import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.api.IUserRoleService;
import cn.qumiandan.role.vo.AddUserRoleVO;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.saleman.api.ICheckSaleOpenShopService;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.vo.CheckSaleOpenShopVO;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.saobeishopinfo.api.ISaoBeiShopInfoServicce;
import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;
import cn.qumiandan.shop.api.IShopAuditRecordService;
import cn.qumiandan.shop.api.IShopGameSwitchService;
import cn.qumiandan.shop.api.IShopPictureService;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.entity.Shop;
import cn.qumiandan.shop.entity.ShopExtend;
import cn.qumiandan.shop.entity.ShopPicture;
import cn.qumiandan.shop.enums.ShopAuditStatusEnum;
import cn.qumiandan.shop.enums.ShopPictureTypeEnum;
import cn.qumiandan.shop.enums.ShopStatusEnum;
import cn.qumiandan.shop.mapper.ShopExtendMapper;
import cn.qumiandan.shop.mapper.ShopMapper;
import cn.qumiandan.shop.mapper.ShopPictureMapper;
import cn.qumiandan.shop.vo.ManagerShopUpdateVO;
import cn.qumiandan.shop.vo.ShopAuditRecordVO;
import cn.qumiandan.shop.vo.ShopBasicInfoVO;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.shop.vo.ShopDetailVO;
import cn.qumiandan.shop.vo.ShopDynamicVO;
import cn.qumiandan.shop.vo.ShopExtendVO;
import cn.qumiandan.shop.vo.ShopInfoVO;
import cn.qumiandan.shop.vo.ShopPictureVO;
import cn.qumiandan.shop.vo.ShopQueryParamVO;
import cn.qumiandan.shop.vo.ShopSimpleVO;
import cn.qumiandan.shop.vo.ShopUpdateVO;
import cn.qumiandan.shop.vo.ShopUserInfoVO;
import cn.qumiandan.shop.vo.ShopVO;
import cn.qumiandan.shopmember.api.IVipDiscountInfoService;
import cn.qumiandan.shopmember.vo.VipDiscountInfoVO;
import cn.qumiandan.shopprofit.api.IShopProfitService;
import cn.qumiandan.shopprofit.vo.ShopProfitDetailVO;
import cn.qumiandan.shopprofit.vo.ShopProfitVO;
import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.ticket.api.IQualificationTicketService;
import cn.qumiandan.ticket.vo.QualificationTicketVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.ShopUserVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@Service(interfaceClass = IShopService.class)
@Slf4j
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private ShopExtendMapper shopExtendMapper;
	@Autowired
	private ShopPictureMapper shopPictureMapper;
	@Autowired
	private IIndustryService industryService;
	@Reference()
	private IUserService userService;
	@Reference()
	private IShopUserService shopUserService;
	@Reference()
	private IUserRoleService userRoleService;
	@Reference()
	private IRoleService roleService;
	@Reference
	private ISysPropertiesService sysPropertiesService;
	@Autowired
	private IVipDiscountInfoService vipDiscountInfoService;
	@Autowired
	private IShopAuditRecordService shopAuditRecordService;

	@Autowired
	private ISaoBeiShopInfoServicce saoBeiShopInfoServicce;

	@Reference
	private ISaoBeiMerchantService saoBeiMerchantService;

	@Reference
	private IAreaCodeService areaCodeService;

	@Reference
	private IBankInfoService bankInfoService;

	@Reference
	private IPayAccountService payAccountService;
	
	@Reference
	private IBankCardService bankCardService;
	
	@Reference
	private IPayAccountManagerService payAccountManagerService;
	
	@Autowired
	private IShopPictureService shopPictureService;
	
	@Reference
	private IQualificationTicketService qualificationTicketService;
	
	@Autowired
	private IShopProfitService shopProfitService;
	
	@Reference
	private ISalemanService salemanService ;
	
	@Reference
	private ICheckSaleOpenShopService checkSaleOpenShopService;
	@Autowired
	private IShopGameSwitchService ShopGameSwitchService;
	@Override
	public boolean existShopName(String name) {
		Shop shop = shopMapper.selectOne(new QueryWrapper<Shop>().eq("name", name));
		if (Objects.nonNull(shop) && Objects.nonNull(shop.getId())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 首页获取店铺列表（分页）
	 * 
	 * @return 返回店铺信息列表
	 */
	/*
	 * @Override public PageInfo<ShopVO> getIndexShopList(int pageNum, int
	 * pageSize) { //开始分页 PageHelper.startPage(pageNum, pageSize); //条件查询
	 * List<ShopVO> shopVOList = shopMapper.getIndexShopList(); //封装分页结果
	 * PageInfo<ShopVO> pageInfo = new PageInfo<>(shopVOList);
	 * pageInfo.setTotal(pageInfo.getTotal()); //返回分页对象 return pageInfo; }
	 */

	/**
	 * 根据类型获取店铺列表信息
	 * 
	 * @return List<ShopVO>
	 */
	/*
	 * @Override public PageInfo<ShopVO> getIndexShopListByType(Long type, int
	 * pageNum, int pageSize) { PageHelper.startPage(pageNum, pageSize);
	 * List<ShopVO> shopVOList = shopMapper.getIndexShopListByType(type);
	 * PageInfo<ShopVO> pageInfo = new PageInfo<>(shopVOList);
	 * pageInfo.setTotal(pageInfo.getTotal()); return pageInfo; }
	 */

	/**
	 * 根据id获取店铺基础信息
	 * 
	 * @param shopId
	 * @return shopBasicVO
	 */
	@Override
	public ShopBasicVO getShopBasicById(Long shopId) {
		return shopMapper.getShopBasicById(shopId);
	}

	/**
	 * 根据id获取店铺详情
	 * 
	 * @param shopId
	 * @return shopBasicVO
	 */
	@Override
	public ShopDetailVO getShopDetailById(Long shopId) {
		// 店铺基础信息
		ShopBasicVO shopBasicVO = shopMapper.getShopBasicById(shopId);
		// 店铺图片信息
		List<ShopPicture> shopPictureList = shopPictureMapper.selectByShopId(shopId);
		ShopDetailVO shopDetailVO = new ShopDetailVO();
		List<ShopPictureVO> shopPictureVOList = new ArrayList<>();
		if (shopBasicVO == null) {
			return null;
		}
		shopDetailVO = CopyBeanUtil.copyBean(shopBasicVO, ShopDetailVO.class);
		if (shopPictureList.size() > 0) {
			for (ShopPicture shopPicture : shopPictureList) {
				ShopPictureVO shopPictureVO = new ShopPictureVO();
				shopPictureVO.setImageName(shopPicture.getName());
				shopPictureVO.setImageUrl(shopPicture.getImageUrl());
				shopPictureVO.setImageType(shopPicture.getImageType());
				shopPictureVO.setImageSize(shopPicture.getImageSize());
				shopPictureVO.setIntro(shopPicture.getIntro());
				shopPictureVO.setSkipUrl(shopPicture.getSkipUrl());
				shopPictureVO.setSuffix(shopPicture.getSuffix());
				shopPictureVO.setImageSort(shopPicture.getSort());
				shopPictureVOList.add(shopPictureVO);
			}
		}
		//店铺费率
		ShopProfitDetailVO shopProfitByShopId = shopProfitService.getShopProfitByShopId(shopId);
		if(shopProfitByShopId != null ) {
			shopDetailVO.setShopProfit(shopProfitByShopId.getRate());
		}
		shopDetailVO.setShopPictureVOList(shopPictureVOList);
		shopDetailVO.setSaoBeiShopInfoVO(saoBeiShopInfoServicce.getSaoBeiShopInfo(shopId));
		
		//查询总店管理员信息
		UserVO user = userService.getUserById(shopBasicVO.getShopAdminId());
		if (Objects.nonNull(user)) {
			shopDetailVO.setMobile(user.getUserName());
		}
		
		//查询业务员信息
		UserVO saleman = userService.getUserById(shopBasicVO.getShopAdminId());
		if(Objects.nonNull(saleman)) {
			shopDetailVO.setSalemanUserName(saleman.getUserName());
		}
		return shopDetailVO;
	}

	/**
	 * 添加店铺信息
	 * 
	 * @param shopVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addShop(ShopVO shopVO)throws QmdException {
		// 判断业务员是否还有开店资格
		if (!qualificationTicketService.hasAvailableTicket(shopVO.getCreateId())) {
			log.error("addShop|业务员没有开店资格 operator" + shopVO.getCreateId());
			throw new QmdException("业务员没有开店资格");
		}
		
		CheckSaleOpenShopVO checkSaleOpenShop = new CheckSaleOpenShopVO();
		checkSaleOpenShop.setSalemanId(shopVO.getCreateId());
		checkSaleOpenShop.setShopAreaCode(shopVO.getCountyCode());
		Boolean saleOpenShop = checkSaleOpenShopService.checkSaleOpenShop(checkSaleOpenShop);
		if (saleOpenShop == null || !saleOpenShop) {
			log.error("addShop|业务员没有开店这个区域店铺资格 operator" + shopVO.getCreateId() + "|countyCode:" + shopVO.getCountyCode());
			throw new QmdException("业务员没有开店这个区域店铺资格");
		}
		
		// 手机号在user表中是否存在
		UserVO userVO = userService.getUserByUsername(shopVO.getMobile());
		if (userVO.getId() == null) {
			log.error("addShop|用户账号未在平台注册 operator" + shopVO.getCreateId());
			throw new QmdException("用户账号未在平台注册，请先注册为平台用户");
		}

		if (existShopName(shopVO.getName())) {
			log.error("addShop|店铺名称已存在 operator" + shopVO.getCreateId());
			throw new QmdException("店铺名称已存在, 请修改");
		}
		Date now = new Date();
		// 保存店铺基础信息
		Shop shop = CopyBeanUtil.copyBean(shopVO, Shop.class);
		shop.setShopAdminId(userVO.getId());
		shop.setCreateDate(now);
		shop.setUpdateDate(now);
		shop.setStatus(ShopStatusEnum.CREATECOMMIT.getCode());
		shop.setSalemanId(shopVO.getCreateId());
		if (!checkCUD(shopMapper.insert(shop))) {
			log.error("添加店铺失败 刷入数据库后 影响条数不为1");
			throw new QmdException("添加店铺失败");
		}
		
		// 保存店铺扩展信息
		ShopExtend shopExtend = CopyBeanUtil.copyBean(shopVO, ShopExtend.class);
		shopExtend.setShopId(shop.getId());
		if (shopExtend.getMembersLimit() == null) {
			SysPropertiesVO sysPropertiesVO = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.SHOPMAXMEMBERS.getId());
			// 设置为平台默认限制数量
			shopExtend.setMembersLimit(Integer.parseInt(sysPropertiesVO.getValue()));
		}
		
		if (!checkCUD(shopExtendMapper.insert(shopExtend))) {
			log.error("addShop|添加店铺扩展表失败 刷入数据库后 影响条数不为1 operator" + shopVO.getCreateId());
			throw new QmdException("添加店铺扩展表失败");
		}
		// 保存或者更新店铺费率
		// 查询店铺相关的行业信息，将数据插入到店铺费率表
		IndustryVO industryVO = industryService.getIndustryVOById(shopVO.getIndustryId());
		if (null == industryVO) {
			throw new QmdException("行业信息不存在");
		}
		
		ShopProfitVO shopProfitVO = new ShopProfitVO();
		shopProfitVO.setSbRateCode(industryVO.getRateCode());
		shopProfitVO.setRate(industryVO.getFee());
		shopProfitVO.setShopId(shop.getId());
		shopProfitService.updateShopProfit(shopProfitVO);
		
		// 保存店铺-行业关联信息
		/*
		 * ShopIndustryRelation shopIndustryRelation = CopyBeanUtil.copyBean(shopVO,
		 * ShopIndustryRelation.class); shopIndustryRelation.setShopId(shop.getId()); if
		 * (!checkCUD(shopIndustryRelationMapper.insert(shopIndustryRelation))) {
		 * log.error("addShop|行业关联信息表失败 刷入数据库后 影响条数不为1 operator" +
		 * shopVO.getCreateId()); throw new QmdException("添加店铺 行业关联信息表失败"); }
		 */

		// 保存店铺图片
		if (!CollectionUtils.isEmpty(shopVO.getShopPictureVOList())) {
			for (ShopPictureVO pic : shopVO.getShopPictureVOList()) {
				if (Objects.nonNull(pic)) {
					if (ShopPictureTypeEnum.Logo.getCode().equals(pic.getImageType())) {
						shop.setLogo(pic.getImageUrl());
						shopMapper.updateById(shop);
					}
					pic.setShopId(shop.getId());
					pic.setCreateDate(now);
					pic.setStatus(StatusEnum.normal.getCode());
					addShopPicture(pic);
				}
			}
		}

		// 保存店长用户角色信息
		RoleVO roleVO = roleService.getShopManagerRoleInfo();
		AddUserRoleVO addUserRole = new AddUserRoleVO();
		addUserRole.setStatus(StatusEnum.normal.getCode());
		addUserRole.setSysRoleId(roleVO.getId());
		addUserRole.setSysUserId(userVO.getId());
		AddUserRoleVO addResult = userRoleService.addUserRole(addUserRole);

		// 保存店铺人员(总店管理员)信息
		ShopUserVO shopUserVO = new ShopUserVO();
		shopUserVO.setUserRoleId(addResult.getId());
		shopUserVO.setUserId(userVO.getId());
		shopUserVO.setShopId(shop.getId());
		shopUserVO.setUserName(shopVO.getMobile());
		shopUserVO.setCreateDate(now);
		shopUserVO.setUpdateDate(now);
		shopUserVO.setCreateId(shopVO.getCreateId());
		shopUserVO.setUpdateId(shopVO.getCreateId());
		shopUserVO.setStatus(StatusEnum.normal.getCode());
		shopUserService.addShopUser(shopUserVO);

		// 创建店铺账户
		BigDecimal zero = new BigDecimal(0);
		PayAccountVO payAccountVO = new PayAccountVO();
		payAccountVO.setShopId(shop.getId());
		payAccountVO.setBalance(zero);
		payAccountVO.setSettBalance(zero);
		payAccountVO.setUnbalance(zero);
		payAccountVO.setSecurityMoney(zero);
		payAccountVO.setName(shop.getName());
		payAccountVO.setType(AccountTypeEnum.Shop.getCode());
		payAccountVO.setWithdrawStatus(WithdrawEnum.ABLE.getCode());
		payAccountVO.setStatus(StatusEnum.normal.getCode());
		payAccountVO.setCreateDate(now);
		payAccountVO.setCreateId(shopVO.getCreateId());
		PayAccountVO addPayAccount = payAccountService.addPayAccount(payAccountVO);
		
		// 创建银行卡信息
		BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(shopVO.getBankId());
		BankCardVO bankCardVO = new BankCardVO();
		bankCardVO.setAccountId(addPayAccount.getId());
		bankCardVO.setBankName(bankInfoVO.getSubBankName());
		bankCardVO.setBankCardNo(shopVO.getBankAccount());
		bankCardVO.setBankCardHolder(shopVO.getCardHolder());
		bankCardVO.setBankMobile(shopVO.getMobile());
		bankCardVO.setUseTimes(0);
		bankCardVO.setSort(1);
		bankCardVO.setStatus(StatusEnum.normal.getCode());
		bankCardVO.setCreateId(shopVO.getCreateId());
		bankCardVO.setCreateDate(now);
		bankCardService.addBankCard(bankCardVO);
		
		// 创建支付账户管理关联信息
		/*PayAccountManagerVO payAccountManagerVO = new PayAccountManagerVO();
		payAccountManagerVO.setAccountId(addPayAccount.getId());
		payAccountManagerVO.setManagerType(AccountManagerTypeEnum.GENERIC.getCode());
		payAccountManagerVO.setUserId(userVO.getId());
		payAccountManagerVO.setCreateDate(now);
		payAccountManagerService.addPayAccountManager(payAccountManagerVO);*/
		
		// 处理卷的使用情况
		qualificationTicketService.setTicketStatusToUsing(shopVO.getTicketId(), shop.getId());
		
		// 添加扫呗信息
		/*AreaCodeVO areaCodeVO = areaCodeService.getSaobeiAreaInfoByCountryCode(shopVO.getCountyCode());
		Industry industry = industryMapper.selectById(shopVO.getIndustryId());
		BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(shopVO.getBankId());
		SaoBeiShopInfoVO saobeiShopInfoVO = shopVO.getSaoBeiShopInfoVO();
		if (Objects.nonNull(saobeiShopInfoVO)) {
			saobeiShopInfoVO.setShopId(shop.getId());
			saobeiShopInfoVO.setCreateId(shop.getCreateId());

			saobeiShopInfoVO.setMerchantProvince(areaCodeVO.getProvince());
			saobeiShopInfoVO.setMerchantProvinceCode(areaCodeVO.getProvinceCode());
			saobeiShopInfoVO.setMerchantCity(areaCodeVO.getCity());
			saobeiShopInfoVO.setMerchantCityCode(areaCodeVO.getCityCode());
			saobeiShopInfoVO.setMerchantCounty(areaCodeVO.getArea());
			saobeiShopInfoVO.setMerchantCountyCode(areaCodeVO.getAreaCode());

			saobeiShopInfoVO.setBusinessName(industry.getName());
			saobeiShopInfoVO.setBusinessCode(industry.getSbIndustryId().toString());

			saobeiShopInfoVO.setBankName(bankInfoVO.getSubBankName());
			saobeiShopInfoVO.setBankNo(bankInfoVO.getSubBankNo());

			saobeiShopInfoVO.setRateCode(industry.getRateCode());

			saoBeiShopInfoServicce.addSaoBeiShopInfo(saobeiShopInfoVO);
		}*/
		return 1;
	}

	/**
	 * // 查询店铺相关的行业信息，将数据插入到店铺费率表
	 * @param shopVO
	 * @param shop
	 */
 

	/**
	 * 更新店铺信息
	 * 
	 * @param shopUpdateVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public void updateShopById(ShopUpdateVO shopUpdateVO) throws QmdException {
		CheckSaleOpenShopVO checkSaleOpenShop = new CheckSaleOpenShopVO();
		checkSaleOpenShop.setSalemanId(shopUpdateVO.getUpdateId());
		checkSaleOpenShop.setShopAreaCode(shopUpdateVO.getCountyCode());
		if (!checkSaleOpenShopService.checkSaleOpenShop(checkSaleOpenShop)) {
			log.error("updateShopById|业务员没有开店这个区域店铺资格 operator" + shopUpdateVO.getUpdateId() + "|countyCode:" + shopUpdateVO.getCountyCode());
			throw new QmdException("业务员没有开店这个区域店铺资格");
		}
		
		Date now = new Date();
		// 手机号在user表中是否存在
		UserVO userVO = userService.getUserByUsername(shopUpdateVO.getMobile());
		if (userVO.getId() == null) {
			log.error("updateShopById|用户账号未在平台注册 operator" + shopUpdateVO.getUpdateId());
			throw new QmdException("用户账号未在平台注册，请先注册为平台用户");
		}

		Boolean saleOpenShop = checkSaleOpenShopService.checkSaleOpenShop(checkSaleOpenShop);
		if (saleOpenShop == null || !saleOpenShop) {
			log.error("updateShopById|业务员没有开店这个区域店铺资格 operator" + shopUpdateVO.getUpdateId() + "|countyCode:" + shopUpdateVO.getCountyCode());
			throw new QmdException("业务员没有开店这个区域店铺资格");
		}
		
		// 更新店铺基础信息
		Shop shop = shopMapper.selectById(shopUpdateVO.getId());
		if (shop != null) {
			if (!shop.getName().equals(shopUpdateVO.getName())) {
				if (existShopName(shopUpdateVO.getName())) {
					log.error("updateShopById|店铺名称已存在 operator" + shopUpdateVO.getUpdateId());
					throw new QmdException("店铺名称已存在, 请修改");
				}
			}
			
			shopUpdateVO.setUpdateDate(now);
			shop = CopyBeanUtil.copyBean(shopUpdateVO, Shop.class);
			
			// 修改店铺为待审核状态
			shop.setShopAdminId(userVO.getId());
			shop.setStatus(ShopStatusEnum.CREATECOMMIT.getCode());
			int shopRet = shopMapper.updateById(shop);
			if (shopRet != 1) {
				log.error("updateShopById|更新店铺信息失败, 更新店铺主表信息返回影响行数不为1 operator" + shopUpdateVO.getUpdateId());
				throw new QmdException("店铺信息更新失败");
			}
			
			// 更新店铺扩展信息
			ShopExtend oldShopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shop.getId()));
			if (Objects.nonNull(oldShopExtend)) {
				ShopExtend shopExtendEntity = CopyBeanUtil.copyBean(shopUpdateVO, ShopExtend.class);
				shopExtendEntity.setId(oldShopExtend.getId());
				if (!checkCUD(shopExtendMapper.updateById(shopExtendEntity))) {
					log.error("updateShopById|更新店铺扩展信息失败 operator" + shopUpdateVO.getUpdateId());
					throw new QmdException("店铺信息更新失败");
				}
			}

			// 保存店铺-行业关联信息
			// 查询店铺相关的行业信息，将数据插入到店铺费率表
			IndustryVO industryVO = industryService.getIndustryVOById(shopUpdateVO.getIndustryId());
			if (null == industryVO) {
				throw new QmdException("行业信息不存在");
			}
			ShopProfitVO shopProfitVO = new ShopProfitVO();
			shopProfitVO.setSbRateCode(industryVO.getRateCode());
			shopProfitVO.setRate(industryVO.getFee());
			shopProfitVO.setShopId(shop.getId());
			shopProfitService.updateShopProfit(shopProfitVO);
			/*
			 * int IndustryRet = shopIndustryRelationMapper.update(new
			 * ShopIndustryRelation(), new UpdateWrapper<ShopIndustryRelation>()
			 * .set("industry_id", shopUpdateVO.getIndustryId()).eq("shop_id",
			 * shopUpdateVO.getId())); if (IndustryRet != 1) {
			 * log.error("updateShopById|更新店铺信息失败, 更新行业关联表信息返回影响行数 operator" +
			 * shopUpdateVO.getUpdateId()); throw new QmdException("店铺信息更新失败"); }
			 */
			
			// 图片
			if (!CollectionUtils.isEmpty(shopUpdateVO.getShopPictureVOList())) {
				Set<Byte> deletedType = Sets.newHashSetWithExpectedSize(13);
				for (ShopPictureVO pic : shopUpdateVO.getShopPictureVOList()) {
					if (Objects.nonNull(pic)) {
						if (!deletedType.contains(pic.getImageType())) {
							shopPictureMapper.deleteAlbumsByShopIdAndType(shop.getId(), pic.getImageType());
						}
						if (ShopPictureTypeEnum.Logo.getCode().equals(pic.getImageType())) {
							shop.setLogo(pic.getImageUrl());
							shopMapper.updateById(shop);
						}
						pic.setShopId(shop.getId());
						pic.setStatus(StatusEnum.normal.getCode());
						pic.setCreateDate(now);
						addShopPicture(pic);
						deletedType.add(pic.getImageType());
						
					}
				}
			}

			// 判断是否修改管理账号
			if (!shopUserService.memberExist(userVO.getId())) {
				// 解除老店管理的权限和绑定关系
				List<ShopUserVO> shopuserList = shopUserService.getShopUserListByShopId(shop.getId());
				if (!CollectionUtils.isEmpty(shopuserList)) {
					shopuserList.forEach(shopUser -> {
						if (Objects.nonNull(shopUser)) {
							shopUserService.unbindMemberShopUser(shopUser.getId(), shopUpdateVO.getUpdateId());
						}
					});
				}
				// 保存店长用户角色信息
				RoleVO roleVO = roleService.getShopManagerRoleInfo();
				AddUserRoleVO addUserRole = new AddUserRoleVO();
				addUserRole.setStatus(StatusEnum.normal.getCode());
				addUserRole.setSysRoleId(roleVO.getId());
				addUserRole.setSysUserId(userVO.getId());
				AddUserRoleVO addResult = userRoleService.addUserRole(addUserRole);
				
				// 保存店铺人员(总店管理员)信息
				ShopUserVO shopUserVO = new ShopUserVO();
				shopUserVO.setUserRoleId(addResult.getId());
				shopUserVO.setUserId(userVO.getId());
				shopUserVO.setShopId(shop.getId());
				shopUserVO.setUserName(shopUpdateVO.getMobile());
				shopUserVO.setCreateDate(now);
				shopUserVO.setUpdateDate(now);
				shopUserVO.setCreateId(shopUpdateVO.getUpdateId());
				shopUserVO.setUpdateId(shopUpdateVO.getUpdateId());
				shopUserVO.setStatus(StatusEnum.normal.getCode());
				shopUserService.addShopUser(shopUserVO);
			}

			// 创建店铺账户
			PayAccountVO payAccountVO = payAccountService.getPayAccountByShopId(shopUpdateVO.getId());
			if (Objects.nonNull(payAccountVO)) {
				payAccountVO.setShopId(shop.getId());
				payAccountVO.setWithdrawStatus(WithdrawEnum.ABLE.getCode());
				payAccountVO.setStatus(StatusEnum.normal.getCode());
				payAccountVO.setCreateDate(now);
				payAccountVO.setCreateId(shopUpdateVO.getUpdateId());
				payAccountService.updatePayAccount(payAccountVO);
				
				// 更新银行卡信息
				BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(shopUpdateVO.getBankId());
				BankCardVO bankCardVO = bankCardService.getBankCardByAccountId(payAccountVO.getId());
				if (Objects.nonNull(bankCardVO)) {
					bankCardVO.setAccountId(payAccountVO.getId());
					bankCardVO.setBankName(bankInfoVO.getSubBankName());
					bankCardVO.setBankCardNo(shopUpdateVO.getBankAccount());
					bankCardVO.setBankCardHolder(shopUpdateVO.getCardHolder());
					bankCardVO.setBankMobile(shopUpdateVO.getMobile());
					bankCardVO.setStatus(StatusEnum.normal.getCode());
					bankCardVO.setUpdateId(shopUpdateVO.getUpdateId());
					bankCardVO.setUpdateDate(now);
					bankCardService.updateBankCard(bankCardVO);
				} else {
					bankCardVO = new BankCardVO();
					bankCardVO.setAccountId(payAccountVO.getId());
					bankCardVO.setBankName(bankInfoVO.getSubBankName());
					bankCardVO.setBankCardNo(shopUpdateVO.getBankAccount());
					bankCardVO.setBankCardHolder(shopUpdateVO.getCardHolder());
					bankCardVO.setBankMobile(shopUpdateVO.getMobile());
					bankCardVO.setUseTimes(0);
					bankCardVO.setSort(1);
					bankCardVO.setStatus(StatusEnum.normal.getCode());
					bankCardVO.setCreateId(shopUpdateVO.getUpdateId());
					bankCardVO.setCreateDate(now);
					bankCardService.addBankCard(bankCardVO);
				}
				
				
				
				// 更新关联信息
				/*payAccountManagerService.deletePayAccountManagerByAccountId(payAccountVO.getId(), shopUpdateVO.getUpdateId());
				PayAccountManagerVO payAccountManagerVO = new PayAccountManagerVO();
				payAccountManagerVO.setAccountId(payAccountVO.getId());
				payAccountManagerVO.setManagerType(AccountManagerTypeEnum.GENERIC.getCode());
				payAccountManagerVO.setUserId(userVO.getId());
				payAccountManagerVO.setCreateDate(now);
				payAccountManagerService.addPayAccountManager(payAccountManagerVO);*/
			}
			
			// 添加扫呗信息
			/*AreaCodeVO areaCodeVO = areaCodeService.getSaobeiAreaInfoByCountryCode(shopUpdateVO.getCountyCode());
			Industry industry = industryMapper.selectById(shopUpdateVO.getIndustryId());
			BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(shopUpdateVO.getBankId());
			SaoBeiShopInfoVO saobeiShopInfoVO = shopUpdateVO.getSaoBeiShopInfoVO();
			if (Objects.nonNull(saobeiShopInfoVO)) {
				saobeiShopInfoVO.setShopId(shop.getId());
				saobeiShopInfoVO.setUpdateDate(shopUpdateVO.getUpdateDate());
				saobeiShopInfoVO.setUpdateId(shopUpdateVO.getUpdateId());

				saobeiShopInfoVO.setMerchantProvince(areaCodeVO.getProvince());
				saobeiShopInfoVO.setMerchantProvinceCode(areaCodeVO.getProvinceCode());
				saobeiShopInfoVO.setMerchantCity(areaCodeVO.getCity());
				saobeiShopInfoVO.setMerchantCityCode(areaCodeVO.getCityCode());
				saobeiShopInfoVO.setMerchantCounty(areaCodeVO.getArea());
				saobeiShopInfoVO.setMerchantCountyCode(areaCodeVO.getAreaCode());

				saobeiShopInfoVO.setBusinessName(industry.getName());
				saobeiShopInfoVO.setBusinessCode(industry.getSbIndustryId().toString());

				saobeiShopInfoVO.setBankName(bankInfoVO.getSubBankName());
				saobeiShopInfoVO.setBankNo(bankInfoVO.getSubBankNo());

				saobeiShopInfoVO.setRateCode(industry.getRateCode());

				int saobeiRet = saoBeiShopInfoServicce.updateSaoBeiShopInfo(saobeiShopInfoVO);
				if (saobeiRet != 1) {
					throw new QmdException("店铺信息更新失败");
				}
			}*/
		}
	}

	/**
	 * 商家主动申请关闭店铺
	 * 
	 * @return int(受影响的行数)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int closeShopById(Long shopId) throws QmdException {
		Shop shop = shopMapper.selectById(shopId);
		if (shop == null) {
			throw new QmdException("店铺不存在");
		} else {
			shop.setStatus(ShopStatusEnum.APPLYCLOSE.getCode());
			int i = shopMapper.updateById(shop);
			if (i >= 1) {
				return i;
			} else {
				throw new QmdException("操作失败");
			}
		}
	}

	/**
	 * 模糊查询店铺列表（分页）
	 */
	/*
	 * @Override public PageInfo<ShopVO> getShopListLike(String inputInfo, int
	 * pageNum, int pageSize) { StringBuffer input=new StringBuffer();
	 * input.append("%"); input.append(inputInfo); input.append("%"); //开始分页
	 * PageHelper.startPage(pageNum, pageSize); //条件查询 List<ShopVO> shopVOList =
	 * shopMapper.getShopListLike(input.toString()); //封装分页结果 PageInfo<ShopVO>
	 * pageInfo = new PageInfo<>(shopVOList);
	 * pageInfo.setTotal(pageInfo.getTotal()); //返回分页对象 return pageInfo; }
	 */

	/**
	 * 动态查询店铺
	 * 
	 * @throws QmdException
	 */
	@Override
	public PageInfo<ShopBasicVO> getShopDynamic(ShopDynamicVO shopDynamicVO, int pageNum, int pageSize){
		if (shopDynamicVO.getSortByDistance() != null && shopDynamicVO.getSortByCreateTime() != null) {
			throw new QmdException("仅支持单字段排序");
		}
		if (shopDynamicVO.getSortByDistance() != null
				&& (ObjectUtils.isNotBlank(shopDynamicVO.getUserLatitude()) 
						|| ObjectUtils.isNotBlank(shopDynamicVO.getUserLongitude()))
				) {
			throw new QmdException("获取用户定位信息失败");
		}
		if(shopDynamicVO.getLevel()!=null && ObjectUtils.isNotBlank(shopDynamicVO.getCode())) {
			throw new QmdException("根据省、市、区、乡镇编码查询店铺参数不完整");
		}
		if(ObjectUtils.isNotBlank(shopDynamicVO.getSalemanName())) {
			UserVO userVO = userService.getUserByUsername(shopDynamicVO.getSalemanName());
			if(userVO ==null) {
				return null;
			}
			shopDynamicVO.setSalemanId(userVO.getId());
		}
		// 开始分页
		Page<ShopBasicVO> startPage = PageHelper.startPage(pageNum, pageSize);
		// 条件查询
		List<ShopBasicVO> shopDynamicVOList = shopMapper.getShopDynamic(shopDynamicVO);
		// 封装分页结果
		PageInfo<ShopBasicVO> pageInfo = new PageInfo<>(shopDynamicVOList);
		pageInfo.setTotal(startPage.getTotal());
		// 返回分页对象
		return pageInfo;
	}

	/**
	 * 查询总店管理员管理的店铺列表
	 * 
	 * @param shopIds
	 * @return
	 */
	@Override
	public List<ShopBasicVO> getShopByManager(List<Long> shopIds) {
		return shopMapper.getShopByManager(shopIds);
	}

	/**
	 * 查询总店管理员管理的店铺列表
	 * 
	 * @param shopUserInfoVOList
	 * @return
	 */
	@Override
	public List<ShopBasicVO> getUserShopByManager(List<ShopUserInfoVO> shopUserInfoVOList) {
		return shopMapper.getUserShopByManager(shopUserInfoVOList);
	}

	/**
	 * 添加店铺图片
	 * 
	 * @param shopPictureVO
	 * @param shopId
	 * @returngetAllIndustry
	 */
	@Transactional(rollbackFor = Exception.class)
	public ShopPictureVO addShopPicture(ShopPictureVO shopPictureVO) {
		AssertUtil.isNull(shopPictureVO, "ShopServiceImpl|addShopPicture|传入参数shopPictureVO为空");
		ShopPicture shopPicture = new ShopPicture();
		shopPicture.setImageType(shopPictureVO.getImageType());
		shopPicture.setShopId(shopPictureVO.getShopId());
		shopPicture.setName(shopPictureVO.getImageName());
		shopPicture.setImageUrl(shopPictureVO.getImageUrl());
		shopPicture.setSkipUrl(shopPictureVO.getSkipUrl());
		shopPicture.setSuffix(shopPictureVO.getSuffix());
		shopPicture.setImageSize(shopPictureVO.getImageSize());
		shopPicture.setIntro(shopPictureVO.getIntro());
		shopPicture.setSort(shopPictureVO.getImageSort());
		shopPicture.setStatus(shopPictureVO.getStatus());
		shopPicture.setCreateDate(shopPictureVO.getCreateDate());
		if (!checkCUD(shopPictureMapper.insert(shopPicture))) {
			log.error("添加店铺图片失败 刷入数据库影响行数不为1");
			throw new QmdException("添加店铺图片失败");
		}
		shopPictureVO.setId(shopPicture.getId());
		return shopPictureVO;
	}

	@Override
	public List<ShopSimpleVO> getShopSimpleInfoByMangerUserId(Long userId) {
		List<Long> shopIdList = shopUserService.getShopIdListByUserId(userId);
		if (!CollectionUtils.isEmpty(shopIdList)) {
			List<ShopBasicVO> basicVOList = getShopByManager(shopIdList);
			if (!CollectionUtils.isEmpty(basicVOList)) {
				return basicVOList.stream().map((vo) -> new ShopSimpleVO(vo.getId(), vo.getName()))
						.collect(Collectors.toList());
			}
		}
		return null;
	}

	@Override
	public List<ShopBasicVO> getShopBasicInfoByMangerUserId(Long userId) {
		List<Long> shopIdList = shopUserService.getShopIdListByUserId(userId);
		if (!CollectionUtils.isEmpty(shopIdList)) {
			return getShopByManager(shopIdList);
		}
		return null;
	}

	@Override
	public ShopBasicInfoVO getShopBasicInfoByShopId(Long shopId) {
		return shopMapper.getShopBasicInfoByShopId(shopId);
	}

	/**
	 * 根据省市区编号和级别查询店铺
	 * 
	 * @param countyCode
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<ShopBasicVO> getShopByCode(Integer code,Integer level,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ShopBasicVO> shopBasicVOList = shopMapper.getShopByCodeAndLevel(code,level);
		PageInfo<ShopBasicVO> pageInfo = new PageInfo<>(shopBasicVOList);
		pageInfo.setTotal(pageInfo.getTotal());
		return pageInfo;
	}

	/**
	 * 审核更新店铺信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int auditUpdateShop(Long ShopId, Byte status) {
		Byte ShopStatus = shopMapper.getShopStatus(ShopId);
		Shop shop = new Shop();
		if (ShopStatus == null) {
			throw new QmdException("店铺不存在");
		}
		if (!ShopStatus.equals(ShopStatusEnum.LOCALUPDATECOMMIT.getCode())) {
			throw new QmdException("该店铺不处于更新待审核状态");
		}
		switch (status) {
		case 0:
			shop.setStatus(ShopStatusEnum.LOCALUPDATEUNPASS.getCode());
			break;
		case 1:
			shop.setStatus(ShopStatusEnum.PASS.getCode());
			break;

		default:
			throw new QmdException("店铺状态设置错误");
		}
		shop.setId(ShopId);
		return shopMapper.updateById(shop);
	}

	/**
	 * 审核新建店铺
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public Result<Void> auditAddShop(ShopAuditRecordVO vo) {
		Result<Void> result = new Result<Void>();
		if (ShopAuditStatusEnum.PASS.getCode().equals(vo.getStatus()) ) {
			shopMapper.updateShopStatus(vo.getShopId(), ShopStatusEnum.PASS.getCode());
			// 处理资格卷使用
			QualificationTicketVO qualificationTicket = qualificationTicketService.getQualificationTicketByShopId(vo.getShopId());
			if(qualificationTicket != null) {
				qualificationTicketService.useTicketByShopId(vo.getShopId());
			}
		} else {
			shopMapper.updateShopStatus(vo.getShopId(), ShopStatusEnum.UNPASS.getCode());
		}
		// 插入审核记录
		Date now = new Date();
		vo.setCreateDate(now);
		vo.setAuditDate(now);
		shopAuditRecordService.addShopAuditRecord(vo);
		return result;
		/*// 当前审核状态
		if (ShopAuditStatusEnum.PASS.getCode() == vo.getStatus()) {
			ShopBasicVO shop = shopMapper.getShopBasicById(vo.getShopId());
			// 创建新商户
			if (ShopStatusEnum.CREATECOMMIT.getCode() == shop.getStatus()) {
				createMerchatAudit(vo.getShopId(), result);
				// 更新商户信息
			} else if (ShopStatusEnum.UPDATECOMMIT.getCode() == shop.getStatus()) {
				updateMerchantAudit(vo.getShopId(), shop.getMerchantNo(), result);
			}
		} else {
			int upShopR = shopMapper.updateShopStatus(vo.getShopId(), ShopStatusEnum.UNPASS.getCode());
			if (upShopR == 1) {
				result.setMessage("操作成功");
			} else {
				throw new QmdException(SHErrorCode.SH1000.getErrorCode(), "审核失败, 系统无法找到店铺信息");
			}
		}*/
	}

	/**
	 * 审核初次创建店铺信息
	 * 
	 * @param shopId
	 */
	@SuppressWarnings("unused")
	private void createMerchatAudit(Long shopId, Result<Void> result) {
		// 查询
		SaoBeiShopInfoVO infoVo = saoBeiShopInfoServicce.getSaoBeiShopInfo(shopId);
		if (Objects.nonNull(infoVo)) {
			// 在扫呗端创建店铺
			SaoBeiShopAllInfoVO allInfoVo = CopyBeanUtil.copyBean(infoVo, SaoBeiShopAllInfoVO.class);
			// 验证重名
			BaseMerchantResVO checkNameResult = saoBeiMerchantService.checkMerchantName(allInfoVo.getMerchantName());
			// 可以使用商户名
			if (Objects.nonNull(checkNameResult) && checkNameResult.isSuccess()) {
				// 发送创建商户请求
				CreateMerchantResVO crResult = saoBeiMerchantService.createMerchant(allInfoVo);
				if (Objects.nonNull(crResult) && crResult.isSuccess()) {
					// 保存商户信息
					// 保存商户号信息
					//int upShopR = shopMapper.updateShopStatus(shopId, ShopStatusEnum.PASSCOMMIT.getCode());

					int upShopExtendR = shopExtendMapper.update(null, new UpdateWrapper<ShopExtend>()
							.set("merchant_no", crResult.getMerchantNo()).eq("shop_id", shopId));

					/*if (upShopR == 1 && upShopExtendR == 1) {
						result.setMessage("操作成功, 商户信息已提交到扫呗.");
					} else {
						throw new QmdException(SHErrorCode.SH1000.getErrorCode(), "审核失败, 系统无法找到店铺信息");
					}*/
				}
			} else {
				log.info("ShopServiceImpl|auditAddShop|shopId:" + shopId + "商户名在扫呗重名");
				throw new QmdException(SHErrorCode.SH1000.getCode(), "商户名重名, 请更换商户名");
			}
		}
	}

	/**
	 * 更新商户信息去扫呗
	 * 
	 * @param shopId
	 * @param result
	 */
	@SuppressWarnings("unused")
	private void updateMerchantAudit(Long shopId, String merchantNo, Result<Void> result) {
		// 查询
		SaoBeiShopInfoVO infoVo = saoBeiShopInfoServicce.getSaoBeiShopInfo(shopId);
		if (Objects.nonNull(infoVo)) {
			// 在扫呗端创建店铺
			SaoBeiShopAllInfoVO allInfoVo = CopyBeanUtil.copyBean(infoVo, SaoBeiShopAllInfoVO.class);
			allInfoVo.setMerchantNo(merchantNo);
			// 发送更新商户请求
			CreateMerchantResVO crResult = saoBeiMerchantService.updateMerchant(allInfoVo);
			if (Objects.nonNull(crResult) && crResult.isSuccess()) {
				// 保存商户信息
				// 保存商户号信息
				//int upShopR = shopMapper.updateShopStatus(shopId, ShopStatusEnum.PASSCOMMIT.getCode());

				int upShopExtendR = shopExtendMapper.update(null, new UpdateWrapper<ShopExtend>()
						.set("merchant_no", crResult.getMerchantNo()).eq("shop_id", shopId));

				/*if (upShopR == 1 && upShopExtendR == 1) {
					result.setMessage("操作成功, 商户信息已提交到扫呗.");
				} else {
					throw new QmdException(SHErrorCode.SH1000.getErrorCode(), "审核失败, 系统无法找到店铺信息");
				}*/
			}
		}
	}

	@Override
	public List<Integer> getPlatformOpenMerchatRegist() {
		SysPropertiesVO vo = sysPropertiesService
				.getSysPropertiesInfoById(SysPropertiresEnums.PLATFORMOPENMERCHAT.getId());
		if (Objects.nonNull(vo) && Objects.nonNull(vo.getId())) {
			String[] temps = vo.getValue().split(",");
			List<Integer> reuslt = Lists.newArrayListWithExpectedSize(temps.length);
			for (String temp : temps) {
				reuslt.add(Integer.parseInt(temp));
			}
			return reuslt;
		}
		return Lists.newArrayList();
	}

	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int updateShopStatus(Long shopId, Byte status) {
		Shop shop = shopMapper.selectById(shopId);
		if(shop == null) {
			log.error("updateShopStatus|该店铺不存在,shopId:"+shopId);
			throw new QmdException("该店铺不存在");
		}
		/*EnumSet<ShopStatusEnum> enumSet = EnumSet.allOf(ShopStatusEnum.class);
		if(!enumSet.contains(status)) {
			log.error("updateShopStatus|店铺状态参数错误,shopId:" + shopId + ";status: " + status);
			throw new QmdException("店铺状态参数错误");
		}*/
		//判断状态参数是否在店铺状态枚举中
		boolean include = false;
		for(ShopStatusEnum statusEnum: ShopStatusEnum.values()) {
			if(statusEnum.getCode().equals(status)) {
				include = true;
				break;
			}
		}
		if(!include) {
			log.error("updateShopStatus|店铺状态参数错误,shopId:" + shopId + ";status: " + status);
			throw new QmdException("店铺状态参数错误");
		}
		shop.setStatus(status);
		return shopMapper.updateById(shop);
	}

	/**
	 * 根据商户号查询店铺信息
	 * 
	 * @param merchantNo
	 * @return
	 */
	@Override
	public ShopBasicVO getShopByMerchantNo(String merchantNo) {
		ShopBasicVO shopBasicVO = shopMapper.getShopByMerchantNo(merchantNo);
		return shopBasicVO;
	}

	
    /**
     * 根据店铺状态、店铺名、业务员手机号查询店铺
     * @param shopQueryParamVO
     * @return
     */
	@Override
	public PageInfo<ShopBasicVO> getShopByShopQuery(ShopQueryParamVO shopQueryParamVO) {
		//当业务员手机号不为空时
		if(shopQueryParamVO.getSalemanMobile() !=null && !shopQueryParamVO.getSalemanMobile().equals("")) {
			//根据业务员手机号查询业务员信息
			UserVO userVO = userService.getUserByUsername(shopQueryParamVO.getSalemanMobile());
			if(userVO != null) {
				//当查询到的用户信息不为空，则将参数shopQueryParamVO中业务员id设置为该用户的id
				shopQueryParamVO.setSalemanId(userVO.getId());
			}else {
				//当查询到的用户信息为空时，返回null
				return null;
			}
		}
		PageHelper.startPage(shopQueryParamVO.getPageNum(), shopQueryParamVO.getPageSize());
		List<ShopBasicVO> list= shopMapper.getShopByShopQuery(shopQueryParamVO);
		PageInfo<ShopBasicVO> pageInfo = new PageInfo<ShopBasicVO>(list);
		return pageInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addShopByAdminManager(ShopVO shopVO) {
		// 手机号在user表中是否存在
		UserVO userVO = userService.getUserByUsername(shopVO.getMobile());
		if (userVO == null || userVO.getId() == null) {
			log.error("addShopByAdminManager|用户账号未在平台注册 operator" + shopVO.getCreateId());
			throw new QmdException("用户账号未在平台注册，请先注册为平台用户");
		}

		Date now = new Date();
		// 保存店铺基础信息
		Shop shop = CopyBeanUtil.copyBean(shopVO, Shop.class);
		shop.setCreateDate(now);
		shop.setUpdateDate(now);
		shop.setShopAdminId(userVO.getId());
		shop.setStatus(ShopStatusEnum.PASS.getCode());
		//添加业务员
		if(shopVO.getSalemanId() != null) {
			SalemanVO salemanById = salemanService.getSalemanByUserId(shopVO.getSalemanId());
			if(salemanById == null) {
				log.error("该业务员不存在，id:" +shopVO.getSalemanId());
				throw new QmdException("该业务员不存在");
			}
			shop.setSalemanId(salemanById.getUserId());
		}
		if (!checkCUD(shopMapper.insert(shop))) {
			log.error("addShopByAdminManager|添加店铺失败 刷入数据库后 影响条数不为1 operator" + shopVO.getCreateId());
			throw new QmdException("添加店铺失败");
		}
		
		// 保存店铺扩展信息
		ShopExtend shopExtend = CopyBeanUtil.copyBean(shopVO, ShopExtend.class);
		shopExtend.setShopId(shop.getId());
		if (shopExtend.getMembersLimit() == null) {
			SysPropertiesVO sysPropertiesVO = sysPropertiesService.getSysPropertiesInfoById(SysPropertiresEnums.SHOPMAXMEMBERS.getId());
			// 设置为平台默认限制数量
			shopExtend.setMembersLimit(Integer.parseInt(sysPropertiesVO.getValue()));
		}
		if (!checkCUD(shopExtendMapper.insert(shopExtend))) {
			log.error("addShopByAdminManager|添加店铺扩展表失败 刷入数据库后 影响条数不为1 operator" + shopVO.getCreateId());
			throw new QmdException("添加店铺扩展表失败");
		}
		
		// 查询店铺相关的行业信息，将数据插入到店铺费率表
		IndustryVO industryVO = industryService.getIndustryVOById(shopVO.getIndustryId());
		if (null == industryVO) {
			throw new QmdException("行业信息不存在");
		}
		ShopProfitVO shopProfitVO = new ShopProfitVO();
		shopProfitVO.setSbRateCode(industryVO.getRateCode());
		shopProfitVO.setRate(industryVO.getFee());
		shopProfitVO.setShopId(shop.getId());
		shopProfitService.updateShopProfit(shopProfitVO);
		// 保存店铺图片
		if (!CollectionUtils.isEmpty(shopVO.getShopPictureVOList())) {
			for (ShopPictureVO pic : shopVO.getShopPictureVOList()) {
				if (Objects.nonNull(pic)) {
					if (ShopPictureTypeEnum.Logo.getCode().equals(pic.getImageType())) {
						shop.setLogo(pic.getImageUrl());
						shopMapper.updateById(shop);
					}
					pic.setShopId(shop.getId());
					pic.setCreateDate(now);
					pic.setStatus(StatusEnum.normal.getCode());
					addShopPicture(pic);
				}
			}
		}

		// 保存店长用户角色信息
		RoleVO roleVO = roleService.getShopManagerRoleInfo();
		AddUserRoleVO addUserRole = new AddUserRoleVO();
		addUserRole.setStatus(StatusEnum.normal.getCode());
		addUserRole.setSysRoleId(roleVO.getId());
		addUserRole.setSysUserId(userVO.getId());
		AddUserRoleVO addResult = userRoleService.addUserRole(addUserRole);

		// 保存店铺人员(总店管理员)信息
		ShopUserVO shopUserVO = new ShopUserVO();
		shopUserVO.setUserRoleId(addResult.getId());
		shopUserVO.setUserId(userVO.getId());
		shopUserVO.setShopId(shop.getId());
		shopUserVO.setUserName(shopVO.getMobile());
		shopUserVO.setCreateDate(now);
		shopUserVO.setUpdateDate(now);
		shopUserVO.setCreateId(shopVO.getCreateId());
		shopUserVO.setUpdateId(shopVO.getCreateId());
		shopUserVO.setStatus(StatusEnum.normal.getCode());
		shopUserService.addShopUser(shopUserVO);

		// 创建店铺账户
		BigDecimal zero = new BigDecimal(0);
		PayAccountVO payAccountVO = new PayAccountVO();
		payAccountVO.setShopId(shop.getId());
		payAccountVO.setBalance(zero);
		payAccountVO.setSettBalance(zero);
		payAccountVO.setUnbalance(zero);
		payAccountVO.setName(shop.getName());
		payAccountVO.setType(AccountTypeEnum.Shop.getCode());
		payAccountVO.setSecurityMoney(zero);
		payAccountVO.setWithdrawStatus(WithdrawEnum.ABLE.getCode());
		payAccountVO.setStatus(StatusEnum.normal.getCode());
		payAccountVO.setCreateDate(now);
		payAccountVO.setCreateId(shopVO.getCreateId());
		PayAccountVO addPayAccount = payAccountService.addPayAccount(payAccountVO);
		
		// 创建银行卡信息
		BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(shopVO.getBankId());
		BankCardVO bankCardVO = new BankCardVO();
		bankCardVO.setAccountId(addPayAccount.getId());
		bankCardVO.setBankName(bankInfoVO.getSubBankName());
		bankCardVO.setBankCardNo(shopVO.getBankAccount());
		bankCardVO.setBankCardHolder(shopVO.getCardHolder());
		bankCardVO.setBankMobile(shopVO.getMobile());
		bankCardVO.setUseTimes(0);
		bankCardVO.setSort(1);
		bankCardVO.setStatus(StatusEnum.normal.getCode());
		bankCardVO.setCreateId(shopVO.getCreateId());
		bankCardVO.setCreateDate(now);
		bankCardService.addBankCard(bankCardVO);
		
		// 创建支付账户管理关联信息
		/*PayAccountManagerVO payAccountManagerVO = new PayAccountManagerVO();
		payAccountManagerVO.setAccountId(addPayAccount.getId());
		payAccountManagerVO.setManagerType(AccountManagerTypeEnum.GENERIC.getCode());
		payAccountManagerVO.setUserId(userVO.getId());
		payAccountManagerVO.setCreateDate(now);
		payAccountManagerService.addPayAccountManager(payAccountManagerVO);*/
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int updateShopByAdminManager(ShopUpdateVO shopUpdateVO) {
		Date now = new Date();
		// 手机号在user表中是否存在
		UserVO userVO = userService.getUserByUsername(shopUpdateVO.getMobile());
		if (userVO.getId() == null) {
			log.error("updateShopByAdminManager|用户账号未在平台注册 operator" + shopUpdateVO.getUpdateId());
			throw new QmdException("用户账号未在平台注册，请先注册为平台用户");
		}

		// 更新店铺基础信息
		Shop shop = shopMapper.selectById(shopUpdateVO.getId());
		if (shop != null) {
			if (!shop.getName().equals(shopUpdateVO.getName())) {
				if (existShopName(shopUpdateVO.getName())) {
					log.error("updateShopByAdminManager|店铺名称已存在operator" + shopUpdateVO.getUpdateId());
					throw new QmdException("店铺名称已存在, 请修改");
				}
			}
			//添加业务员
			if(shopUpdateVO.getSalemanId() != null) {
				SalemanVO salemanById = salemanService.getSalemanByUserId(shopUpdateVO.getSalemanId());
				if(salemanById == null) {
					log.error("该业务员不存在，id:" +shopUpdateVO.getSalemanId());
					throw new QmdException("该业务员不存在");
				}
				shop.setSalemanId(salemanById.getUserId());
			}
			shopUpdateVO.setUpdateDate(now);
			shop = CopyBeanUtil.copyBean(shopUpdateVO, Shop.class);
			// 修改店铺为待审核状态
			shop.setShopAdminId(userVO.getId());
			shop.setStatus(ShopStatusEnum.PASS.getCode());
			int shopRet = shopMapper.updateById(shop);
			if (shopRet != 1) {
				log.error("updateShopByAdminManager|更新店铺信息失败, 更新店铺主表信息返回影响行数 operator:" + shopUpdateVO.getUpdateId());
				throw new QmdException("店铺信息更新失败");
			}
			
			// 更新店铺扩展信息
			ShopExtend oldShopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shop.getId()));
			if (Objects.nonNull(oldShopExtend)) {
				ShopExtend shopExtendEntity = CopyBeanUtil.copyBean(shopUpdateVO, ShopExtend.class);
				shopExtendEntity.setId(oldShopExtend.getId());
				if (!checkCUD(shopExtendMapper.updateById(shopExtendEntity))) {
					log.error("updateShopByAdminManager|更新店铺信息失败, 更新店铺扩展信息失败  operator:" + shopUpdateVO.getUpdateId());
					throw new QmdException("店铺信息更新失败");
				}
				
			}
			
			// 保存店铺-行业关联信息
			/*
			 * int IndustryRet = shopIndustryRelationMapper.update(new
			 * ShopIndustryRelation(), new UpdateWrapper<ShopIndustryRelation>()
			 * .set("industry_id", shopUpdateVO.getIndustryId()).eq("shop_id",
			 * shopUpdateVO.getId())); if (IndustryRet != 1) {
			 * log.error("updateShopByAdminManager|更新店铺信息失败, 更新行业关联表信息返回影响行数  operator:" +
			 * shopUpdateVO.getUpdateId()); throw new QmdException("店铺信息更新失败"); }
			 */
			
			
			// 图片
			if (!CollectionUtils.isEmpty(shopUpdateVO.getShopPictureVOList())) {
				Set<Byte> deletedType = Sets.newHashSetWithExpectedSize(13);
				for (ShopPictureVO pic : shopUpdateVO.getShopPictureVOList()) {
					if (Objects.nonNull(pic)) {
						if (!deletedType.contains(pic.getImageType())) {
							shopPictureMapper.deleteAlbumsByShopIdAndType(shop.getId(), pic.getImageType());
						}
						if (ShopPictureTypeEnum.Logo.getCode().equals(pic.getImageType())) {
							shop.setLogo(pic.getImageUrl());
							shopMapper.updateById(shop);
						}
						pic.setShopId(shop.getId());
						pic.setCreateDate(now);
						pic.setStatus(StatusEnum.normal.getCode());
						addShopPicture(pic);
						deletedType.add(pic.getImageType());
					}
				}
			}

			// 判断是否修改管理账号
			if (!shopUserService.memberExist(userVO.getId())) {
				// 解除老店管理的权限和绑定关系
				List<ShopUserVO> shopuserList = shopUserService.getShopUserListByShopId(shop.getId());
				if (!CollectionUtils.isEmpty(shopuserList)) {
					shopuserList.forEach(shopUser -> {
						if (Objects.nonNull(shopUser)) {
							shopUserService.unbindMemberShopUser(shopUser.getId(), shopUpdateVO.getUpdateId());
						}
					});
				}
				// 保存店长用户角色信息
				RoleVO roleVO = roleService.getShopManagerRoleInfo();
				AddUserRoleVO addUserRole = new AddUserRoleVO();
				addUserRole.setStatus(StatusEnum.normal.getCode());
				addUserRole.setSysRoleId(roleVO.getId());
				addUserRole.setSysUserId(userVO.getId());
				AddUserRoleVO addResult = userRoleService.addUserRole(addUserRole);
				
				// 保存店铺人员(总店管理员)信息
				ShopUserVO shopUserVO = new ShopUserVO();
				shopUserVO.setUserId(userVO.getId());
				shopUserVO.setUserRoleId(addResult.getId());
				shopUserVO.setShopId(shop.getId());
				shopUserVO.setUserName(shopUpdateVO.getMobile());
				shopUserVO.setCreateDate(now);
				shopUserVO.setUpdateDate(now);
				shopUserVO.setCreateId(shopUpdateVO.getUpdateId());
				shopUserVO.setUpdateId(shopUpdateVO.getUpdateId());
				shopUserVO.setStatus(StatusEnum.normal.getCode());
				shopUserService.addShopUser(shopUserVO);
			}

			// 修改店铺账户
			PayAccountVO payAccountVO = payAccountService.getPayAccountByShopId(shopUpdateVO.getId());
			if (Objects.nonNull(payAccountVO)) {
				payAccountVO.setShopId(shop.getId());
				payAccountVO.setWithdrawStatus(WithdrawEnum.ABLE.getCode());
				payAccountVO.setStatus(StatusEnum.normal.getCode());
				payAccountVO.setCreateDate(now);
				payAccountVO.setCreateId(shopUpdateVO.getUpdateId());
				payAccountService.updatePayAccount(payAccountVO);
				
				// 更新银行卡信息
				BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(shopUpdateVO.getBankId());
				BankCardVO bankCardVO = bankCardService.getBankCardByAccountId(payAccountVO.getId());
				if (Objects.nonNull(bankCardVO)) {
					bankCardVO.setAccountId(payAccountVO.getId());
					bankCardVO.setBankName(bankInfoVO.getSubBankName());
					bankCardVO.setBankCardNo(shopUpdateVO.getBankAccount());
					bankCardVO.setBankCardHolder(shopUpdateVO.getCardHolder());
					bankCardVO.setBankMobile(shopUpdateVO.getMobile());
					bankCardVO.setStatus(StatusEnum.normal.getCode());
					bankCardVO.setUpdateId(shopUpdateVO.getUpdateId());
					bankCardVO.setUpdateDate(now);
					bankCardService.updateBankCard(bankCardVO);
				} else {
					bankCardVO = new BankCardVO();
					bankCardVO.setAccountId(payAccountVO.getId());
					bankCardVO.setBankName(bankInfoVO.getSubBankName());
					bankCardVO.setBankCardNo(shopUpdateVO.getBankAccount());
					bankCardVO.setBankCardHolder(shopUpdateVO.getCardHolder());
					bankCardVO.setBankMobile(shopUpdateVO.getMobile());
					bankCardVO.setUseTimes(0);
					bankCardVO.setSort(1);
					bankCardVO.setStatus(StatusEnum.normal.getCode());
					bankCardVO.setCreateId(shopUpdateVO.getUpdateId());
					bankCardVO.setCreateDate(now);
					bankCardService.addBankCard(bankCardVO);
				}
				
				// 更新关联信息
				/*payAccountManagerService.deletePayAccountManagerByAccountId(payAccountVO.getId(), shopUpdateVO.getUpdateId());
				PayAccountManagerVO payAccountManagerVO = new PayAccountManagerVO();
				payAccountManagerVO.setAccountId(payAccountVO.getId());
				payAccountManagerVO.setManagerType(AccountManagerTypeEnum.GENERIC.getCode());
				payAccountManagerVO.setUserId(userVO.getId());
				payAccountManagerVO.setCreateDate(now);
				payAccountManagerService.addPayAccountManager(payAccountManagerVO);*/
			}
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int updateShopByShopManager(ManagerShopUpdateVO shopUpdateVO) {
		// 更新店铺基础信息
		Date now = new Date();
		Shop shop = shopMapper.selectById(shopUpdateVO.getId());
		if (shop != null) {
			shopUpdateVO.setUpdateDate(now);
			shop = CopyBeanUtil.copyBean(shopUpdateVO, Shop.class);
			// 修改店铺为待审核状态
			shop.setStatus(ShopStatusEnum.LOCALUPDATECOMMIT.getCode());
			int shopRet = shopMapper.updateById(shop);
			if (shopRet != 1) {
				log.error("updateShopByShopManager|更新店铺信息失败, 更新店铺主表信息返回影响行数不为1  operator:" + shopUpdateVO.getUpdateId());
				throw new QmdException("店铺信息更新失败");
			}
			
			// 更新店铺扩展信息
			ShopExtend oldShopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shop.getId()));
			if (Objects.nonNull(oldShopExtend)) {
				ShopExtend shopExtendEntity = CopyBeanUtil.copyBean(shopUpdateVO, ShopExtend.class);
				shopExtendEntity.setId(oldShopExtend.getId());
				if (!checkCUD(shopExtendMapper.updateById(shopExtendEntity))) {
					log.error("updateShopByShopManager|更新店铺信息失败, 更新店铺扩展信息返回影响行数不为1  operator:" + shopUpdateVO.getUpdateId());
					throw new QmdException("店铺信息更新失败");
				}
			}
			
			// 图片
			if (!CollectionUtils.isEmpty(shopUpdateVO.getShopPictureVOList())) {
				Set<Byte> deletedType = Sets.newHashSetWithExpectedSize(13);
				for (ShopPictureVO pic : shopUpdateVO.getShopPictureVOList()) {
					if (Objects.nonNull(pic)) {
						if (!deletedType.contains(pic.getImageType())) {
							shopPictureMapper.deleteAlbumsByShopIdAndType(shop.getId(), pic.getImageType());
						}
						if (ShopPictureTypeEnum.Logo.getCode().equals(pic.getImageType())) {
							shop.setLogo(pic.getImageUrl());
							shopMapper.updateById(shop);
						}
						pic.setShopId(shop.getId());
						pic.setCreateDate(now);
						pic.setStatus(StatusEnum.normal.getCode());
						addShopPicture(pic);
						deletedType.add(pic.getImageType());
					}
				}
			}
		}
		return 0;
	}

    /**
     * 修改店铺营业额（用数据库内营业额值与传入营业额值累加）
     * @param shopId
     * @param totalIncome
     * @return
     */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public BigDecimal updateTotalIncome(Long shopId, BigDecimal totalIncome) {
		ShopExtend shopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shopId));
		if(shopExtend == null) {
			throw new QmdException("该店铺不存在");
		}
		shopExtend.setTotalIncome(shopExtend.getTotalIncome().add(totalIncome));
		int i = shopExtendMapper.updateById(shopExtend);
		if( i!=1 ) {
			throw new QmdException("更新营业额失败");
		}
		return shopExtend.getTotalIncome();
	}

	
    /**
     * 修改店铺销量（用数据库内销量值与传入营业额值累加）
     * @param shopId
     * @param salesVolume
     * @return
     */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public Long updateSalesVolume(Long shopId, Long salesVolume) {
		ShopExtend shopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shopId));
		if(shopExtend == null) {
			throw new QmdException("该店铺不存在");
		}
		shopExtend.setSalesVolume(shopExtend.getSalesVolume()+salesVolume);
		int i = shopExtendMapper.updateById(shopExtend);
		if( i!=1 ) {
			throw new QmdException("更新营业额失败");
		}
		return shopExtend.getSalesVolume();
	}

	@Override
	public ShopInfoVO getShopInfoById(Long shopId) {
		ShopInfoVO shopInfo = shopMapper.getShopInfoById(shopId);
		if (Objects.nonNull(shopInfo)) {
			// 只查询 商家相册 和logo 图片
//			ArrayList<Byte> picType = Lists.newArrayList(ShopPictureTypeEnum.MerchantAlbum.getCode(),
//					ShopPictureTypeEnum.Logo.getCode(),
//					ShopPictureTypeEnum.OtherQualifications.getCode(),
//					ShopPictureTypeEnum.DoorPhotos.getCode(),
//					ShopPictureTypeEnum.BusinessLicense.getCode(),
//					ShopPictureTypeEnum.Reception.getCode());
			shopInfo.setShopPictureVOList(shopPictureService.getShopPictureByShopId(shopId));
			VipDiscountInfoVO vipDiscountInfo = vipDiscountInfoService.getVipDiscountInfoByShopId(shopId);
			if(vipDiscountInfo != null) {
				shopInfo.setDiscount(vipDiscountInfo.getDiscountMoney());
			}else {
				shopInfo.setDiscount(new BigDecimal(1000));
			}
		}
		
		  ShopExtendVO shopExtendByShopId = ShopGameSwitchService.getShopExtendByShopId(shopId);
		  if(null!=shopExtendByShopId) {
			  
			  shopInfo.setGameSwitch(shopExtendByShopId.getGameSwitch());
		  }
		 
		
		return shopInfo;
	}

	@Override
	public List<ShopBasicInfoVO> getShopBasicInfoBySalemanId(Long salemanId) {
		List<Shop> shopList = shopMapper.selectList(new QueryWrapper<Shop>().eq("saleman_id", salemanId)) ;
		if(ObjectUtils.isEmpty(shopList)) {
			return null;
		}
		return CopyBeanUtil.copyList(shopList, ShopBasicInfoVO.class);
	}


	/**
	 * 设置店铺是否开启打烊，以及打烊时间
	 * @param shopId
	 * @param status 1开启；0关闭；
	 * @param openTime
	 * @param restTime
	 */
	@Override
	public void setIsOpenShut(Long shopId, Byte status, Date openTime, Date restTime) {
		ShopExtend shopExtend = shopExtendMapper.selectOne(new QueryWrapper<ShopExtend>().eq("shop_id", shopId));
		if(!Objects.nonNull(shopExtend)) {
			log.error("setIsOpenShut|设置店铺打烊时，根据店铺id查询店铺扩展表信息返回null，shopId:"+shopId);
			throw new QmdException("该店铺不存在");
		}
		shopExtend.setIsOpenShut(status);
		if(StatusEnum.TRUE.getCode().equals(status)) {
			//当设置为开启打烊时，开始营业时间和结束营业时间不能为空
			if(openTime == null || restTime == null) {
				log.error("setIsOpenShut|开启打烊时，开始营业时间和结束营业时间不能为空，openTime:"+openTime +";restTime:"+restTime);
				throw new QmdException("开启打烊时，开始营业时间和结束营业时间不能为空");
			}
			shopExtend.setOpenTime(openTime);
			shopExtend.setRestTime(restTime);
		}
		if(!checkCUD(shopExtendMapper.updateById(shopExtend))) {
			log.error("设置店铺是否开启打烊，以及打烊时间 影响条数不为1");
			throw new QmdException("设置店铺是否开启打烊，以及打烊时间失败");
		}
	}

	@Override
	public List<ShopBasicInfoVO> getShopIdsBySalemanUserId(Long userId) {
		List<Shop> list = shopMapper.selectList(new QueryWrapper<Shop>().eq("saleman_id", userId));
		return CopyBeanUtil.copyList(list, ShopBasicInfoVO.class);
	}

	
}
