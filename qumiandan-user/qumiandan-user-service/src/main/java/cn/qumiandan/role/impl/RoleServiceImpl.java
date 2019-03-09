package cn.qumiandan.role.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.enums.UCSysPropertiresEnums;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.entity.Role;
import cn.qumiandan.role.mapper.RoleMapper;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.system.api.ISysPropertiesService;
import cn.qumiandan.system.enums.SysPropertiresEnums;
import cn.qumiandan.system.vo.SysPropertiesVO;
import cn.qumiandan.user.mapper.UserMapper;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import cn.qumiandan.utils.ToolUtil;


@Component
@Service(interfaceClass = IRoleService.class)
public class RoleServiceImpl implements IRoleService {
    public static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Reference()
    private ISysPropertiesService sysProperties;
    
	@Autowired
    private UserMapper userDao;
	
	
	/**
	 * 根据用户名查询用户角色
	 * @param userName
	 * @return
	 */
	@Override
	public List<RoleVO> getRoleByUserName(String userName) {
		AssertUtil.isNull(userName, "userName不能为空");
		List<RoleVO> roleVOlist = null;
		if ("admin".equals(userName)) {
			List<Role> selectList = roleMapper.selectList(new QueryWrapper<Role>());
			roleVOlist = CopyBeanUtil.copyList(selectList, RoleVO.class);
			return roleVOlist;
		}
		roleVOlist = userDao.getRoleByUserName(userName);
		return roleVOlist;
	}

    @Override
    public List<RoleVO> getRoleListByUserId(Long userId){
        List<RoleVO> roleVOList = roleMapper.getRoleListByUserId(userId);
        return roleVOList.size() > 0 ? roleVOList : new ArrayList<>();
    }

    @Override
    public List<PermissionVO> getPermissionListByUserId(Long userId){
        List<PermissionVO> permissionVOList = roleMapper.getPermissionListByUserId(userId);
        return permissionVOList.size() > 0 ? permissionVOList : new ArrayList<>();
    }

    @Override
    public Set<String> getRoleEaliasSetByUserId(Long userId){
        Set<String> roleEaliasSet = roleMapper.getRoleEaliasSetByUserId(userId);
        return roleEaliasSet.size() > 0 ? roleEaliasSet : new HashSet<>();
    }

    /**
     * 根据用户id查询所有角色
     */
    @Override
    public Set<String> getPermissionSetByUserId(Long userId){
        Set<String> permissionSet = roleMapper.getPermissionSetByUserId(userId);
        return permissionSet.size() > 0 ? permissionSet : new HashSet<>();
    }
    /**
     * 添加角色信息
     * @param roleVO
     * @return
     */
	@Override
	public int addRole(RoleVO roleVO) throws QmdException {
		Role role2= roleMapper.getRoleByName(roleVO.getName());
		//角色名不能重复
		if(role2!=null){
			throw new QmdException("角色名已存在");	
		}
		Integer maxSort=roleMapper.getMaxSort();
		Role role = CopyBeanUtil.copyBean(roleVO, Role.class);
		role.setCreateDate(new Date());
		if(role.getSort()==null) {
			role.setSort((maxSort!=null?maxSort:0)+1);
		}
		role.setStatus(ToolUtil.intToByte(1));
		int i = roleMapper.insertSelective(role);
		return i;
		
		
	}
	/**
     * 修改角色信息
     * @param roleVO
     * @return
     */
	@Override
	public int updateRole(RoleVO roleVO) throws QmdException{
		Role role2= roleMapper.getRoleByName(roleVO.getName());
		Role role = CopyBeanUtil.copyBean(roleVO, Role.class);
		role.setUpdateDate(new Date());
		//角色名不能重复
		if(role2!=null&&role2.getId().equals(roleVO.getId())) {
			throw new QmdException("角色名已存在");
		}
		return roleMapper.updateByPrimaryKeySelective(role);

	}

	/**
     * 删除角色信息（逻辑删除）
     * @param id
     * @return
     */
	@Override
	public int deleteRole(Long id) throws QmdException{
		Role role = new Role();
		role.setId(id);
		role.setStatus(ToolUtil.intToByte(0));
		int i = roleMapper.updateByPrimaryKeySelective(role);
		return i;

	}

	
	/**
     * 根据角色id查询菜单信息
     * @param roleId
     * @return
     */
	@Override
	public List<PermissionVO> getPermissionByRoleId(Long roleId) {
		return roleMapper.getPermissionByRoleId(roleId);
	}

	/**
	 * 根据用户名查询用户所有菜单
	 * @param userName
	 * @return
	 */
	@Override
	public List<PermissionVO> getPermissionByUserName(String userName, Byte type ){
		return roleMapper.getPermissionByUserName(userName,type);
	}

	@Override
	public List<RoleVO> getPlatformOpenRolesList() {
		SysPropertiesVO properties = sysProperties.getSysPropertiesInfoById(UCSysPropertiresEnums.PLATFORMOPENROLES.getId());
		List<RoleVO> resultList = null;
		if (properties.getId() != null) {
			resultList = roleMapper.getRolesByRoleIds(properties.getValue().split(","));
		}
		return resultList;
	}

	@Override
	public RoleVO getShopManagerRoleInfo() {
		RoleVO roleVO = null;
		SysPropertiesVO  pro = sysProperties.getSysPropertiesInfoById(SysPropertiresEnums.SHOPMANAGERROLE.getId());
		if (Objects.nonNull(pro)) {
			Role role = roleMapper.selectById(Long.parseLong(pro.getValue()));
			roleVO = CopyBeanUtil.copyBean(role, RoleVO.class);
		}
		return roleVO;
	}

	/**
	 * 根据角色名查角色
	 * @param name
	 * @return
	 */
	@Override
	public RoleVO getRoleByRoleName(String name) {
		Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("name", name));
		if(role == null) {
			return null;
		}
		RoleVO roleVO = CopyBeanUtil.copyBean(role, RoleVO.class);
		return roleVO;
	}

	/**
	 * 根据用户id查询用户角色列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<RoleVO> getRoleByUserId(Long userId) {	
		return roleMapper.getRoleByUserId(userId);
	}

	@Override
	public List<RoleVO> getAllRole(String roleName) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		if(roleName != null && ! roleName.equals("")) {
			queryWrapper.likeLeft("name", roleName);
		}
		List<Role> list = roleMapper.selectList(queryWrapper);
		if(list == null) {
			return null;
		}
		List<RoleVO> roleVOs = CopyBeanUtil.copyList(list, RoleVO.class);
		return roleVOs;
	}

	@Override
	public PageInfo<RoleVO> queryPageRole(String roleName, Integer pageNum, Integer pageSize) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(roleName)) {
			queryWrapper.likeLeft("name", roleName);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list = roleMapper.selectList(queryWrapper);
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		List<RoleVO> roleVOs = CopyBeanUtil.copyList(list, RoleVO.class);
		PageInfo<RoleVO> pageInfo = new PageInfo<>(roleVOs);
		return pageInfo;
	}
}
