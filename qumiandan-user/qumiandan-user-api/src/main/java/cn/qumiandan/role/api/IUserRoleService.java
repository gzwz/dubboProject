package cn.qumiandan.role.api;

import java.util.Collection;
import java.util.List;

import cn.qumiandan.role.vo.AddUserRoleVO;
import cn.qumiandan.role.vo.BatchAddUserRoleVO;
import cn.qumiandan.role.vo.UserRoleVO;

/**
 * 用户角色关联service
 * @author yuleidian
 * @version 创建时间：2018年11月27日 下午3:09:29
 */
public interface IUserRoleService {

	/**
	 * 如果该用户已有该角色关联信息 系统将直接返回vo信息
	 * 如果没有就添加
	 * 给用户添加角色
	 * @param vo
	 * @return
	 */
	AddUserRoleVO addUserRole(AddUserRoleVO vo);
	
	/**
	 * 批量操作用户角色关联信息
	 * @param vo
	 */
	void batchOperateUserRole(BatchAddUserRoleVO vo);
	
	/**
	 * 根据用户编号和角色编号查询 关联信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
	UserRoleVO getUserRoleByUserIdAndRoleId(Long userId, Long roleId);
	
	/**
	 * 批量删除用户角色管理信息
	 * @param roleIds
	 */
	void batchDeleteUserRoleInfo(Long  userId, Collection<Long> roleIds);
	
	/**
	 * 删除用户角色关系
	 * @param id
	 * @return
	 */
	int deleteUserRoleById(Long id);
	
	/**
	 * 根据用户编号获取持有的所有角色 (未删除的)
	 * @param userId
	 * @return
	 */
	List<UserRoleVO> getUserRolesByUserId(Long userId);
}
