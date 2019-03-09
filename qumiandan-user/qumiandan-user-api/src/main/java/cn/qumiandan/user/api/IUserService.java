package cn.qumiandan.user.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.interfaces.IBaseService;
import cn.qumiandan.user.vo.BackStageQueryUserParamsVO;
import cn.qumiandan.user.vo.SetPasswordVO;
import cn.qumiandan.user.vo.UpdateUserVO;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserInfoVO;
import cn.qumiandan.user.vo.UserUpdateVO;
import cn.qumiandan.user.vo.UserVO;

public interface IUserService extends IBaseService {
	
	/**
	 * 根据id获取用户信息
	 * @param id
	 */
	UserVO getUserById(Long id);

    List<UserVO> findAllUser(int pageNum, int pageSize);

	/**
	 * 根据用户账号获取用户信息
	 * @param userName
	 * @return
	 */
	UserVO getUserByUsername(String userName);

	/**
	 * 根据用户账号和密码获取用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	UserVO getUserByUsernameAndPwd(String userName,String password);

	/**
	 * 根据unionid或openid获取用户信息
	 * @param unionId
	 * @param openId
	 * @return
	 */
	UserVO getUserByUnionidOrOpenId(String unionId,String openId);

	/**
	 * 注册-创建用户
	 * @param userAddVO
	 * @return
	 * @throws UcException
	 */
	UserAddVO addUser(UserAddVO userAddVO);

	/**
	 * 根据id修改用户基本信息
	 * @param userAddVO
	 * @return
	 * @throws UcException
	 */
	int updateUserInfoByUserId(UserAddVO userAddVO);

	/**
	 * 根据id修改用户角色
	 * @param userUpdateVO
	 * @return
	 * @throws UcException
	 */
	int updateUserRolesByUserId(UserUpdateVO userUpdateVO);

	/**
	 * 根据id修改用户信息
	 * @param userId
	 * @return
	 * @throws UcException
	 */
	int deleteByUserId(Long userId);

	/**
	 * 获取用户分页列表
	 * @return
	 */
	PageInfo<UserAddVO> getUserPageList(int pageNum, int pageSize);
	
	/**
	 * 根据用户id删除用户角色信息（sys_user_role）,逻辑删除
	 * @param userId
	 * @return
	 */
	int deleteUserRoleByUserId(Long userId);
	
	/**
	 * 更新用户扩张表信息
	 * @param vo
	 * @return
	 */
	int updateUserInfo(UserInfoVO vo);
	
	/**
	 * 用户登出
	 * @param userId
	 * @return
	 */
	int logout(Long userId);
	
	/**
	 * 根据用户编号查询用户扩展信息
	 * @param userId
	 * @return
	 */
	UserInfoVO getUserInfoByUserId(Long userId);
	
	/**
	 * 设置密码
	 * @param passwordVO
	 * @return
	 */
	int setPassword(SetPasswordVO passwordVO);
	
	/**
	 * 忘记密码
	 * @param passwordVO
	 * @return
	 */
	int resetPassword(SetPasswordVO passwordVO);
	
	/**
	 * 更新主表信息
	 * @param vo
	 * @return
	 */
	int updateUser(UpdateUserVO vo);
	
	/**
	 * 根据用户id查询(只返回user主表信息)
	 * @param idList
	 * @return
	 */
	List<UserVO> getUserByUserIds(List<Long> idList);
	
	/**
	 * 总后台查询用户
	 * @param paramsVO
	 * @return
	 */
	PageInfo<UserVO> backStageQueryUser(BackStageQueryUserParamsVO paramsVO);
}