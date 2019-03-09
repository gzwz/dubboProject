package cn.qumiandan.system.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.qumiandan.utils.MD5Utils;
/**
 * 密码加密
 * @author yld
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		WebLoginToken token = (WebLoginToken) authcToken;
		switch (token.getAgentType()) {
		case WECHAT:
		case VERIFICATIONCODE:
			return true;
		default:
			// 获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
			String inPassword = new String(token.getPassword());
			// 获得数据库中的密码
			String dbPassword = (String) info.getCredentials();
			// 进行密码的比对
			return this.equals(MD5Utils.encode(inPassword, inPassword), dbPassword);
		}
	}
	
}
