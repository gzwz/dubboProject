package cn.qumiandan.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.user.vo.UserVO;

/**
 * shiro获取用户信息简化
 * @author yuleidian
 * @version 创建时间：2018年11月5日 上午10:21:49
 */
public abstract class ShiroUtils {

	/**
	 * 获取当前操作用户
	 * @return
	 */
	public static UserVO getCurrentUser() {
		Object o = SecurityUtils.getSubject().getPrincipal();
		if (o != null) {
			return (UserVO) o;
		} else {
			throw new QmdException(GwErrorCode.GW1004);
		}
	}
	
	/**
	 * 获取当前条用户Id
	 * @return
	 */
	public static Long getUserId() {
		return getCurrentUser().getId();
	}
	
	/**
	 * 登出
	 */
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		SecurityUtils.getSecurityManager().logout(subject);
	}
}
