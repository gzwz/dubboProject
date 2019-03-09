package cn.qumiandan.pay;

import java.util.Map;

import cn.qumiandan.common.interfaces.Jsonable;

/**
 * 请求定义
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午2:53:44
 */
public interface RequestDefinition extends Jsonable {

	/**
	 * 获取签名
	 * @return
	 */
	String getSign();
	
	/**
	 * 初始化固有参数(一般是配置中的固定参数)
	 * @param params
	 */
	void initInherentParameter(Object...params);
	
	/**
	 * 加密
	 * @param str
	 */
	String encrypt(String content);
	
	/**
	 * 获取请求参数
	 * @return
	 */
	Map<String, String> getRequestParameter();
	
}
