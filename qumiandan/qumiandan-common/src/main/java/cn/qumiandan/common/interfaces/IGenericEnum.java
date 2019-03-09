package cn.qumiandan.common.interfaces;

/**
 * 中心异常枚举定义
 * @author yuleidian
 * @version 创建时间：2018年12月11日 上午10:56:10
 */
public interface IGenericEnum {
	
	/** 默认错误异常*/
	String QMD1000 = "QMD1000";
	
	/**
	 * 获取错误码
	 * @return
	 */
	String getCode();
	
	/**
	 * 获取错误信息
	 * @return
	 */
	String getMsg();
	
}
