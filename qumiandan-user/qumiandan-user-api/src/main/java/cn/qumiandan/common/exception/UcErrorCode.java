package cn.qumiandan.common.exception;

import cn.qumiandan.common.interfaces.IGenericEnum;
import lombok.Getter;

/**
 * 基础异常枚举
 * @author W
 *  
 */
@Getter
public enum UcErrorCode implements IGenericEnum {
	/** 成功*/
	UC0000("UC0000", "成功"),
	/** 系统异常*/
	UC9999("UC9999", "系统异常"),
	/** 数据库服务异常 */
	UC9002("UC9002", "数据库服务异常"),
	/** Redis服务器异常*/
	UC9003("UC9003", "Redis服务器异常"),
	
	
	/** 业务异常 */
	UC1001("UC1001", "业务异常"),
	;
	
	private String code;
	private String msg;
	
	private UcErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
