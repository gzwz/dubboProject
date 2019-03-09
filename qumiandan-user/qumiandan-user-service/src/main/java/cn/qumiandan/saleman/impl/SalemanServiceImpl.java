package cn.qumiandan.saleman.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.enums.AddressLevelEnum;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.bankinfo.api.IBankInfoService;
import cn.qumiandan.bankinfo.vo.BankInfoVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.ParentDataEnum;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.enums.AccountTypeEnum;
import cn.qumiandan.payaccount.enums.WithdrawEnum;
import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.api.IUserRoleService;
import cn.qumiandan.role.vo.AddUserRoleVO;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.saleman.api.ISalemanService;
import cn.qumiandan.saleman.entity.Saleman;
import cn.qumiandan.saleman.enums.SalemanTypeEnums;
import cn.qumiandan.saleman.mapper.SalemanMapper;
import cn.qumiandan.saleman.vo.AddSalmanVO;
import cn.qumiandan.saleman.vo.SalemanAndRoleVO;
import cn.qumiandan.saleman.vo.SalemanAndUserParamVO;
import cn.qumiandan.saleman.vo.SalemanAndUserVO;
import cn.qumiandan.saleman.vo.SalemanVO;
import cn.qumiandan.saleman.vo.ShopAgentVO;
import cn.qumiandan.saleman.vo.UpdateSalmanVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.ticket.api.IQualificationTicketService;
import cn.qumiandan.ticket.enums.TicketStatusEnums;
import cn.qumiandan.ticket.vo.TicketNumVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统管理员实现类
 * 
 * @author lrj
 *
 */
@Slf4j
@Component
@Service(interfaceClass = ISalemanService.class)
public class SalemanServiceImpl implements ISalemanService {

	@Autowired
	private SalemanMapper salemanMapper;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IUserService userService;
	@Reference
	private IAddressService addressService;
	@Reference
	private ISysPropertiesService sysPropertiesService;

	@Reference
	private IPayAccountService payAccountService;

	@Reference
	private IBankInfoService bankInfoService;

	@Reference
	private IBankCardService bankCardService;

	@Autowired
	private IQualificationTicketService ticketService;

	@Autowired
	private IRoleService roleService;

	@Reference
	private IShopService shopService;
	
	/**
	 * 添加业务员、市代理、省代理
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public SalemanVO addSaleman(AddSalmanVO addSalmanVO) {
		UserVO userVO = userService.getUserByUsername(addSalmanVO.getUserName());
		if (userVO == null) {
			log.error("添加业务员、市代理、省代理 -->该用户不为平台用户,请先注册为平台用户 operator:" + addSalmanVO.getCreateId());
			throw new QmdException("该用户不为平台用户,请先注册为平台用户");
		}
		// 判断该用户是否已为平台业务员
		Saleman salemanByUserId = salemanMapper.selectOne(new QueryWrapper<Saleman>()
				.eq("user_id", userVO.getId()).eq("status", StatusEnum.normal.getCode()));
		if (salemanByUserId != null) {
			log.error("添加业务员、市代理、省代理  -->该用户已为平台业务员或市代理或省代理 operator:" + addSalmanVO.getCreateId());
			throw new QmdException("该用户已为平台业务员或市代理或省代理 ");
		}
		addSalmanVO.setUserId(userVO.getId());
		addSalmanVO.setCreateDate(new Date());
		SalemanVO salemanVO = new SalemanVO();
		switch (addSalmanVO.getType()) {
		case 1:
			addSalmanVO.setAccountType(AccountTypeEnum.Saleman.getCode());
			salemanVO = addSalemanInfo(addSalmanVO);
			break;
		case 2:
			addSalmanVO.setAccountType(AccountTypeEnum.CountryAgent.getCode());
			salemanVO = addCountryAgent(addSalmanVO);
			break;
		case 3:
			addSalmanVO.setAccountType(AccountTypeEnum.CityAgent.getCode());
			salemanVO = addCityAgent(addSalmanVO);

			break;
		case 4:
			addSalmanVO.setAccountType(AccountTypeEnum.ProvinceAgent.getCode());
			salemanVO = addProAgent(addSalmanVO);
			break;
		default:
			log.error("添加业务员 、省代理、市代理-->添加类型参数错误:" + addSalmanVO.getType());
			throw new QmdException("添加类型参数错误");
		}
		// 添加账户
		addPayAccount(addSalmanVO);
		return salemanVO;
	}

	/**
	 * 添加账户
	 */
	@Transactional(rollbackFor = QmdException.class)
	private void addPayAccount(AddSalmanVO addSalmanVO) {
		Date now = new Date();
		// 添加业务员账户
		BigDecimal zero = new BigDecimal(0);
		PayAccountVO account = new PayAccountVO();
		account.setName(addSalmanVO.getUserName());
		account.setType(addSalmanVO.getAccountType());
		account.setUserId(addSalmanVO.getUserId());
		account.setBalance(zero);
		account.setSettBalance(zero);
		account.setUnbalance(zero);
		account.setSecurityMoney(zero);
		account.setWithdrawStatus(WithdrawEnum.ABLE.getCode());
		account.setStatus(StatusEnum.normal.getCode());
		account.setCreateDate(now);
		account.setCreateId(addSalmanVO.getCreateId());
		PayAccountVO addPayAccount = payAccountService.addPayAccount(account);

		// 创建银行卡信息
		BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(addSalmanVO.getBankId());
		BankCardVO bankCardVO = new BankCardVO();
		bankCardVO.setAccountId(addPayAccount.getId());
		bankCardVO.setBankName(bankInfoVO.getSubBankName());
		bankCardVO.setBankCardNo(addSalmanVO.getBankCardNo());
		bankCardVO.setBankCardHolder(addSalmanVO.getBankCardHolder());
		bankCardVO.setBankMobile(addSalmanVO.getBankMobile());
		bankCardVO.setCardType(addSalmanVO.getCardType());
		bankCardVO.setUseTimes(1);
		bankCardVO.setSort(1);
		bankCardVO.setStatus(StatusEnum.normal.getCode());
		bankCardVO.setCreateDate(now);
		bankCardVO.setCreateId(addSalmanVO.getCreateId());
		bankCardService.addBankCard(bankCardVO);
	}

