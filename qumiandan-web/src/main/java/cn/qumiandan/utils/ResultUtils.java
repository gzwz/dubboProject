package cn.qumiandan.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.pojo.Result;

/**
 * 返回结果utils
 * @author yld
 *
 */
public abstract class ResultUtils {
	// ################################SUCCESS#############################################
	/**
	 * 返回成功信息
	 * @return
	 */
	public static <T> Result<T> success() {
		return new Result<T>(GwErrorCode.GW0000.getCode(), GwErrorCode.GW0000.getMsg());
	} 
 	
	/**
	 * 返回成功信息
	 * @return
	 */
	public static <T> Result<T> success(String message) {
		return new Result<T>(GwErrorCode.GW0000.getCode(), message);
	} 
	
	/**
	 * 返回成功信息
	 * @return
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(GwErrorCode.GW0000.getCode(), GwErrorCode.GW0000.getMsg(), data);
	}
	
	/**
	 * 返回成功信息
	 * @return
	 */
	public static <T> Result<T> success(GwErrorCode code) {
		return new Result<T>(code.getCode(), code.getMsg());
	} 
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> success(GwErrorCode code, T data) {
		return new Result<T>(code.getCode(), code.getMsg(), data);
	}
	
	// ################################ERROR#############################################
	/**
	 * 返回成功信息
	 * @return
	 */
	public static <T> Result<T> error() {
		return new Result<T>(GwErrorCode.GW1000.getCode(), GwErrorCode.GW1000.getMsg());
	} 
 	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(String message) {
		return new Result<T>(GwErrorCode.GW1000.getCode(), message);
	} 
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(T data) {
		return new Result<T>(GwErrorCode.GW1000.getCode(), GwErrorCode.GW1000.getMsg(), data);
	}
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(GwErrorCode code) {
		return new Result<T>(code.getCode(), code.getMsg());
	}
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(GwErrorCode code, String message) {
		return new Result<T>(code.getCode(), message);
	} 
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(String message, T data) {
		return new Result<T>(GwErrorCode.GW1000.getCode(), message, data);
	}
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(GwErrorCode code, T data) {
		return new Result<T>(code.getCode(), code.getMsg(), data);
	}
	
	/**
	 * 参数检查错误
	 * @param message
	 * @return
	 */
	public static <T> Result<T> paramsError(String message) {
		return new Result<T>(GwErrorCode.GW1001.getCode(), message);
	} 
	
	/**
	 * 请求参数错误
	 * @param validResult
	 * @return
	 */
	public static <T> Result<T> paramsError(BindingResult validResult) {
		if (validResult != null && validResult.getFieldError() != null 
				&& StringUtils.isNotBlank(validResult.getFieldError().getDefaultMessage())) {
			return new Result<T>(GwErrorCode.GW1001.getCode(), validResult.getFieldError().getDefaultMessage());
		}
		return new Result<T>(GwErrorCode.GW1001.getCode(), GwErrorCode.GW1001.getMsg());
	} 
	
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public static <T> Result<T> error(String code, String message) {
		return new Result<T>(code, message);
	}
}
