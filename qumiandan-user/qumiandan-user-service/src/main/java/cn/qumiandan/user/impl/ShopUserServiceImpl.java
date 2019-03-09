package cn.qumiandan.user.impl;

import java.util.Date;
import java.util.HashSet;
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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.exception.UcErrorCode;
import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.api.IUserRoleService;
import cn.qumiandan.role.mapper.UserRoleMapper;
import cn.qumiandan.role.vo.AddUserRoleVO;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.shop.api.IShopService;
import cn.qumiandan.shop.vo.ShopBasicVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.entity.ShopUser;
import cn.qumiandan.user.mapper.ShopUserMapper;
import cn.qumiandan.user.vo.AddShopUserVO;
import cn.qumiandan.user.vo.ShopInfoVO;
import cn.qumiandan.user.vo.ShopUserDetailVO;
import cn.qumiandan.user.vo.ShopUserRoleVO;
import cn.qumiandan.user.vo.ShopUserVO;
import cn.qumiandan.user.vo.UpdateShopUserRoleVO;
import cn.qumiandan.user.vo.UpdateShopUserVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.verifycode.api.IVerifyCodeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @description：店铺人员接口实现类
 * @author：zhuyangyong
 * @date: 2018/11/23 16:48
 */
@Slf4j
@Component
@Service(interfaceClass = IShopUserService.class)
public class ShopUserServiceImpl extends ServiceImpl<ShopUserMapper, ShopUser> implements IShopUserService {

	@Autowired
	private IUserService userService;
	
    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Reference()
    private IShopService shopService;

    @Reference()
    private IVerifyCodeService verifyCodeService;
    
    @Autowired
    private IUserRoleService userRoleService;
    
    @Autowired
    private IShopUserService shopUserService;
    
    @Autowired
    private IRoleService roleService;
    
    @Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
    public ShopUserVO addShopUser(ShopUserVO shopUserVO){
    	AssertUtil.isNull(shopUserVO, "ShopUserServiceImpl|addShopUser|传入参数shopUserVO为空");
		ShopUser shopUser = CopyBeanUtil.copyBean(shopUserVO, ShopUser.class);
    	if (!checkCUD(shopUserMapper.insert(shopUser))) {
    		throw new QmdException("添加店铺用户信息失败");
    	}
    	shopUserVO.setId(shopUser.getId());
        return shopUserVO;
    }

	@Override
	public PageInfo<ShopUserVO> getShopUserPageListByUserId(CommonParams params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		// 执行sql
		List<ShopUserVO> resultList = shopUserMapper.getShopUserPageListByUserId(params);
        //封装分页结果
        PageInfo<ShopUserVO> pageInfo = new PageInfo<ShopUserVO>(resultList);
        pageInfo.setTotal(pageInfo.getTotal());
        //返回分页对象
        return pageInfo;
	}

	@Override
	public ShopUserDetailVO getShopUserDetailInfo(CommonParams params) {
		// 查询基本信息
		ShopUserDetailVO result = shopUserMapper.getShopUserDetailInfoById(params.getId());
		// 查询在某个角色下所管的店铺id
		List<ShopUserVO> shopUserList = getShopUserInfoByUserIdAndUserRoleId(result.getUserId(), result.getUserRoleId());
		if (!CollectionUtils.isEmpty(shopUserList)) {
			List<Long> shopIds = shopUserList.stream().map(shopuser -> shopuser.getShopId()).collect(Collectors.toList());
			// 查询店铺信息
			List<ShopBasicVO> shopList = shopService.getShopByManager(shopIds);
			if (!CollectionUtils.isEmpty(shopList)) {
				result.setShopList(shopList.stream().map(shopInfo -> new ShopInfoVO(shopInfo.getId(), shopInfo.getName())).collect(Collectors.toList()));
			}
		}
		return result;
	}

	@Override
	public List<ShopUserVO> getShopUserInfoByUserIdAndUserRoleId(Long userId, Long userRoleId) {
		return shopUserMapper.getShopUserInfoByUserIdAndUserRoleId(userId, userRoleId);
	}

	@Override
	public List<ShopUserVO> getShopUserListByUserId(Long userId){
		return shopUserMapper.getShopUserListByUserId(userId);
	}

	@Override
	public List<Long> getShopIdListByUserId(Long userId){
		return shopUserMapper.getUserShopIdListByUserId(userId);
	}

