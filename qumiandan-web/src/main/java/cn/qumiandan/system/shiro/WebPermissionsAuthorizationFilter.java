package cn.qumiandan.system.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.utils.FilterUtil;
import cn.qumiandan.utils.ResultUtils;

/**
 * 重写权限filter
 * 角色 权限验证
 * @author yuleidian
 * @version 创建时间：2018年11月2日 上午9:45:09
 */
public class WebPermissionsAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected  boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception{
    	Subject subject = this.getSubject(request, response);
        String[] perms = (String[]) ((String[]) mappedValue);
        boolean isPermitted = true;

        if (subject.getPrincipal() == null) {
            if(FilterUtil.isAjax(request)){
                FilterUtil.out(response, ResultUtils.error(GwErrorCode.GW1004));
            }else{
                this.saveRequestAndRedirectToLogin(request, response);
            }
            isPermitted = false;
        } else {
            if (perms != null && perms.length > 0) {
                if (perms.length == 1) {
                    if (!subject.isPermitted(perms[0])) {
                        isPermitted = false;
                    }
                } else if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
            if (!isPermitted) {
                if (FilterUtil.isAjax(request)) {
                   FilterUtil.out(response, ResultUtils.error(GwErrorCode.GW1003));
                } else {
                    return isPermitted;
                }
            }
        }
        return isPermitted;
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
    	return false;
    }
    
}
