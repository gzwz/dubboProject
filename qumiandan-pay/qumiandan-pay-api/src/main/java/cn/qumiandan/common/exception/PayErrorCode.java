package cn.qumiandan.common.exception;

import cn.qumiandan.common.interfaces.IGenericEnum;
import lombok.Getter;

/**
 * 基础异常枚举
 * @author W
 *  
 */
@Getter
public enum PayErrorCode implements IGenericEnum {
	/** 成功*/
	PAY0000("PAY0000", "成功"),
	/** 系统异常*/
	PAY0001("PAY0001", "系统异常"),
	/** 数据库服务异常 */
	PAY0002("PAY0002", "数据库服务异常"),
	/** Redis服务器异常*/
	PAY0003("PAY0003", "Redis服务器异常"),
	
	
	/** 支付异常 */
	PAY1000("PAY1000", "支付异常"),
	/** 用户名不能为空 */
	PAY1001("PAY1001", "用户名不能为空"),
	/** 业务异常*/
	PAY1002("PAY1002", "业务异常"),
	
	/** 特殊错误code 发现支付流水金额与回调金额不一致时使用*/
	PAY1003("PAY1003", "支付流水异常"),
	
	
	/** 批量添加今日可提现余额异常*/
	PAY7000("PAY7000", "批量添加今日可提现余额异常"),
	
	/** 第三方回调 接口返回异常信息*/
	PAY8000("PAY8000", "第三方回调 接口返回异常信息")
	;
	
	private String code;
	private String msg;
	
	private PayErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
