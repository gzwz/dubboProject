package cn.qumiandan.utils;

import cn.qumiandan.common.exception.GwErrorCode;

/**
 * 日志输出结果
 * @author yuleidian
 * @version 创建时间：2018年11月7日 上午11:51:18
 */
public abstract class LogPrintUtils {
	
	private static final String COLON = ":";
	
	/**
	 * web网关日志打印结果
	 * @param code
	 * @param errorMessage
	 * @return
	 */
	public static String getLogPrint(GwErrorCode code, String errorMessage) {
		return new StringBuilder(code.getCode()).append(COLON).append(code.getMsg())
				.append(COLON).append(errorMessage).toString();
	}
	
	/**
	 * 各个服务异打印结果
	 * @param code
	 * @param errorMessage
	 * @return
	 */
	public static String getServiceLogPrint(String code, String errorMessage) {
		return new StringBuilder(code).append(COLON).append(errorMessage).toString();
	}
}