	/**
	 * 添加市代理
	 */
	@Transactional(rollbackFor = QmdException.class)
	private SalemanVO addCityAgent(AddSalmanVO addSalmanVO) {
		// 查询市代理
		Saleman cityAgent = salemanMapper
				.selectOne(new QueryWrapper<Saleman>().eq("city_code", addSalmanVO.getCityCode())
						.eq("status", StatusEnum.normal.getCode()).eq("type", SalemanTypeEnums.CityAgent.getCode()));
		if (cityAgent != null) {
			log.error("该市代理已存在:" + addSalmanVO.getCityCode());
			throw new QmdException("该市代理已存在");
		}

		// 给用户添加市代理角色
		SysPropertiesVO sysPropertiesInfoById = sysPropertiesService
				.getSysPropertiesInfoById(SysPropertiresEnums.CityAgent.getId());
		AddUserRoleVO userRoleVO = new AddUserRoleVO();
		userRoleVO.setSysUserId(addSalmanVO.getUserId());
		userRoleVO.setSysRoleId(Long.parseLong(sysPropertiesInfoById.getValue()));
		userRoleVO.setStatus(StatusEnum.normal.getCode());
		AddUserRoleVO addUserRoleVO = userRoleService.addUserRole(userRoleVO);
		if (addUserRoleVO == null) {
			log.error("添加市代理角色--> 用户添加市代理角色失败  operator:" + addSalmanVO.getCreateId());
			throw new QmdException("给用户添加市代理角色失败");
		}
		addSalmanVO.setUserRoleId(userRoleVO.getId());

		// 查询省代理，省代理不为空则将addSalmanVO中Pro_user_id设置为省代理的userId
		/*
		 * Saleman proAgent = salemanMapper.selectOne(new
		 * QueryWrapper<Saleman>().eq("pro_code", addSalmanVO.getProCode())
		 * .eq("status", StatusEnum.normal.getCode()).eq("type",
		 * SalemanTypeEnums.ProAgent.getCode())); if (proAgent != null) {
		 * addSalmanVO.setProUserId(proAgent.getUserId()); }
		 */

		// 添加市代理
		Saleman saleman = CopyBeanUtil.copyBean(addSalmanVO, Saleman.class);
		if (!checkCUD(salemanMapper.insert(saleman))) {
			log.error("添加市代理 -->添加市代理失败，受影响行数不为1:" + addSalmanVO.getCreateId());
			throw new QmdException("添加市代理失败");
		}

		// 将业务员的city_user_id改为对应市代理user_id
		// UpdateWrapper<Saleman> updateWrapper = new UpdateWrapper<Saleman>();
		// updateWrapper.set("city_user_id", addSalmanVO.getUserId());
		// 如果省代理不为空，将业务员的pro_user_id改为对应省代理user_id
		/*
		 * if (proAgent != null) { updateWrapper.set("pro_user_id",
		 * proAgent.getUserId());
		 * 
		 * }
		 */
		/*
		 * updateWrapper.eq("pro_code", addSalmanVO.getProCode()).eq("type",
		 * SalemanTypeEnums.Saleman.getCode()); salemanMapper.update(new Saleman(),
		 * updateWrapper);
		 */
		return CopyBeanUtil.copyBean(saleman, SalemanVO.class);
	}

