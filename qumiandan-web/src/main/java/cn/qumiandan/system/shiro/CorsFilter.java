package cn.qumiandan.system.shiro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 解决options
 * @author W
 * 2019年1月8日
 */
@Component
public class CorsFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
		response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域访问报错
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600"); // 设置过期时间
		response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP1.1.
		response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. 
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
