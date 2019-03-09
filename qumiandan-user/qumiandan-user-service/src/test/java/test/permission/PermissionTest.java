package test.permission;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import cn.qumiandan.utils.ToolUtil;
import org.junit.Test;
import cn.qumiandan.permission.api.IPermissionService;
import cn.qumiandan.permission.mapper.PermissionMapper;
import cn.qumiandan.permission.vo.PermissionVO;
import cn.qumiandan.role.api.IRolePermissionService;
import cn.qumiandan.role.vo.AddRolePermissionVO;
import test.BaseTest;

public class PermissionTest extends BaseTest{

	@Resource
    private IPermissionService permissionService;
	@Resource
	private PermissionMapper  permissionMapper;
	@Resource IRolePermissionService rolePermissionService;
	
	 
	@Test
	public void addPermission() {
		System.out.println("------addPermission-------");
		PermissionVO permissionVO = new PermissionVO();
		permissionVO.setName("测试6311");
		int i = permissionService.addPermission(permissionVO);
		System.out.println(i);
	}

	@Test
	public void updatePermission() {
		System.out.println("------updatePermission-------");
		PermissionVO permissionVO = new PermissionVO();
		permissionVO.setId(14L);
		permissionVO.setPId(6L);;
		permissionVO.setName("更新权限11");
		permissionVO.setPermission("/permission/updatePermission1");
		permissionVO.setType(ToolUtil.intToByte(2));
		System.out.println(permissionService.updatePermission(permissionVO));
	}
	
	@Test
	public void addRolePermission() {
		System.out.println("-------addRolePermission-------");
		AddRolePermissionVO permissionVO=new AddRolePermissionVO();
		permissionVO.setRoleId(1L);
		List<Long> permissionIds= new ArrayList<Long>();
		permissionIds.add(1L);
		permissionVO.setPermissionIds(permissionIds);
		//System.out.println(rolePermissionService.addOneRolePermission(permissionVO));
	}
//	updateRolePermission
	@Test
	public void updateRolePermission() {
		System.out.println("------updateRolePermission-------");
		AddRolePermissionVO permissionVO=new AddRolePermissionVO();
		permissionVO.setRoleId(1L);
		List<Long> permissionIds= new ArrayList<Long>();
		permissionIds.add(1L);
		permissionVO.setPermissionIds(permissionIds);
		System.out.println(rolePermissionService.updateRolePermission(permissionVO));
	}
	
	
	
	@Test
	public void deletePermission() {
		System.out.println("------deletePermission-------");
		System.out.println(permissionService.deletePermission(6L));
	}
	
//	getAllPermission
	
	@Test
	public void getAllPermission() {
		System.out.println("------getAllPermission-------");
		List<PermissionVO> permission = permissionService.getAllPermission(null);
		System.out.println(permission);
	}
}