	/**
	 * 添加省代理
	 */
	@Transactional(rollbackFor = QmdException.class)
	private SalemanVO addProAgent(AddSalmanVO addSalmanVO) {
		// 查询省代理
		Saleman proAgent = salemanMapper.selectOne(new QueryWrapper<Saleman>().eq("pro_code", addSalmanVO.getProCode())
				.eq("status", StatusEnum.normal.getCode()).eq("type", SalemanTypeEnums.ProAgent.getCode()));
		if (proAgent != null) {
			log.error("该省代理已存在:" + addSalmanVO.getProCode());
			throw new QmdException("该省代理已存在");
		}

		// 给用户添加省代理角色
		SysPropertiesVO sysPropertiesInfoById = sysPropertiesService
				.getSysPropertiesInfoById(SysPropertiresEnums.ProvinceAgent.getId());
		AddUserRoleVO userRoleVO = new AddUserRoleVO();
		userRoleVO.setSysUserId(addSalmanVO.getUserId());
		userRoleVO.setSysRoleId(Long.parseLong(sysPropertiesInfoById.getValue()));
		userRoleVO.setStatus(StatusEnum.normal.getCode());
		AddUserRoleVO addUserRoleVO = userRoleService.addUserRole(userRoleVO);
		if (addUserRoleVO == null) {
			log.error("添加省代理 --> 用户添加省代理员角色失败  operator:" + addSalmanVO.getCreateId());
			throw new QmdException("给用户添加省代理角色失败");
		}
		addSalmanVO.setUserRoleId(userRoleVO.getId());

		// 添加省代理
		Saleman saleman = CopyBeanUtil.copyBean(addSalmanVO, Saleman.class);
		if (!checkCUD(salemanMapper.insert(saleman))) {
			log.error("添加省代理 -->添加省代理失败，受影响行数不为1:" + addSalmanVO.getCreateId());
			throw new QmdException("添加省代理失败");
		}

		// 将该省的市代理、业务员的pro_userId改为该省代理userId
		/*
		 * salemanMapper.update(new Saleman(), new
		 * UpdateWrapper<Saleman>().set("pro_user_id", addSalmanVO.getUserId())
		 * .eq("pro_code", addSalmanVO.getProCode()).in("type",
		 * Lists.newArrayList(SalemanTypeEnums.CityAgent.getCode(),
		 * SalemanTypeEnums.Saleman.getCode())));
		 */
		return CopyBeanUtil.copyBean(saleman, SalemanVO.class);
	}

	/**
	 * 添加业务员
	 * 
	 * @param addSalmanVO
	 * @return
	 */
	@Transactional(rollbackFor = QmdException.class)
	private SalemanVO addSalemanInfo(AddSalmanVO addSalmanVO) {

		// 查询省代理
		/*
		 * Saleman proAgent = salemanMapper.selectOne(new
		 * QueryWrapper<Saleman>().eq("pro_code", addSalmanVO.getProCode())
		 * .eq("status", StatusEnum.normal.getCode()).eq("type",
		 * SalemanTypeEnums.ProAgent.getCode())); if (proAgent != null) {
		 * addSalmanVO.setProUserId(proAgent.getUserId()); }
		 */

		// web端添加时省市代理和业务员都为同一接口，因此在此验证citycode不能为空
		if (addSalmanVO.getCityCode() == null || addSalmanVO.getCityCode().equals("")) {
			log.error("添加业务员 -->城市code不能为空:" + addSalmanVO.getCreateId());
			throw new QmdException("城市code不能为空");
		}

		// web端添加时省市代理和业务员都为同一接口，因此在此验证countryCode不能为空
		if (addSalmanVO.getCountryCode() == null || addSalmanVO.getCountryCode().equals("")) {
			log.error("添加业务员 -->区县code不能为空:" + addSalmanVO.getCreateId());
			throw new QmdException("区县code不能为空");
		}

		// 查询市代理
		/*
		 * Saleman cityAgent = salemanMapper .selectOne(new
		 * QueryWrapper<Saleman>().eq("city_code", addSalmanVO.getCityCode())
		 * .eq("status", StatusEnum.normal.getCode()).eq("type",
		 * SalemanTypeEnums.CityAgent.getCode())); if (proAgent != null) {
		 * addSalmanVO.setProUserId(cityAgent.getUserId()); }
		 */
		// 给用户添加业务员角色
		SysPropertiesVO sysPropertiesInfoById = sysPropertiesService
				.getSysPropertiesInfoById(SysPropertiresEnums.Saleman.getId());
		AddUserRoleVO userRoleVO = new AddUserRoleVO();
		userRoleVO.setSysUserId(addSalmanVO.getUserId());
		userRoleVO.setSysRoleId(Long.parseLong(sysPropertiesInfoById.getValue()));
		userRoleVO.setStatus(StatusEnum.normal.getCode());
		AddUserRoleVO addUserRoleVO = userRoleService.addUserRole(userRoleVO);
		if (addUserRoleVO == null) {
			log.error("添加业务员 --> 用户添加业务员角色失败  operator:" + addSalmanVO.getCreateId());
			throw new QmdException("给用户添加业务员角色失败");
		}
		addSalmanVO.setUserRoleId(userRoleVO.getId());

		// 在业务员表里插入数据
		Saleman saleman = CopyBeanUtil.copyBean(addSalmanVO, Saleman.class);
		if (saleman.getParentId() == null) {
			saleman.setParentId(ParentDataEnum.RootId.getCode());
		}

		if (!checkCUD(salemanMapper.insert(saleman))) {
			log.error("添加业务员 -->添加业务员失败，受影响行数不为1:" + addSalmanVO.getCreateId());
			throw new QmdException("添加业务员失败");
		}
		SalemanVO salemanVO = CopyBeanUtil.copyBean(saleman, SalemanVO.class);
		return salemanVO;
	}

