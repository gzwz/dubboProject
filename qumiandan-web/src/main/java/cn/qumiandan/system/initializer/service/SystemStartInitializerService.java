package cn.qumiandan.system.initializer.service;

/**
 * 初始化
 * @author yuleidian
 * @date 2019年1月18日
 */
public interface SystemStartInitializerService  {
	
	/**
	 * 初始化shiro拦截连
	 * 动态加载拦截信息
	 */
	void initShiroFilterChain();
	
}
