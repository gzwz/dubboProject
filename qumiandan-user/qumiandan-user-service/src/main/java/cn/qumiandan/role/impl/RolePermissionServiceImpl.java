package cn.qumiandan.role.impl;

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
import cn.qumiandan.role.api.IRolePermissionService;
import cn.qumiandan.role.entity.RolePermission;
import cn.qumiandan.role.mapper.RolePermissionMapper;
import cn.qumiandan.role.vo.AddRolePermissionVO;
import cn.qumiandan.role.vo.AddRolePermissionVO2;
import cn.qumiandan.role.vo.BatchAddRolePermissionVO;
import cn.qumiandan.role.vo.RolePermissionVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

/**
 * 角色菜单联合信息管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IRolePermissionService.class)
public class RolePermissionServiceImpl implements IRolePermissionService {
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	/**
	 * 添加角色-菜单联合信息
	 * @param rolePermissionVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public int addRolePermission(AddRolePermissionVO addRolePermissionVO) throws QmdException {
		List<Long> permissionIds = addRolePermissionVO.getPermissionIds();
		for(int i=0;i<permissionIds.size();i++) {
			int count = rolePermissionMapper.getInfoByPerIdAndRoleId(permissionIds.get(i), addRolePermissionVO.getRoleId());
			if(count>0) {
				throw new QmdException("添加失败,该角色已添加过菜单");
			}
		}
		int j=0;
		for(int i=0;i<permissionIds.size();i++) {
			RolePermission rolePermission = new  RolePermission();
			rolePermission.setRoleId(addRolePermissionVO.getRoleId());
			rolePermission.setPermissionId(permissionIds.get(i));
			rolePermissionMapper.insertSelective(rolePermission);
			j++;
		}
		if(j<permissionIds.size()) {
			throw new QmdException("添加失败");
		}else {
			return j;
		}
		
	}

	/**
	 * 修改角色—菜单联合信息
	 * @param addRolePermissionVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = QmdException.class)
	public int updateRolePermission(AddRolePermissionVO addRolePermissionVO)throws QmdException {
		//根据角色id删除角色-菜单联合表信息
		rolePermissionMapper.deleteByRoleId(addRolePermissionVO.getRoleId());
		List<Long> permissionIds = addRolePermissionVO.getPermissionIds();
		int j=0;
		for(int i=0;i<permissionIds.size();i++) {
			RolePermission rolePermission = new  RolePermission();
			rolePermission.setRoleId(addRolePermissionVO.getRoleId());
			rolePermission.setPermissionId(permissionIds.get(i));
			rolePermissionMapper.insertSelective(rolePermission);
			j++;
		}
		if(j<permissionIds.size()) {
			throw new QmdException("更新失败");
		}else {
			return j;
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class,QmdException.class})
	public AddRolePermissionVO2 addOneRolePermission(AddRolePermissionVO2 addRolePermissionVO2) {
		AssertUtil.isNull(addRolePermissionVO2, "添加角色权限（菜单），入参不能为空");
		RolePermission entity = CopyBeanUtil.copyBean(addRolePermissionVO2, RolePermission.class);
		rolePermissionMapper.insert(entity);
		addRolePermissionVO2.setId(entity.getId());
		return addRolePermissionVO2;
	}

	@Override
	public List<RolePermissionVO> queryRolePermissionList(Long roleId) {
		AssertUtil.isNull(roleId, "查询角色的权限（菜单）列表，角色id不能不为空");
		List<RolePermission> oldList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", roleId));
		if (oldList == null) {
			return null;
		}
		List<RolePermissionVO> list = CopyBeanUtil.copyList(oldList, RolePermissionVO.class);
		return list;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class,QmdException.class})
	public void batchDeleteByRoleIdAndPermissIds(Long roleId, Set<Long> permissIds) {
		rolePermissionMapper.delete(new QueryWrapper<RolePermission>()
				.eq("role_id", roleId).in("permission_id", permissIds));
	}

	@Override
	public void batchOperateRolePermission(BatchAddRolePermissionVO vo) {
		AssertUtil.isNull(vo, "传入vo为空");
		AssertUtil.isNull(vo.getRoleId(), "传入roleId为空");
		AssertUtil.isNull(vo.getPermissionIds(), "入参permissionIds为空");
		
		List<RolePermissionVO> oldList = queryRolePermissionList(vo.getRoleId());
		//List<UserRole> oldList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("sys_user_id", vo.getUserId()));
		if (!CollectionUtils.isEmpty(oldList)) {
			// 以前的
			Set<Long> oldPermiss = oldList.stream().filter(old -> Objects.nonNull(old))
					.map(old -> old.getPermissionId()).collect(Collectors.toSet());
			// 现在的
			Set<Long> newPermiss = vo.getPermissionIds().stream()
					.filter(permiss -> Objects.nonNull(permiss))
					.collect(Collectors.toSet());
			// 以前有现在也有的 
			Set<Long> immobile = Sets.intersection(oldPermiss, newPermiss);
			
			// 获取需要添加的角色
			Set<Long> addPermiss = Sets.symmetricDifference(immobile, newPermiss);
			addPermiss.stream().forEach(permissId -> {
				AddRolePermissionVO2 add = new AddRolePermissionVO2();
				add.setPermissionId(permissId);
				add.setRoleId(vo.getRoleId());
				addOneRolePermission(add );
			});
			
			// 获取需要删除角色信息
			Set<Long> deleteRoles = Sets.symmetricDifference(immobile, oldPermiss);
			if (!CollectionUtils.isEmpty(deleteRoles)) {
				// 删除移除的角色
				batchDeleteByRoleIdAndPermissIds(vo.getRoleId(), deleteRoles);
			}
		} else {
			Set<Long> newRoles = vo.getPermissionIds().stream()
					.filter(role -> Objects.nonNull(role)).collect(Collectors.toSet());
			newRoles.stream().forEach((permissId) -> {
				AddRolePermissionVO2 add = new AddRolePermissionVO2();
				add.setPermissionId(permissId);
				add.setRoleId(vo.getRoleId());
				addOneRolePermission(add );
			});
		}
		
	}


}
