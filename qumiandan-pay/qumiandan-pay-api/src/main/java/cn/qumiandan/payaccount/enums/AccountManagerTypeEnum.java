package cn.qumiandan.payaccount.enums;

import lombok.Getter;

/**
 * 账户管理类型枚举
 * @author yuleidian
 * @version 创建时间：2018年12月26日 下午5:33:42
 */
@Getter
public enum AccountManagerTypeEnum {

	/** 普通*/
	GENERIC(new Byte("1"), "通用的"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	AccountManagerTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
