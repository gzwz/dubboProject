package cn.qumiandan.permission.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.permission.api.IPermissionService;
import cn.qumiandan.permission.entity.Permission;
import cn.qumiandan.permission.mapper.PermissionMapper;
import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;

/**
 * 菜单管理实现类
 * 
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IPermissionService.class)
public class PermissionServiceImpl implements IPermissionService {
	@Autowired
	private PermissionMapper permissionMapper;

	/**
	 * 添加菜单信息
	 */
	@Override
	public int addPermission(PermissionVO permissionVO) throws QmdException {
		Permission permission = 
		CopyBeanUtil.copyBean(permissionVO, Permission.class);
		permission.setCreateDate(new Date());
		Permission permissionByName = permissionMapper.selectOne(new QueryWrapper<Permission>().eq("name", permissionVO.getName()));
		// 数据库中菜单名不能重复
		if (permissionByName != null) {
			throw new QmdException("菜单名已存在");
		}
		if (permissionVO.getSort() == null) {
			Integer sort = permissionMapper.getMaxSort();
			permission.setSort( (sort!=null?sort:0)+ 1);
		}
		return permissionMapper.insert(permission);

	}

	/**
	 * 更新菜单信息
	 */
	@Override
	public int updatePermission(PermissionVO permissionVO) throws QmdException {
		Permission permissionByName = permissionMapper.selectOne(new QueryWrapper<Permission>().eq("name", permissionVO.getName()));
		Permission permission = CopyBeanUtil.copyBean(permissionVO, Permission.class);
		permission.setUpdateDate(new Date());
		// 数据库中菜单名不能重复
		if (permissionByName != null&&!permissionByName.getId().equals(permissionVO.getId())) {
			throw new QmdException("菜单名已存在");
		} 
		return permissionMapper.updateById(permission);	
	}

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @return
	 * @throws QmdException
	 */
	@Override
	public int deletePermission(Long id) throws QmdException {
		Permission permission = new Permission();
		permission.setId(id);
		permission.setStatus(ToolUtil.intToByte(0));
		int i = permissionMapper.updateById(permission);
		if (i < 1) {
			throw new QmdException("删除失败，数据库异常");
		}
		return i;
	}

	
	/**
	 * 获取所有菜单
	 * @return
	 */
	@Override
	public List<PermissionVO> getAllPermission(Byte type) {
		boolean flag = false;
		if (type != null) {
			flag = true;
		}
		List<Permission> list = permissionMapper.selectList(new QueryWrapper<Permission>()
				.eq("status", 1).eq(flag , "type", type));
		if(list.size() <= 0) {
			return null;
		}
		List<PermissionVO> permissionVOs = CopyBeanUtil.copyList(list, PermissionVO.class);
		return permissionVOs;
	}
}
