package cn.qumiandan.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.utils.FilterUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.utils.ShiroUtils;

/**
 * 登录重写
 * @author yuleidian
 * @version 创建时间：2018年11月2日 上午10:54:09
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			ShiroUtils.getUserId();
		} catch (QmdException e) {
			FilterUtil.out(response, ResultUtils.error(GwErrorCode.GW1004));
			return false;
		}
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}

}
