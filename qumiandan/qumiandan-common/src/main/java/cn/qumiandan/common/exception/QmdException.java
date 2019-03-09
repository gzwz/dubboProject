package cn.qumiandan.common.exception;

import cn.qumiandan.common.interfaces.IGenericEnum;

/**
 * 统一全局异常
 * @author yuleidian
 * @version 创建时间：2019年1月6日 下午2:05:29
 */
public class QmdException extends RuntimeException implements IBaseException{

	private static final long serialVersionUID = 1L;

	private String code;
	
	private String message;
	
	
	public QmdException(String message) {
		this(IGenericEnum.QMD1000, message);
	}
	
	public QmdException(IGenericEnum generic) {
		this(generic.getCode(), generic.getMsg());
	}

	public QmdException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return message;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(message).toString();
	}
}
