package cn.qumiandan.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.qumiandan.common.exception.GwErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.LogPrintUtils;
import cn.qumiandan.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * controller异常处理
 * 
 * @author yuleidian
 * @version 创建时间：2018年11月2日 上午9:55:29
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpMessageNotReadableException.class, BindException.class, MethodArgumentNotValidException.class })
	public Result<Void> handleHttpBindingException(BindException e, IllegalStateException ie) {
		BindingResult bindingResult = e.getBindingResult();
		if (bindingResult != null && bindingResult.hasErrors()) {
			return ResultUtils.paramsError(bindingResult);
		}
		return ResultUtils.error(GwErrorCode.GW1001);
	}

	/**
	 * 不支持当前请求方法
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("", e);
		return ResultUtils.error(GwErrorCode.GW1002);
	}

	/**
	 * shiro权限异常处理
	 * 
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public Result<Void> unauthorizedException(UnauthorizedException e) {
		log.error("", e);
		return ResultUtils.error(GwErrorCode.GW1003);
	}

	/**
	 * 系统错误
	 * 
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result<Void> handleException(Exception e) {
		log.error("", e);
		return ResultUtils.error();
	}

	/**
	 * 系统错误
	 * @param e
	 * @return
	 */
	
	/*@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public Result<Void> handleRuntimeException(RuntimeException e) {
		log.error("", e);
		return ResultUtils.error(e.getMessage());
	}*/
	 

	/**************************************
	 * 拦截各个服务异常
	 ****************************************/
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({QmdException.class})
	public Result<String> handleServiceException(Exception e) {
		if (e instanceof QmdException) {
			QmdException ex = (QmdException) e;
			log.error(LogPrintUtils.getServiceLogPrint(ex.getCode(), ex.getMsg()));
			if (GwErrorCode.GW1004.getCode().equals(ex.getCode())) {
				return ResultUtils.error(GwErrorCode.GW1004);
			}
			return ResultUtils.error(ex.getMsg());
		}
		return ResultUtils.error(e.getMessage());
	}
}
