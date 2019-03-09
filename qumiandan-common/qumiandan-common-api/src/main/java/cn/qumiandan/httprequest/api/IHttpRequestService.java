package cn.qumiandan.httprequest.api;

import java.util.Map;

/**
 * http请求service
 * @author yuleidian
 * @version 创建时间：2018年12月5日 上午10:27:58
 */
public interface IHttpRequestService {

	/**
	 * 发送post请求
	 * @param url
	 * @param param
	 * @return
	 */
	String postRequest(String url, Map<String, String> param);
	
	/**
	 * 发送post请求
	 * @param url
	 * @param param
	 * @return
	 */
	String doPostForJson(String url, Map<String, String> param);
	
	/**
	 * 发送post请求
	 * @param url
	 * @param param
	 * @return
	 */
	String doGetForJson(String url, Map<String, String> param);
	
	/**
	 * 发送post请求
	 * @param url
	 * @param entity
	 * @return
	 */
	String doPostForString(String url, String entity);
	
}
