package cn.qumiandan.common.pojo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import cn.qumiandan.common.interfaces.IGenericEnum;
import cn.qumiandan.common.interfaces.IResult;
import lombok.Data;

/**
 * 统一返回结果
 * 
 * @author yld
 * @param <T>
 */
@Data
public class Result<T> implements Serializable, IResult {
	private static final long serialVersionUID = 1L;

	/**
	 * 返回状态码
	 */
	@Expose
	private String code;

	/**
	 * 返回结果说明
	 */
	@Expose
	private String message;

	/**
	 * 返回结果对象
	 */
	@Expose
	private T data;

	/** 系统内部判断业务操作是否成功 (toJson时不会转化)*/
	private boolean success = Boolean.FALSE;
	
	public Result() {

	}

	public Result(String code) {
		this(code, null);
	}

	public Result(String code, String message) {
		this(code, message, null);
	}

	public Result(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public void setResult(IGenericEnum code) {
		this.code = code.getCode();
		this.message = code.getMsg();
	}
}