	/**
	 * 添加区级代理
	 * 
	 * @param addSalmanVO
	 * @return
	 */
	@Transactional(rollbackFor = QmdException.class)
	private SalemanVO addCountryAgent(AddSalmanVO addSalmanVO) {

		// web端添加时省市区代理和业务员都为同一接口，因此在此验证citycode不能为空
		if (addSalmanVO.getCityCode() == null || addSalmanVO.getCityCode().equals("")) {
			log.error("添加区代理 -->城市code不能为空:" + addSalmanVO.getCreateId());
			throw new QmdException("城市code不能为空");
		}

		// web端添加时省市区代理和业务员都为同一接口，因此在此验证countryCode不能为空
		if (addSalmanVO.getCountryCode() == null || addSalmanVO.getCountryCode().equals("")) {
			log.error("添加区代理 -->区县code不能为空:" + addSalmanVO.getCreateId());
			throw new QmdException("区县code不能为空");
		}

		// 给用户添加区代理角色
		SysPropertiesVO sysPropertiesInfoById = sysPropertiesService
				.getSysPropertiesInfoById(SysPropertiresEnums.CountryAgent.getId());
		AddUserRoleVO userRoleVO = new AddUserRoleVO();
		userRoleVO.setSysUserId(addSalmanVO.getUserId());
		userRoleVO.setSysRoleId(Long.parseLong(sysPropertiesInfoById.getValue()));
		userRoleVO.setStatus(StatusEnum.normal.getCode());
		AddUserRoleVO addUserRoleVO = userRoleService.addUserRole(userRoleVO);
		if (addUserRoleVO == null) {
			log.error("添加区代理 --> 用户添加区代理角色失败  operator:" + addSalmanVO.getCreateId());
			throw new QmdException("给用户添加区代理角色失败");
		}
		addSalmanVO.setUserRoleId(userRoleVO.getId());

		// 在业务员表里插入数据
		Saleman saleman = CopyBeanUtil.copyBean(addSalmanVO, Saleman.class);
		if (saleman.getParentId() == null) {
			saleman.setParentId(ParentDataEnum.RootId.getCode());
		}

		if (!checkCUD(salemanMapper.insert(saleman))) {
			log.error("添加区代理 -->添加区代理失败，受影响行数不为1:" + addSalmanVO.getCreateId());
			throw new QmdException("添加区代理失败");
		}
		SalemanVO salemanVO = CopyBeanUtil.copyBean(saleman, SalemanVO.class);
		return salemanVO;
	}

	/**
	 * 修改系统管理员
	 * 
	 * @param managerVO
	 * @return
	 */
	@Override
	public int updateSaleman(SalemanVO managerVO) {
		Saleman manager = salemanMapper.selectById(managerVO.getId());
		if (manager == null) {
			throw new QmdException("该人员信息不存在");
		}
		Saleman updateManager = CopyBeanUtil.copyBean(managerVO, Saleman.class);
		int i = salemanMapper.updateById(updateManager);
		if (i != 1) {
			throw new QmdException("修改人员信息失败");
		}
		return i;
	}

