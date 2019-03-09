package test.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.user.api.IUserService;
import cn.qumiandan.user.vo.BackStageQueryUserParamsVO;
import cn.qumiandan.user.vo.SetPasswordVO;
import cn.qumiandan.user.vo.UserAddVO;
import cn.qumiandan.user.vo.UserInfoVO;
import cn.qumiandan.user.vo.UserUpdateVO;
import cn.qumiandan.user.vo.UserVO;
import cn.qumiandan.utils.MD5Utils;
import test.BaseTest;

public class UserTest extends BaseTest {

	@Resource
	private IUserService us;


	@Test
	public void add() {
	}

	@Test
	public void getUserByUsername(){
		System.out.println(("----- getUserByUsername method test ------"));
		System.out.println(us.getUserByUsername("15085958874"));
	}

	@Test
	public void getUserByUsernameAndPwd(){
		System.out.println(("----- getUserByUsernameAndPwd method test ------"));
		UserVO userVO = us.getUserByUsernameAndPwd("test","e10adc3949ba59abbe56e057f20f883e");
		userVO = us.getUserByUsernameAndPwd("test","123456");
		System.out.println(userVO);
	}

	@Test
	public void getUserByUnionidOrOpenId(){
		System.out.println(("----- getUserByUnionidOrOpenId method test ------"));
		System.out.println(us.getUserByUnionidOrOpenId("ovDz75_qd4EvGiaLyCXuziTLF954",null));
	}

	@Test
	public void addUser() throws Exception{
		System.out.println(("----- addUserByProperty method test ------"));
		UserAddVO userAddVO = new UserAddVO("test123123","113","tt");
		System.out.println(us.addUser(userAddVO));
	}
	
	
	@Test
	public void updateUserInfoByUserId() throws Exception{
		System.out.println(("----- updateUserInfoByUserId method test ------"));
		UserAddVO userAddVO = new UserAddVO();
		userAddVO.setId(4L);
		userAddVO.setName("测试");
		System.out.println(us.updateUserInfoByUserId(userAddVO));

	}
	
	
//	updateUserRolesByUserId
	@Test
	public void updateUserRolesByUserId () throws Exception{
		System.out.println(("----- updateUserRolesByUserId method test ------"));
		UserUpdateVO userAddVO=new UserUpdateVO();
		userAddVO.setUserId(5L);
		List<Long> list=new ArrayList<>();
		list.add(5L);
		userAddVO.setRoleIds(list);
		System.out.println(us.updateUserRolesByUserId(userAddVO));

	}
	

	@Test
	public void deleteByUserId () throws Exception{
		System.out.println(("----- deleteByUserId method test ------"));
		System.out.println(us.deleteByUserId(1L));

	}

	@Test
	public void getUserPageList () throws Exception{
		System.out.println(("----- getUserPageList method test ------"));
		System.out.println(us.getUserPageList(1,10));

	}
	
//	deleteUserRoleByUserId
	@Test
	public void deleteUserRoleByUserId () throws Exception{
		System.out.println(("----- deleteUserRoleByUserId method test ------"));
		System.out.println(us.deleteUserRoleByUserId(1L));

	}
	
//	getRoleByUserName
	/*
	 * @Test public void getRoleByUserName () {
	 * System.out.println(("----- getRoleByUserName method test ------"));
	 * System.out.println(us.getRoleByUserName("test123123"));
	 * 
	 * }
	 */
	@Test
	public void updateUserInfo () {
		System.out.println(("----- getRoleByUserName method test ------"));
		UserInfoVO vo = new UserInfoVO();
		vo.setUserId(2l);
		vo.setToken("123465465465465");
		System.out.println(us.updateUserInfo(vo));

	}
	
	@Test
	public void logout () {
		System.out.println(("----- logout method test ------"));
		System.out.println(us.logout(2l));

	}
	
//	setPassword
	
	@Test
	public void setPassword () {
		System.out.println(("----- setPassword method test ------"));
		SetPasswordVO passwordVO = new SetPasswordVO();
		passwordVO.setUserName("18585511152");
		passwordVO.setNewPassword("123456");
		System.out.println(us.setPassword(passwordVO));

	}
	
	public static void main(String[] args) {
		System.out.println(MD5Utils.encode("123456",""));
	}
	
	@Test
	public void backStageQueryUser() {
		BackStageQueryUserParamsVO paramsVO = new BackStageQueryUserParamsVO();
//		paramsVO.setCityCode("110100");
		PageInfo<UserVO> backStageQueryUser = us.backStageQueryUser(paramsVO);
		System.out.println(backStageQueryUser);
	}
	
}