	@Override
	public boolean memberExist(Long userId) {
		if (shopUserMapper.countMemberShopNum(userId) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public int addMemberShopInfo(AddShopUserVO vo) {
		// 验证手机验证码是否通过
		if (!verifyCodeService.validationVerifyCodeAndMobile(vo.getMobile(), vo.getCode())) {
			throw new QmdException(UcErrorCode.UC1001.getCode(), "手机验证码错误");
		}
		
		UserVO user = userService.getUserByUsername(vo.getMobile());
		if (Objects.isNull(user.getId())) {
			throw new QmdException(UcErrorCode.UC1001.getCode(), "该手机号未注册");
		}
		
		if (shopUserService.memberExist(user.getId())) {
			throw new QmdException(UcErrorCode.UC1001.getCode(), "重复添加成员信息");
		}
		
		// 2.先给用户赋值角色
		AddUserRoleVO userRole = new AddUserRoleVO();
		userRole.setStatus(StatusEnum.normal.getCode());
		userRole.setSysRoleId(vo.getRoleId());
		userRole.setSysUserId(user.getId());
		userRoleService.addUserRole(userRole);
		
		// 3.添加店铺用户信息
		if (!CollectionUtils.isEmpty(vo.getShopIds())) {
			Date now = new Date();
			for (Long shopId : vo.getShopIds()) {
				ShopUser shopuser = new ShopUser();
				shopuser.setCreateDate(now);
				shopuser.setCreateId(vo.getCreateId());
				shopuser.setShopId(shopId);
				shopuser.setStatus(StatusEnum.normal.getCode());
				shopuser.setUserId(user.getId());
				shopuser.setUserRoleId(userRole.getId());
				if (shopUserMapper.insert(shopuser) != 1) {
					log.info(new StringBuilder()
							.append("ShopUserServiceImpl|addMemberShopInfo|createId:")
							.append(vo.getCreateId())
							.append("|插入店铺用户绑定信息失败 shopId:")
							.append(shopId).toString());
					throw new QmdException(UcErrorCode.UC9999.getCode(), "系统异常,添加店铺成员失败");
				}
			}
		}
		
		// 让用户登出
		userService.logout(user.getId());
		
		log.info(new StringBuilder()
				.append("ShopUserServiceImpl|addMemberShopInfo|createId:")
				.append(vo.getCreateId())
				.append("|userId:")
				.append(user.getId())
				.append("|userRoleId:")
				.append(userRole.getId())
				.append("|shopIds:")
				.append(!CollectionUtils.isEmpty(vo.getShopIds()) ? vo.getShopIds().toString() : null)
				.append("创建店铺用户绑定关系信息成功").toString());
		return 1;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public int updateMemberRole(UpdateShopUserRoleVO vo) {
		// 查询基本信息
		ShopUserDetailVO shopuser = shopUserMapper.getShopUserDetailInfoById(vo.getShopUserId());
		if (Objects.isNull(shopuser)) {
			throw new QmdException(UcErrorCode.UC1001.getCode(), "成员信息不再存在");
		}
		List<RoleVO> list = roleService.getPlatformOpenRolesList();
		Set<Long> openRoleIds = list.stream().map(role -> role.getId()).collect(Collectors.toSet());
		if (!openRoleIds.contains(vo.getRoleId())) {
			log.info(new StringBuilder()
					.append("ShopUserServiceImpl|updateMemberRole|updateId:")
					.append(vo.getUpdateId())
					.append("|非法修改角色:")
					.append(vo.getRoleId()).toString());
			throw new QmdException(UcErrorCode.UC1001.getCode(), "非法修改角色");
		}
		
		// 修改角色
		if (userRoleMapper.updateUserRoleByIdAndRoleId(shopuser.getUserRoleId(), vo.getRoleId()) != 1) {
			throw new QmdException(UcErrorCode.UC9999.getCode(), "系统异常, 修改角色失败");
		}
		
		// 让用户登出
		userService.logout(shopuser.getUserId());
		log.info(new StringBuilder()
				.append("ShopUserServiceImpl|updateMemberManageShop|updateId:")
				.append(vo.getUpdateId())
				.append("|修改店铺用户绑定信息成功").toString());
		return 1;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateMemberManageShop(UpdateShopUserVO vo) {
		// 查询基本信息
		ShopUserDetailVO oldShopUser = shopUserMapper.getShopUserDetailInfoById(vo.getShopUserId());
		if (Objects.isNull(oldShopUser)) {
			throw new QmdException(UcErrorCode.UC1001.getCode(), "成员信息不再存在");
		}
		
		// 删除以前的店铺用户关联信息
		shopUserMapper.deleteShopUserInfoByUserId(oldShopUser.getUserId());
		
		// 添加改变后的店铺用户关联信息
		if (!CollectionUtils.isEmpty(vo.getShopIds())) {
			Date now = new Date();
			for (Long shopId : vo.getShopIds()) {
				ShopUser shopuser = new ShopUser();
				shopuser.setShopId(shopId);
				shopuser.setStatus(StatusEnum.normal.getCode());
				shopuser.setUserId(oldShopUser.getUserId());
				shopuser.setUserRoleId(oldShopUser.getUserRoleId());
				shopuser.setCreateDate(now);
				shopuser.setCreateId(vo.getUpdateId());
				shopuser.setUpdateId(vo.getUpdateId());
				shopuser.setUpdateDate(now);
				if (shopUserMapper.insert(shopuser)  != 1) {
					log.info(new StringBuilder()
							.append("ShopUserServiceImpl|updateMemberManageShop|updateId:")
							.append(vo.getUpdateId())
							.append("|修改店铺用户绑定信息 shopId:")
							.append(shopId).toString());
					throw new QmdException(UcErrorCode.UC9999.getCode(), "系统异常, 修改店铺成员失败");
				}
			}
		}
		log.info(new StringBuilder()
				.append("ShopUserServiceImpl|updateMemberManageShop|updateId:")
				.append(vo.getUpdateId())
				.append("|修改店铺用户绑定信息成功").toString());
		return 1;
	}

	@Override
	public int unbindMemberShopUser(Long shopUserId, Long updateId) {
		// 查询基本信息
		ShopUserDetailVO oldShopUser = shopUserMapper.getShopUserDetailInfoById(shopUserId);
		if (Objects.isNull(oldShopUser)) {
			log.info(new StringBuilder()
					.append("ShopUserServiceImpl|unbindMemberShopUser|updateId:")
					.append(updateId)
					.append("|shopUserId:")
					.append(shopUserId)
					.append("数据库不存在").toString());
			throw new QmdException(UcErrorCode.UC1001.getCode(), "成员信息不再存在");
		}
		
		// 删除角色关系
		if (userRoleMapper.deleteById(oldShopUser.getUserRoleId()) != 1) {
			throw new QmdException(UcErrorCode.UC9999.getCode(), "系统错误, 删除绑定角色失败");
		}
		
		// 删除和店铺的绑定关系
		shopUserMapper.deleteShopUserInfoByUserId(oldShopUser.getUserId());
		
		// 让用户登出
		userService.logout(oldShopUser.getUserId());
		log.info(new StringBuilder()
				.append("ShopUserServiceImpl|unbindMemberShopUser|updateId:")
				.append(updateId)
				.append("|shopUserId:")
				.append(shopUserId)
				.append("|userId:")
				.append(oldShopUser.getUserId())
				.append("解除绑定关系成功").toString());
		return 1;
	}

	@Override
	public List<ShopUserVO> getShopUserListByShopId(Long shopId) {
		AssertUtil.isNull(shopId, "ShopUserServiceImpl|getShopUserListByShopId|传入参数shopId为空");
		return shopUserMapper.getShopUserListByShopId(shopId);
	}

	@Override
	public PageInfo<ShopUserVO> getShopUserPageListByShopId(CommonParams params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		// 执行sql
		List<ShopUserVO> resultList = shopUserMapper.getShopUserPageListByShopId(params);
        //封装分页结果
        PageInfo<ShopUserVO> pageInfo = new PageInfo<ShopUserVO>(resultList);
        pageInfo.setTotal(pageInfo.getTotal());
        //返回分页对象
        return pageInfo;
	}

	/**
	 * 根据用户id查询店铺id集合
	 */
	@Override
	public Set<Long> getShopIdsByUserId(Long userId) {
		List<ShopUser> shopUserList = shopUserMapper.selectList(new QueryWrapper<ShopUser>().eq("user_id", userId));
		if(ObjectUtils.isEmpty(shopUserList)) {
			return null;
		}
		Set<Long> ids = new HashSet<>();
		for(ShopUser shopUser:shopUserList) {
			ids.add(shopUser.getShopId());
		}
		return ids;
	}

	/**
	 * 根据店铺id查询店铺相关人员信息
	 */
	@Override
	public List<ShopUserRoleVO> getShopUserRoleByShopId(Long shopId) {
		return shopUserMapper.getShopUserRoleByShopId(shopId);
	}
}
