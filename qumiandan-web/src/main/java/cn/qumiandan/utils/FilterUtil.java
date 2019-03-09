package cn.qumiandan.utils;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.qumiandan.common.pojo.Result;

/**
 * 判断请求类型
 * 
 */
public abstract class FilterUtil {

	private static final Logger log = LoggerFactory.getLogger(FilterUtil.class);

	/**
	 * 是否是Ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("Content-Type");
		if ("application/json".equalsIgnoreCase(header)) {
			return true;
		}
		return false;
	}

	/**
	 * 使用response输出JSON
	 * 
	 * @param response
	 * @param resultMap
	 */
	public static void out(ServletResponse response, Map<String, Object> resultMap) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.println(GsonHelper.create().toJson(resultMap));
		} catch (Exception e) {
			log.error(e + "输出JSON出错");
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	public static <T> void out(ServletResponse response, Result<T> result) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.write(result.toJson());
		} catch (Exception e) {
			log.error(e + "输出JSON出错");
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	public static void out(ServletResponse response, String json) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.println(json);
		} catch (Exception e) {
			log.error(e + "输出JSON出错");
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
