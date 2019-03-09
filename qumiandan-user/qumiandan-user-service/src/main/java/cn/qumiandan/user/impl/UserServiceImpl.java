package cn.qumiandan.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.address.api.IAddressService;
import cn.qumiandan.address.vo.AddressVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.exception.UcErrorCode;
import cn.qumiandan.constant.RedisKeyPrefix;
import cn.qumiandan.role.mapper.UserRoleMapper;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.entity.User;
import cn.qumiandan.user.entity.UserInfo;
import cn.qumiandan.user.entity.UserRole;
import cn.qumiandan.user.mapper.UserInfoMapper;
import cn.qumiandan.user.mapper.UserMapper;
import cn.qumiandan.user.vo.BackStageQueryUserParamsVO;
import cn.qumiandan.user.vo.SetPasswordVO;
import cn.qumiandan.user.vo.UpdateUserVO;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserInfoVO;
import cn.qumiandan.user.vo.UserUpdateVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.MD5Utils;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.utils.PhoneUtils;
import cn.qumiandan.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {

	@Autowired
    private UserMapper userDao;

	@Autowired
    private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Reference
	private IAddressService addressService;
	@Override
	public UserVO getUserById(Long id) {
		return userDao.getUserById(id);
	}

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<UserVO> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
       // PageHelper.startPage(pageNum, pageSize);
    	List<User> user = userDao.selectByMap(null);
        //PageInfo result = new PageInfo(userDomains);
        List<UserVO> list = CopyBeanUtil.copyList(user, UserVO.class);
        return list;
    }

	@Override
	public UserVO getUserByUnionidOrOpenId(String unionid,String openId) {
		// 两个参数不能同时为空
		if (unionid == null && openId == null) {
			throw new QmdException("根据unionid 或 openId 获取用户信息时，两者不能同时为空！");
		}
    	UserVO userVO = userDao.getUserByUnionidOrOpenId(unionid,openId);
		return userVO != null ? userVO : new UserVO();
	}

	@Override
	public UserVO getUserByUsername(String username) {
		UserVO userVO = userDao.getUserByUsername(username);
		return userVO ;
	}

	@Override
	public UserVO getUserByUsernameAndPwd(String username,String password){
		UserVO userVO = userDao.getUserByUsernameAndPwd(username,password);
		return userVO != null ? userVO : new UserVO();
	}
	
	@Override
	public UserAddVO addUser(UserAddVO userAddVO) throws QmdException {
		UserVO OldUserVO = userDao.getUserByUsername(userAddVO.getUserName());
		if(OldUserVO != null){
			throw new QmdException("用户手机号已存在，可直接使用手机号登陆");
		}
		if (StringUtils.isNotBlank(userAddVO.getUnionId())||
				StringUtils.isNotBlank(userAddVO.getOpenId())) {
			OldUserVO = userDao.getUserByUnionidOrOpenId(userAddVO.getUnionId(), userAddVO.getOpenId());
			if(OldUserVO != null){
				throw new QmdException("该用户已绑定手机号"+PhoneUtils.blurPhone(OldUserVO.getUserName())+",可直接使用微信或手机号登陆！");
			}
		}
		
		User user = CopyBeanUtil.copyBean(userAddVO,User.class);
		user.setCreateDate(new Date());

		int userRet = userDao.insertSelective(user);
		if(userRet < 1){
			throw new QmdException("添加用户失败");
		}
		UserInfo userInfo = 
		CopyBeanUtil.copyBean(userAddVO,UserInfo.class);
		userInfo.setUserId(user.getId());
		int userInfoRet = userInfoMapper.insertSelective(userInfo);
		if(userInfoRet < 1){
			throw new QmdException("添加用户信息失败");
		}

		userAddVO.setId(user.getId());
		return userAddVO;
	}

	
	/**
	 * 根据id修改用户基本信息
	 * @param userAddVO
	 * @return
	 * @throws QmdException
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public int updateUserInfoByUserId(UserAddVO userAddVO) throws QmdException{
		User user = CopyBeanUtil.copyBean(userAddVO, User.class);
		user.setUpdateDate(new Date());
		UserInfo userInfo = CopyBeanUtil.copyBean(userAddVO, UserInfo.class);
		if(userInfo!= null) {
			userInfo.setUserId(userAddVO.getId());
			userInfoMapper.update(userInfo, new UpdateWrapper<UserInfo>().eq("user_id", userAddVO.getId()));
		}
		int  i = userDao.updateUserById(user);
		UserInfoVO vo = CopyBeanUtil.copyBean(userAddVO, UserInfoVO.class);
		vo.setUserId(user.getId());
		updateUserInfo(vo);
		return  i;
	}
	/**
	 * 根据id修改用户角色
	 * @param userUpdateVO
	 * @return
	 * @throws QmdException
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public int updateUserRolesByUserId(UserUpdateVO userUpdateVO) throws QmdException{
		userRoleMapper.updateUserRoleByUserId(userUpdateVO.getUserId());
		List<Long> roleIds=userUpdateVO.getRoleIds();
		int j = 0;
		for(int i=0;i<roleIds.size();i++) {
			UserRole userRole=new UserRole();
			userRole.setSysUserId(userUpdateVO.getUserId());
			userRole.setSysRoleId(roleIds.get(i));
			userRole.setStatus(ToolUtil.intToByte(1));
			userRoleMapper.addUserRole(userRole);
			j++;
			
		}
		if(j<roleIds.size()) {
			throw new QmdException("添加失败");
		}else {
			return j;
		}
		
		
	}

	/**
	 * 删除用户（将sys_user表中status值改为3）
	 */
	@Override
	@Transactional(rollbackFor=QmdException.class)
	public int deleteByUserId(Long userId) throws QmdException{
		User user = new User();
		user.setStatus(3);
		user.setId(userId);
		int i = userDao.updateUserById(user);
		userRoleMapper.updateUserRoleByUserId(userId);
		if(i<1) {
			throw new QmdException("删除失败");
		}else {
			return i;
		}
		
	}

	/**
	 * 查询sys_user和sys_user_info的联合用户信息
	 * @return
	 */
	@Override
	public PageInfo<UserAddVO> getUserPageList(int pageNum, int pageSize){
		 //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //条件查询
        List<UserAddVO> userVOList = userDao.getUserAndUserInfo();
        //封装分页结果
        PageInfo<UserAddVO> pageInfo = new PageInfo<>(userVOList);
        pageInfo.setTotal(pageInfo.getTotal());
        //返回分页对象
        return pageInfo;
	}

	/**
	 * 根据用户id删除用户角色信息（sys_user_role）,逻辑删除
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteUserRoleByUserId(Long userId) {
		int i=userRoleMapper.updateUserRoleByUserId(userId);
		if(i<1) {
			throw new QmdException("删除失败");
		}else {
			return i;
		}
		
	}

	@Override
	public int updateUserInfo(UserInfoVO vo) {
		if (Objects.isNull(vo.getUserId())) {
			throw new QmdException("修改userInfo userId不能为空");
		}
		UserInfo userInfo = CopyBeanUtil.copyBean(vo, UserInfo.class);
		if (userInfoMapper.updateUserInfoByUserId(userInfo) != 1) {
			throw new QmdException(UcErrorCode.UC9999.getCode(), "更新用户扩展信息失败");
		}
		return 1;
	}

	@Override
	public int logout(Long userId) {
		UserVO vo = getUserById(userId);
		if (Objects.isNull(vo)) {
			throw new QmdException(UcErrorCode.UC1001.getCode(), "用户信息不存在");
		}
		
		String token = new StringBuilder().append(RedisKeyPrefix.MUTUALWEB_SHIRO_SESSION).append(vo.getToken()).toString();
		if (redisTemplate.hasKey(token)) {
			redisTemplate.delete(token);
		}
		
		String cacheUserName = new StringBuilder(RedisKeyPrefix.MUTUALWEB_SHIRO_CACHE).append("shiro_redis_cache:").append(vo.getUserName()).toString();
		if (redisTemplate.hasKey(cacheUserName)) {
			redisTemplate.delete(cacheUserName);
		}
		
		String cacheId = new StringBuilder(RedisKeyPrefix.MUTUALWEB_SHIRO_CACHE).append("shiro_redis_cache:").append(vo.getId()).toString();
		if (redisTemplate.hasKey(cacheId)) {
			redisTemplate.delete(cacheId);
		}
		
		log.info(new StringBuilder("UserServiceImpl")
				.append("|logout")
				.append("|userId:")
				.append(vo.getId())
				.append("|userName:")
				.append(vo.getUserName())
				.append("|执行登出成功").toString());
		return 1;
	}

	@Override
	public UserInfoVO getUserInfoByUserId(Long userId) {
		return userInfoMapper.selectUserInfoByUserId(userId);
	}
	
	/**
	 * 设置密码
	 * @param userVO
	 * @return
	 */
	@Override
	public int setPassword(SetPasswordVO passwordVO) {
		UserVO userVO = userDao.getUserByUsername(passwordVO.getUserName());
		if(userVO ==null) {
			throw new QmdException("用户不存在");
		}
		if(userVO.getPassword()!=null) {
			if(passwordVO.getOldPassword()==null) {
				throw new QmdException("原密码不能为空");
			}
			if(!userVO.getPassword().equals(MD5Utils.encode(passwordVO.getOldPassword(), passwordVO.getOldPassword()))) {
				throw new QmdException("原始密码不正确");
			}	
		}
		String md5Password = MD5Utils.encode(passwordVO.getNewPassword(), passwordVO.getNewPassword());
		return userDao.setPassword(passwordVO.getUserName(), md5Password);
	}

	/**
	 * 忘记密码
	 * @param passwordVO
	 * @return
	 */
	@Override
	public int resetPassword(SetPasswordVO passwordVO) {
		UserVO userVO = userDao.getUserByUsername(passwordVO.getUserName());
		if(userVO ==null) {
			throw new QmdException("用户不存在");
		}String md5Password = MD5Utils.encode(passwordVO.getNewPassword(), passwordVO.getNewPassword());
		return userDao.setPassword(passwordVO.getUserName(), md5Password);
	}

	@Override
	public int updateUser(UpdateUserVO vo) {
		AssertUtil.isNull(vo, "UserServiceImpl|updateUser|传入参数vo为空");
		AssertUtil.isNull(vo.getId(), "UserServiceImpl|updateUser|传入参数id为空");
		User user = CopyBeanUtil.copyBean(vo, User.class);
		if (!checkCUD(userDao.updateById(user))) {
			log.error("更新用户主表信息 返回更新影响条数不为1");
			throw new QmdException("更新用户主表信息失败");
		}
		return 1;
	}

	/**
	 * 根据用户id查询
	 * @param idList
	 * @return
	 */
	@Override
	public List<UserVO> getUserByUserIds(List<Long> idList) {
		List<User> users = userDao.selectList(new QueryWrapper<User>().in("id", idList));
		if(users == null) {
			return null;
		}
		List<UserVO>  list = CopyBeanUtil.copyList(users, UserVO.class);
		return list;
	}

	/**
	 * 总后台查询用户信息
	 */
	@Override
	public PageInfo<UserVO> backStageQueryUser(BackStageQueryUserParamsVO paramsVO) {
		PageHelper.startPage(paramsVO.getPageNum(), paramsVO.getPageSize());
		List<UserVO> backStageQueryUser = userDao.backStageQueryUser(paramsVO);
		if(ObjectUtils.isEmpty(backStageQueryUser)) {
			return null;
		}
		List<Integer> areaCodeList = new ArrayList<>();
		//组装查询省市区条件
		for(UserVO userVO : backStageQueryUser) {
			userVO.setPassword(null);
			if(userVO.getCountryCode() != null &&!userVO.getCountryCode().equals("")) {
				areaCodeList.add(Integer.parseInt(userVO.getCountryCode()));
			}
		}
		if(areaCodeList.size()>0) {
			//查询用户的省市区地址
			List<AddressVO> addressList = addressService.getAddressByDistrictCodeList(areaCodeList);
			for(UserVO userVO : backStageQueryUser) {
				for(AddressVO addressVO : addressList) {
					if(userVO.getCityCode().equals(addressVO.getDistrictCode().toString())) {
						userVO.setProName(addressVO.getProvinceName());
						userVO.setCityName(addressVO.getCityName());
						userVO.setCountryName(addressVO.getDistrictName());
					}
				}
			}
		}
		PageInfo<UserVO> pageInfo = new PageInfo<>(backStageQueryUser);
		return pageInfo;
	}

}
