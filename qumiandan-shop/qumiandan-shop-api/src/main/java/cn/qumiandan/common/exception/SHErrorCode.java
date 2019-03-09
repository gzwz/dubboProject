package cn.qumiandan.common.exception;

import cn.qumiandan.common.interfaces.IGenericEnum;
import lombok.Getter;

/**
 * 基础异常枚举
 * @author W
 *  
 */
@Getter
public enum SHErrorCode implements IGenericEnum {
	/** 成功*/
	SH0000("SH0000", "成功"),
	/** 系统异常*/
	SH0001("SH0001", "系统异常"),
	/** 数据库服务异常 */
	SH0002("SH0002", "数据库服务异常"),
	/** Redis服务器异常*/
	SH0003("SH0003", "Redis服务器异常"),
	
	
	/** 店铺中心业务异常 */
	SH1000("SH1000", "店铺中心业务异常"),
	/** 用户名不能为空 */
	SH1001("SH1001", "用户名不能为空"),
	/** 店铺不存在 */
	SH1002("SH1002", "店铺不存在"),
	
	;
	private String code;
	private String msg;
	
	private SHErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
