package cn.qumiandan.system.shiro;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.qumiandan.role.api.IRoleService;
import cn.qumiandan.user.vo.UserVO;

/**
 * 自定义Realm
 * @author 
 */
//@Slf4j
public class ShiroRealm extends AuthorizingRealm {
 
	private IRoleService roleService;
	
	private SimpleAuthenticationInfo getAuthenticationInfo(WebLoginToken token) {
		if (token.getUser() != null && token.getUser().getId() != null) {
			if (2 == token.getUser().getStatus() || 3 == token.getUser().getStatus()) {
				throw new DisabledAccountException();
			}
			return new SimpleAuthenticationInfo(token.getUser(), token.getUser().getPassword(), getName());
		}
		throw new UnknownAccountException();
	}
	
    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		WebLoginToken token = (WebLoginToken) authcToken;
		return getAuthenticationInfo(token);
		
    	/*UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        // 从数据库获取对应用户名密码的用户
        User user = userService.getUserByName(name);
        if (user != null) {
            // 用户为禁用状态
            if (!user.getLoginFlag().equals("1")) {
                throw new DisabledAccountException();
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user, //用户
                    user.getPassword(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String name = token.getUsername();
		User user = new User();
		user.setId(1234);
		user.setUsername(name);
		user.setPassword("e10adc3949ba59abbe56e057f20f883e");
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户
				user.getPassword(), // 密码
				getName() // realm name
		);
		return authenticationInfo;*/
    }

    public static class User implements Serializable{
		private static final long serialVersionUID = 1L;

		int id;
    	
    	String username;
    	
    	String password;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
    }
    
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();
		UserVO user = (UserVO) principal;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (user != null && user.getId() != null) {
			info.setRoles(roleService.getRoleEaliasSetByUserId(user.getId()));
			info.setStringPermissions(roleService.getPermissionSetByUserId(user.getId()));
		}
		return info;
    	/*System.out.println("调用");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("[ss]");
        info.addStringPermission("/content/add");
        if (principal instanceof User) {
            User userLogin = (User) principal;
            if(userLogin != null){
                List<Role> roleList = roleService.findByUserid(userLogin.getId());
                if(CollectionUtils.isNotEmpty(roleList)){
                    for(Role role : roleList){
                        info.addRole(role.getEnname());

                        List<Menu> menuList = menuService.getAllMenuByRoleId(role.getId());
                        if(CollectionUtils.isNotEmpty(menuList)){
                            for (Menu menu : menuList){
                                if(StringUtils.isNoneBlank(menu.getPermission())){
                                    info.addStringPermission(menu.getPermission());
                                }
                            }
                        }
                    }
                }
            }
        }
        return info;*/
    	/*System.out.println("授权");
    	return null;*/
    }
    
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken || token instanceof WebLoginToken;
    }

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