	/**
	 * 删除管理员
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteSaleman(Long id) {
		int i = salemanMapper.deleteById(id);
		if (i != 1) {
			throw new QmdException("删除人员信息失败");
		}
		return i;
	}

	/**
	 * 查询管理员信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public SalemanVO getSalemanById(Long id) {
		Saleman manager = salemanMapper.selectById(id);
		if (manager == null) {
			return null;
		}
		SalemanVO managerVO = CopyBeanUtil.copyBean(manager, SalemanVO.class);
		return managerVO;
	}

	@Override
	public SalemanVO getSalemanByUserId(Long userId) {
		AssertUtil.isNull(userId, "ManagerServiceImpl|getManagerByUserId|传入参数userId为空");
		Saleman manager = salemanMapper.selectOne(new QueryWrapper<Saleman>().eq("user_id", userId)
				.eq("status", StatusEnum.normal.getCode()));
		if (Objects.nonNull(manager)) {
			return CopyBeanUtil.copyBean(manager, SalemanVO.class);
		}
		return null;
	}

	/**
	 * 根据业务员手机号查询业务员信息
	 */
	@Override
	public SalemanVO getSalemanByUserName(String userName) {
		UserVO userVO = userService.getUserByUsername(userName);
		if (userVO == null) {
			throw new QmdException("用户不存在");
		}
		Saleman manager = salemanMapper.selectOne(new QueryWrapper<Saleman>().eq("user_id", userVO.getId())
				.eq("status", StatusEnum.normal.getCode()));
		if (Objects.nonNull(manager)) {
			return CopyBeanUtil.copyBean(manager, SalemanVO.class);
		}
		return null;
	}

