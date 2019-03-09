package test.role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.qumiandan.role.api.IRolePermissionService;
import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.role.api.IUserRoleService;
import cn.qumiandan.role.vo.AddUserRoleVO;
import cn.qumiandan.role.vo.RoleVO;
import cn.qumiandan.utils.ToolUtil;
import test.BaseTest;

public class RoleTest extends BaseTest {

    @Resource
    private IRoleService roleService;
    
    @Resource
    private IUserRoleService userRoleService;

    @Autowired
    private IRolePermissionService rolePerService;
    
    @Test
    public void batchDeleteByRoleIdAndPermissIds() {
    	Long roleId = 3L;
		Set<Long> permissIds = new HashSet<Long>();
		permissIds.add(5L);
		permissIds.add(6L);
		permissIds.add(7L);
		rolePerService.batchDeleteByRoleIdAndPermissIds(roleId, permissIds);
    }
    @Test
    public void addUserRole() {
    	AddUserRoleVO addUserRoleVO = new AddUserRoleVO();
    	addUserRoleVO.setSysRoleId(1L);
    	addUserRoleVO.setSysUserId(1L);
    	addUserRoleVO  = userRoleService.addUserRole(addUserRoleVO);
    	System.out.println(addUserRoleVO);
    }
    
    @Test
    public void getRoleListByUserId(){
        System.out.println(("----- getUserByUnionidOrOpenId method test ------"));
        roleService.getRoleListByUserId(1L);
    }

    @Test
    public void getRoleEaliasSetByUserId(){
        System.out.println(("----- getRoleEaliasSetByUserId method test ------"));
        roleService.getRoleEaliasSetByUserId(1L);
    }

    @Test
    public void getPermissionSetByUserId(){
        System.out.println(("----- getPermissionSetByUserId method test ------"));
        roleService.getPermissionSetByUserId(1L);
    }

    @Test
    public void getPermissionListByUserId(){
        System.out.println(("----- getPermissionListByUserId method test ------"));
        roleService.getPermissionListByUserId(1L);
    }
    
    @Test
    public void addRole(){
        System.out.println(("----- addRole method test ------"));
        RoleVO roleVO=new RoleVO();
        roleVO.setName("yewuyuan11");
        roleService.addRole(roleVO);
    }
    
    
    @Test
    public void updateRole(){
        System.out.println(("----- updateRole method test ------"));
        RoleVO roleVO=new RoleVO();
        roleVO.setName("测试啦");
        roleVO.setId(9L);
        roleService.updateRole(roleVO);
    }
    
    
    @Test
    public void deleteRole(){
        System.out.println(("----- deleteRole method test ------"));
        System.out.println(roleService.deleteRole(1L));
    }
    

    @Test
    public void getPermissionByRoleId(){
        System.out.println(("----- getPermissionByRoleId method test ------"));
        System.out.println(roleService.getPermissionByRoleId(1L));
    }
//    getPermissionByUserName
    @Test
    public void getPermissionByUserName(){
        System.out.println(("----- getPermissionByUserName method test ------"));
        System.out.println(roleService.getPermissionByUserName("18785273024",ToolUtil.intToByte(1)));
    }

    
    @Test
    public void getRolesByRoleIds(){
        System.out.println(("----- getPermissionByUserName method test ------"));
        System.out.println(roleService.getPlatformOpenRolesList());
    }
    
    @Test
    public void getShopManagerRoleInfo() {
    	System.out.println(("----- getPermissionByUserName method test ------"));
    	System.out.println(roleService.getShopManagerRoleInfo());
    }
    
    @Test
    public void getPlatformOpenRolesList() {
    	System.out.println(("----- getPlatformOpenRolesList method test ------"));
    	System.out.println(roleService.getPlatformOpenRolesList());
    }
    
    @Test
    public void getRoleByUserId() {
    	List<RoleVO> roleByUserId = roleService.getRoleByUserId(1L);
    	System.out.println(roleByUserId);
    }
    
    @Test
    public void getAllRole() {
    	List<RoleVO> list = roleService.getAllRole("");
    	System.out.println(list);
    }
}
