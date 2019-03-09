package cn.qumiandan.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.user.entity.User;
import cn.qumiandan.user.vo.BackStageQueryUserParamsVO;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserVO;

@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据用户账号获取用户信息
	 * @param userName
	 * @return
	 */
	UserVO getUserByUsername(String userName);

	/**
	 * 根据微信账号获取用户信息
	 * @param unionid
	 * @return
	 */
	UserVO getUserByUnionidOrOpenId(@Param("unionId") String unionid,@Param("openId") String openId);

	/**
	 * 根据微信账号获取用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	UserVO getUserByUsernameAndPwd(@Param("userName") String userName,@Param("password") String password);

	/**
	 * 添加用户
	 * @param record
	 * @return
	 */
	int insertSelective(User record);
	
	/**
	 * 更新sys_user表的用户
	 * @param user
	 * @return
	 */
	int updateUserById(User user);
	
	/**
	 * 查询sys_user和sys_user_info的联合用户信息
	 * @return
	 */
	List<UserAddVO> getUserAndUserInfo();
	
	/**
	 * 根据userName查询角色信息
	 * @param userName
	 * @return
	 */
	List<RoleVO> getRoleByUserName(String userName);
	
	/**
	 * 根据id查询user信息
	 * @param id
	 * @return
	 */
	UserVO getUserById(Long id);
	
	/**
	 * 设置密码
	 * @param userName
	 * @param password
	 * @return
	 */
	int setPassword(@Param("userName")String userName, @Param("password")String password);

	/**
	 * 总后台查询用户
	 * @param paramsVO
	 * @return
	 */
	List<UserVO> backStageQueryUser(@Param("paramsVO") BackStageQueryUserParamsVO paramsVO);
}