	/**
	 * 总后台查询业务员列表
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public PageInfo<SalemanAndUserVO> querySalemanAndUser(SalemanAndUserParamVO params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		List<SalemanAndUserVO> list = salemanMapper.querySalemanAndUser(params);
		if (ObjectUtils.isEmpty(list)) {
			return null;
		}
		List<Integer> proCodeList = new ArrayList<Integer>();
		List<Integer> cityCodeList = new ArrayList<Integer>();
		List<Integer> countryCodeList = new ArrayList<Integer>();
		List<Long> userIdList = new ArrayList<>();
		for (SalemanAndUserVO andUserVO : list) {
			// 组装省代理省code
			if (andUserVO.getType().equals(SalemanTypeEnums.ProAgent.getCode()) && andUserVO.getProCode() != null) {
				proCodeList.add(Integer.parseInt(andUserVO.getProCode()));
			}
			// 组装市代理市code
			if (andUserVO.getType().equals(SalemanTypeEnums.CityAgent.getCode()) && andUserVO.getCityCode() != null) {
				cityCodeList.add(Integer.parseInt(andUserVO.getCityCode()));
			}
			// 组装区code
			if ((andUserVO.getType().equals(SalemanTypeEnums.Saleman.getCode())
					|| andUserVO.getType().equals(SalemanTypeEnums.CountryAgent.getCode()))
					&& andUserVO.getCountryCode() != null) {
				countryCodeList.add(Integer.parseInt(andUserVO.getCountryCode()));
			}
			userIdList.add(andUserVO.getUserId());
		}
		// 根据省code集合查询省信息
		List<AddressVO> addressByProCodeList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(proCodeList)) {
			addressByProCodeList = addressService.getAddressByProCodeList(proCodeList);
		}
		// 根据市code集合查询市
		List<AddressVO> addressByCityCodeList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(cityCodeList)) {
			addressByCityCodeList = addressService.getAddressByCityCodeList(cityCodeList);
		}
		// 根据区code集合查询区
		List<AddressVO> addressByDistrictCodeList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(countryCodeList)) {
			addressByDistrictCodeList = addressService.getAddressByDistrictCodeList(countryCodeList);
		}
		// 查询资格券数量
		List<TicketNumVO> tiketNumByUserIdList = ticketService.getTiketNumByUserIdList(userIdList,
				TicketStatusEnums.UNUSE.getCode());
		for (SalemanAndUserVO andUserVO : list) {
			// 组装省代理省名
			if (!ObjectUtils.isEmpty(addressByProCodeList)) {
				for (AddressVO addressVO : addressByProCodeList) {
					if (andUserVO.getProCode() != null && addressVO.getProvinceCode() != null
							&& andUserVO.getProCode().equals(addressVO.getProvinceCode().toString())) {
						andUserVO.setProName(addressVO.getProvinceName());
					}
				}
			}

			// 组装市代理省、市名
			if (!ObjectUtils.isEmpty(addressByCityCodeList)) {
				for (AddressVO addressVO : addressByCityCodeList) {
					if (andUserVO.getCityCode() != null && addressVO.getCityCode() != null
							&& andUserVO.getCityCode().equals(addressVO.getCityCode().toString())) {
						andUserVO.setProName(addressVO.getProvinceName());
						andUserVO.setCityName(addressVO.getCityName());
					}
				}
			}

			// 组装市代理省、市、区 名
			if (!ObjectUtils.isEmpty(addressByDistrictCodeList)) {
				for (AddressVO addressVO : addressByDistrictCodeList) {
					if (andUserVO.getCountryCode() != null && addressVO.getDistrictCode() != null
							&& andUserVO.getCountryCode().equals(addressVO.getDistrictCode().toString())) {
						andUserVO.setProName(addressVO.getProvinceName());
						andUserVO.setCityName(addressVO.getCityName());
						andUserVO.setCountryName(addressVO.getDistrictName());
					}
				}
			}
			// 查询用户可用资格券数量
			if (!ObjectUtils.isEmpty(tiketNumByUserIdList)) {
				for (TicketNumVO numVO : tiketNumByUserIdList) {
					if (numVO.getUserId().equals(andUserVO.getUserId())) {
						andUserVO.setTicketNum(numVO.getTicketNum());
					}
				}
			}
		}
		PageInfo<SalemanAndUserVO> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public ShopAgentVO getAgentAndSalemanByShopId(Long shopId) {
		ShopBasicVO shopBasicInfoByShopId = shopService.getShopBasicById(shopId);
		if (shopBasicInfoByShopId == null) {
			return null;
		}
		List<SalemanVO> agentAndSalemanByCode = salemanMapper.getAgentAndSalemanByCode(
				shopBasicInfoByShopId.getProCode(), shopBasicInfoByShopId.getCityCode(),
				shopBasicInfoByShopId.getCountyCode(), shopBasicInfoByShopId.getSalemanId());
		if (ObjectUtils.isEmpty(agentAndSalemanByCode)) {
			return null;
		}
		ShopAgentVO shopAgentVO = new ShopAgentVO();
		for (SalemanVO salemanVO : agentAndSalemanByCode) {
			if (salemanVO.getType() != null) {
				if (salemanVO.getType().equals(SalemanTypeEnums.ProAgent.getCode())) {
					shopAgentVO.setProAgent(salemanVO);
				}
				if (salemanVO.getType().equals(SalemanTypeEnums.CityAgent.getCode())) {
					shopAgentVO.setCityAgent(salemanVO);
				}
				if (salemanVO.getType().equals(SalemanTypeEnums.CountryAgent.getCode())) {
					shopAgentVO.setCountryAgent(salemanVO);
				}
				if (salemanVO.getType().equals(SalemanTypeEnums.Saleman.getCode())) {
					shopAgentVO.setSaleman(salemanVO);
				}
			}
		}
		return shopAgentVO;
	}

	@Override
	public SalemanVO getAgentByCodeAndType(String code, Byte type) {
		Saleman saleman = new Saleman();
		switch (type) {
		case 4:
			saleman = salemanMapper.selectOne(
					new QueryWrapper<Saleman>().eq("pro_code", code).eq("type", SalemanTypeEnums.ProAgent.getCode())
					.eq("status", StatusEnum.normal.getCode()));
			break;
		case 3:
			saleman = salemanMapper.selectOne(
					new QueryWrapper<Saleman>().eq("city_code", code).eq("type", SalemanTypeEnums.CityAgent.getCode())
					.eq("status", StatusEnum.normal.getCode()));
			break;
		case 2:
			saleman = salemanMapper.selectOne(new QueryWrapper<Saleman>().eq("country_code", code)
					.eq("type",SalemanTypeEnums.CountryAgent.getCode())
					.eq("status", StatusEnum.normal.getCode()));
			break;
		default:
			break;
		}
		if (saleman == null) {
			return null;
		}
		return CopyBeanUtil.copyBean(saleman, SalemanVO.class);
	}

	@Override
	public List<SalemanVO> getAgentSalemenByShopId(Long shopId) {
		AssertUtil.isNull(shopId, "ISalemanService|getAgentSalemenByShopId|传入参数shopId为空");
		ShopBasicVO shopBasicInfoByShopId = shopService.getShopBasicById(shopId);
		if (shopBasicInfoByShopId == null) {
			return null;
		}
		List<SalemanVO> agentAndSalemanByCode = salemanMapper.getAgentAndSalemanByCode(
				shopBasicInfoByShopId.getProCode(), shopBasicInfoByShopId.getCityCode(),
				shopBasicInfoByShopId.getCountyCode(), shopBasicInfoByShopId.getSalemanId());
		return agentAndSalemanByCode;
	}

	/**
	 * 查询省、市、区代理、业务员信息及其角色信息
	 * @param userId
	 * @return
	 */
	@Override
	public SalemanAndRoleVO getSalemanAndRoleByUserId(Long userId) {
		Saleman saleman = salemanMapper
				.selectOne(new QueryWrapper<Saleman>().eq("user_id", userId).eq("status", StatusEnum.normal.getCode()));
		if (saleman == null) {
			return null;
		}
		SalemanAndUserVO salemanVO = CopyBeanUtil.copyBean(saleman, SalemanAndUserVO.class);
		getSalemanAddress(salemanVO);
		List<RoleVO> roleList = roleService.getRoleListByUserId(userId);
		SalemanAndRoleVO salemanAndRoleVO = new SalemanAndRoleVO();
		salemanAndRoleVO.setSalemanVO(salemanVO);
		salemanAndRoleVO.setRoleList(roleList);
		return salemanAndRoleVO;
	}

