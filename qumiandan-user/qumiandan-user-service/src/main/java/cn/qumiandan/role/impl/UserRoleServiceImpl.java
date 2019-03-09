package cn.qumiandan.role.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.role.api.IUserRoleService;
import cn.qumiandan.role.mapper.UserRoleMapper;
import cn.qumiandan.role.vo.AddUserRoleVO;
import cn.qumiandan.role.vo.BatchAddUserRoleVO;
import cn.qumiandan.role.vo.UserRoleVO;
import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.entity.UserRole;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

@Transactional
@Component
@Service(interfaceClass = IUserRoleService.class)
public class UserRoleServiceImpl implements IUserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private IUserService userService;
	
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public AddUserRoleVO addUserRole(AddUserRoleVO vo) {
		UserRoleVO old = userRoleMapper.getUserRoleByUserIdAndRoleId(vo.getSysUserId(), vo.getSysRoleId());
		if (Objects.nonNull(old) && Objects.nonNull(old.getId())) {
			vo.setId(old.getId());
			return vo;
		}
		UserRole userRole = CopyBeanUtil.copyBean(vo, UserRole.class);
		int i = userRoleMapper.insert(userRole);
		if ( i!= 1) {
			throw new QmdException("添加用户角色关联信息失败");
		} 
		vo.setId(userRole.getId());
		return vo;
	}

	@Override
	public UserRoleVO getUserRoleByUserIdAndRoleId(Long userId, Long roleId) {
		return userRoleMapper.getUserRoleByUserIdAndRoleId(userId, roleId);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public int deleteUserRoleById(Long id) {
		AssertUtil.isNull(id, "UserRoleServiceImpl|deleteUserRoleById|传入参数为id为空");
		return userRoleMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void batchOperateUserRole(BatchAddUserRoleVO vo) {
		AssertUtil.isNull(vo, "UserRoleServiceImpl|batchOperateUserRole|传入vo为空");
		AssertUtil.isNull(vo.getUserId(), "UserRoleServiceImpl|batchOperateUserRole|传入userId为空");
		AssertUtil.isNull(vo.getRoles(), "UserRoleServiceImpl|batchOperateUserRole|传入roles为空");
		
		List<UserRole> oldList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("sys_user_id", vo.getUserId()));
		if (!CollectionUtils.isEmpty(oldList)) {
			// 以前的
			Set<Long> oldRoles = oldList.stream().filter(old -> Objects.nonNull(old)).map(old -> old.getSysRoleId()).collect(Collectors.toSet());
			// 现在的
			Set<Long> newRoles = vo.getRoles().stream().filter(role -> Objects.nonNull(role)).collect(Collectors.toSet());
			// 以前有现在也有的 
			Set<Long> immobile = Sets.intersection(oldRoles, newRoles);
			
			// 获取需要添加的角色
			Set<Long> addRoles = Sets.symmetricDifference(immobile, newRoles);
			addRoles.stream().forEach(roleId -> {
				AddUserRoleVO add = new AddUserRoleVO();
				add.setStatus(StatusEnum.normal.getCode());
				add.setSysUserId(vo.getUserId());
				add.setSysRoleId(roleId);
				addUserRole(add);
			});
			
			// 获取需要删除角色信息
			Set<Long> deleteRoles = Sets.symmetricDifference(immobile, oldRoles);
			if (!CollectionUtils.isEmpty(deleteRoles)) {
				// 删除移除的角色
				batchDeleteUserRoleInfo(vo.getUserId(), deleteRoles);
			}
		} else {
			Set<Long> newRoles = vo.getRoles().stream().filter(role -> Objects.nonNull(role)).collect(Collectors.toSet());
			newRoles.stream().forEach(roleId -> {
				AddUserRoleVO add = new AddUserRoleVO();
				add.setStatus(StatusEnum.normal.getCode());
				add.setSysUserId(vo.getUserId());
				add.setSysRoleId(roleId);
				addUserRole(add);
			});
		}
		// 把用户踢掉线
		userService.logout(vo.getUserId());
	}
	
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public void batchDeleteUserRoleInfo(Long userId, Collection<Long> roleIds) {
		AssertUtil.isNull(userId, "UserRoleServiceImpl|batchDeleteUserRoleInfo|传入userId为空");
		AssertUtil.isNull(roleIds, "UserRoleServiceImpl|batchDeleteUserRoleInfo|传入roleIds为空");
		int deleteRet = userRoleMapper.delete(new QueryWrapper<UserRole>().eq("sys_user_id", userId).in("sys_role_id", roleIds));
		if (deleteRet == 0) {
			throw new QmdException("批量删除用户角色关联信息失败");
		}
	}

	@Override
	public List<UserRoleVO> getUserRolesByUserId(Long userId) {
		AssertUtil.isNull(userId, "UserRoleServiceImpl|getUserRolesByUserId|传入userId为空");
		List<UserRole> resultList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("sys_user_id", userId).eq("status", StatusEnum.normal.getCode()));
		if (!CollectionUtils.isEmpty(resultList)) {
			return CopyBeanUtil.copyList(resultList, UserRoleVO.class);
		}
		return null;
	}

}
