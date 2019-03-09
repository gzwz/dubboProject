package cn.qumiandan.common.exception;

import cn.qumiandan.common.interfaces.IGenericEnum;
import lombok.Getter;

/**
 * 基础异常枚举
 * @author W
 *  
 */
@Getter
public enum CsErrorCode implements IGenericEnum {
	/** 成功*/
	CS0000("CS0000", "成功"),
	/** 系统异常*/
	CS0001("CS0001", "系统异常"),
	/** 数据库服务异常 */
	CS0002("CS0002", "数据库服务异常"),
	/** Redis服务器异常*/
	CS0003("CS0003", "Redis服务器异常"),
	
	
	/** 业务异常*/
	CS1000("CS1000", "业务异常"),
	/** 参数不能为空*/
	CS1001("CS1001", "参数不能为空"),
	/** 短信发送频率过快，请稍后重试*/
	CS1002("CS1002", "短信发送频率过快，请稍后重试"),
	/** 图片验证码IO异常 */
	CS1003("CS1003", "图片验证码IO异常 "),
	/** 验证码已失效  */
	CS1004("CS1004", "验证码已失效"),
	/** 图片验证码未通过  */
	CS1005("CS1005", "图片验证码未通过"),
	/** 短信验证码未通过  */
	CS1006("CS1006", "短信验证码未通过"),
	
	/** 系统异常  */
	CS9999("CS9999", "系统异常"),
	;
	
	
	private String code;
	private String msg;
	private CsErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