	/**
	 * 根据用户id查询业务员信息
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public SalemanAndUserVO getSalemanAndUserByUserId(Long userId) {
		Saleman saleman = salemanMapper
				.selectOne(new QueryWrapper<Saleman>().eq("user_id", userId).eq("status", StatusEnum.normal.getCode()));
		if (saleman == null) {
			return null;
		}
		SalemanAndUserVO salemanVO = CopyBeanUtil.copyBean(saleman, SalemanAndUserVO.class);
		getSalemanAddress(salemanVO);
		UserVO userVO = userService.getUserById(userId);
		if(userVO != null) {
			salemanVO.setUserName(userVO.getUserName());
			salemanVO.setNickName(userVO.getNickName());
			salemanVO.setName(userVO.getName());
			salemanVO.setSex(userVO.getSex());
		}

		return salemanVO;
	}

	/**
	 * 查询业务员的省市区地址
	 * @param salemanVO
	 */
	private void getSalemanAddress(SalemanAndUserVO salemanVO) {
		AddressVO addressVO = new AddressVO();
		// 当用户为省代理时，根据salemanVO省code查询地址
		if (salemanVO.getType().equals(SalemanTypeEnums.ProAgent.getCode())) {
			addressVO = addressService.getAddressByCode(Integer.parseInt(salemanVO.getProCode()),
					AddressLevelEnum.Province.getCode());
			if (addressVO != null) {
				salemanVO.setProName(addressVO.getProvinceName());
			}
		}
		// 当用户为市代理时，根据salemanVO市code查询地址
		if (salemanVO.getType().equals(SalemanTypeEnums.CityAgent.getCode())) {
			addressVO = addressService.getAddressByCode(Integer.parseInt(salemanVO.getCityCode()),
					AddressLevelEnum.City.getCode());
			if (addressVO != null) {
				salemanVO.setProName(addressVO.getProvinceName());
				salemanVO.setCityName(addressVO.getCityName());
			}
		}
		// 当用户为区代理或业务员时，根据salemanVO区code查询地址
		if (salemanVO.getType().equals(SalemanTypeEnums.CountryAgent.getCode())
				|| salemanVO.getType().equals(SalemanTypeEnums.Saleman.getCode())) {
			addressVO = addressService.getAddressByCode(Integer.parseInt(salemanVO.getCountryCode()),
					AddressLevelEnum.District.getCode());
			if (addressVO != null) {
				salemanVO.setProName(addressVO.getProvinceName());
				salemanVO.setCityName(addressVO.getCityName());
				salemanVO.setCountryName(addressVO.getDistrictName());
			}
		}
	}

