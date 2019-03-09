package cn.qumiandan.payaccount.enums;

import lombok.Getter;

/**
 * 提现状态枚举
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午1:40:19
 */
@Getter
public enum WithdrawEnum {

	/** 可以提现*/
	ABLE(new Byte("1"), "可以提现"),
	/** 不可以提现*/
	UNABLE(new Byte("0"), "不可以提现"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	WithdrawEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
