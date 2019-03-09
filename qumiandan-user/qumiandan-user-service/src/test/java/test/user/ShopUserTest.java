package test.user;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.user.vo.ShopUserRoleVO;
import cn.qumiandan.user.vo.ShopUserVO;
import test.BaseTest;

public class ShopUserTest extends BaseTest {

	@Resource
	private IShopUserService shopUserService;

	@Test
	public void addShopUser() {
		System.out.println(("----- addShopUser method test ------"));
		ShopUserVO shopUserVO = new ShopUserVO();
		shopUserVO.setUserName("13638108370");
		shopUserVO.setUserRoleId(1l);
		shopUserVO.setUserId(1L);
		shopUserVO.setShopId(20L);
		shopUserService.addShopUser(shopUserVO);
	}
	
	
	@Test
	public void getShopIdsByUserId() {
		System.out.println("=====getShopIdsByUserId=====");
		Set<Long> shopIdsByUserId = shopUserService.getShopIdsByUserId(1L);
		System.out.println(shopIdsByUserId);
	}
	/*
	@Test
	public void getShopIdListByUserId() {
		System.out.println(("----- getShopIdListByUserId method test ------"));
		System.out.println(shopUserService.getShopIdListByUserId(1L,1L));
	}

	
	@Test
	public void getShopUserDetailInfo() {
		System.out.println(("----- getShopIdListByUserId method test ------"));
		CommonParams params = new CommonParams();
		params.setId(1l);
		System.out.println(shopUserService.getShopUserDetailInfo(params));
	}

	@Test
	public void getUserShopIdListByUserId () {
		System.out.println(("----- getShopIdListByUserId method test ------"));
		System.out.println(shopUserService.getShopIdListByUserId(1L));
	}
	
	@Test
	public void memberExist() {
		System.out.println(("----- memberExist method test ------"));
		System.out.println(shopUserService.memberExist(1L));
	}
	
	
	@Test
	public void addMemberShopInfo() {
		System.out.println(("----- addMemberShopInfo method test ------"));
		AddShopUserVO addShopUserVO = new AddShopUserVO();
		addShopUserVO.setCode("1234");
		addShopUserVO.setMobile("13087804777");
		addShopUserVO.setRoleId(1l);
		addShopUserVO.setCreateId(1l);
		addShopUserVO.setUserId(2l);
		addShopUserVO.setShopIds(Lists.newArrayList(1l, 2l));
		shopUserService.addMemberShopInfo(addShopUserVO);
	}
	
	@Test
	public void updateMemberRole() {
		System.out.println(("----- updateMemberRole method test ------"));
		shopUserService.updateMemberRole(3l, 4l);
	}
	
	
	@Test
	public void updateMemberManageShop() {
		System.out.println(("----- updateMemberManageShop method test ------"));
		UpdateShopUserVO vo = new UpdateShopUserVO();
		vo.setShopIds(Lists.newArrayList(1l, 3l));
		vo.setShopUserId(3l);
		vo.setUpdateId(1l);
		shopUserService.updateMemberManageShop(vo);
	}
	
	@Test
	public void deleteMemberShopUser() {
		System.out.println(("----- deleteMemberShopUser method test ------"));
		//shopUserService.deleteMemberShopUser(7l);
	}
	
	
	@Test
	public void getShopUserPageListByUserId() {
		System.out.println(("----- deleteMemberShopUser method test ------"));
		CommonParams p = new CommonParams();
		p.setUserId(4L);
		System.out.println(shopUserService.getShopUserPageListByUserId(p));
	}
	
	
	@Test
	public void getShopUserDetailInfo() {
		System.out.println(("----- getShopUserDetailInfo method test ------"));
		CommonParams p = new CommonParams();
		p.setId(3l);
		p.setUserId(2l);
		System.out.println(shopUserService.getShopUserDetailInfo(p));
	}*/
	
	@Test
	public void getShopUserRoleByShopId(){
		System.out.println("======getShopUserRoleByShopId=======");
		List<ShopUserRoleVO> shopUserRoleByShopId = shopUserService.getShopUserRoleByShopId(10L);
		System.out.println(shopUserRoleByShopId);
	}
}