	/**
	 * 判断下线关系
	 * 
	 * @param agentUseId
	 *            上线用户id
	 * @param OfflineUserId
	 *            下线用户id
	 * @return 是上下线关系则返回true,否则返回false
	 */
	@Override
	public Boolean isOffline(Long agentUseId, Long OfflineUserId) {
		// 上线代理
		Saleman agent = salemanMapper.selectOne(new QueryWrapper<Saleman>().eq("user_id", agentUseId)
				.eq("status", StatusEnum.normal.getCode()));
		// 下线代理或业务员
		Saleman offlineSaleman = salemanMapper.selectOne(new QueryWrapper<Saleman>().eq("user_id", OfflineUserId)
				.eq("status", StatusEnum.normal.getCode()));
		// 当前用户或下线不存在时
		if (agent == null || offlineSaleman == null) {
			return false;
		}
		// agent的级别不大于offlineSaleman级别时
		if (agent.getType() <= offlineSaleman.getType()) {
			return false;
		}
		switch (agent.getType()) {
		case 2:
			// 上线为区代理时,若offlineSaleman不为其下线则返回false
			if (!agent.getCountryCode().equals(offlineSaleman.getCountryCode())) {
				return false;
			}
			break;
		case 3:
			// 上线为市代理时,若offlineSaleman不为其下线则返回false
			if (!agent.getCityCode().equals(offlineSaleman.getCityCode())) {
				return false;
			}
			break;
		case 4:
			// 上线为省代理时,若offlineSaleman不为其下线则返回false
			if (!agent.getProCode().equals(offlineSaleman.getProCode())) {
				return false;
			}
			break;
		default:
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void updateSalemanInfo(UpdateSalmanVO vo) {
		AssertUtil.isNull(vo, "SalemanServiceImpl|updateSalemanInfo|传入参数vo为空");
		SalemanVO old = getSalemanById(vo.getId());
		if (Objects.nonNull(old)) {
			Saleman entity = CopyBeanUtil.copyBean(vo, Saleman.class);
			if (!checkCUD(salemanMapper.updateById(entity))) {
				throw new QmdException("更新 业务员信息失败");
			}
			PayAccountVO payAccount = payAccountService.getPayAccountByUserId(old.getUserId());
			if (Objects.nonNull(payAccount)) {
				BankCardVO bankCardVO = bankCardService.getBankCardByAccountId(payAccount.getId());
				BankInfoVO bankInfoVO = bankInfoService.getBankInfoById(vo.getBankId());
				if (Objects.nonNull(bankCardVO)) {
					bankCardVO.setAccountId(vo.getId());
					bankCardVO.setBankName(bankInfoVO.getSubBankName());
					bankCardVO.setBankCardNo(vo.getBankCardNo());
					bankCardVO.setBankCardHolder(vo.getBankCardHolder());
					bankCardVO.setBankMobile(vo.getBankMobile());
					bankCardVO.setCardType(vo.getCardType());
					bankCardVO.setStatus(StatusEnum.normal.getCode());
					bankCardVO.setUpdateDate(vo.getUpdateDate());
					bankCardVO.setUpdateId(vo.getUpdateId());
					bankCardService.updateBankCard(bankCardVO);
				} else {
					bankCardVO = new BankCardVO();
					bankCardVO.setAccountId(payAccount.getId());
					bankCardVO.setBankName(bankInfoVO.getSubBankName());
					bankCardVO.setBankCardNo(vo.getBankCardNo());
					bankCardVO.setBankCardHolder(vo.getBankCardHolder());
					bankCardVO.setBankMobile(vo.getBankMobile());
					bankCardVO.setCardType(vo.getCardType());
					bankCardVO.setUseTimes(1);
					bankCardVO.setSort(1);
					bankCardVO.setStatus(StatusEnum.normal.getCode());
					bankCardVO.setCreateDate(vo.getUpdateDate());
					bankCardVO.setCreateId(vo.getUpdateId());
					bankCardService.addBankCard(bankCardVO);
				}
			} else {
				throw new QmdException("该业务员或代理没有账户, 请及时联系系统管理员.");
			}
		} else {
			throw new QmdException("未找到更新的业务员信息");
		}
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void unBindSaleman(Long id, Long operator) {
		AssertUtil.isNull(id, "SalemanServiceImpl|unBindSaleman|传入id为空");
		Saleman saleman = salemanMapper.selectById(id);
		if (Objects.isNull(saleman)) {
			throw new QmdException("未找到需要解绑的业务员信息或代理信息");
		}
		saleman.setUpdateId(operator);
		saleman.setUpdateDate(new Date());
		saleman.setStatus(StatusEnum.deleted.getCode());
		if (!checkCUD(salemanMapper.updateById(saleman))) {
			throw new QmdException("删除业务员或代理信息失败");
		}
		// 删除角色
		userRoleService.deleteUserRoleById(saleman.getUserRoleId());
	}

	@Override
	public Boolean existSaleman(Long userId, Byte...type) {
		AssertUtil.isNull(userId, "SalemanServiceImpl|existSaleman|传入参数userId为空");
		QueryWrapper<Saleman> queryWrapper = new QueryWrapper<Saleman>()
				.eq("user_id", userId)
				.eq("status", StatusEnum.normal.getCode());
		if (Objects.nonNull(type) && type.length > 0)  {
			queryWrapper.in("type", Lists.newArrayList(type));
		}
		List<Saleman> resultList = salemanMapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(resultList)) {
			return false;
		}
		return true;
	}
}
