package cn.qumiandan.payaccount.enums;

import lombok.Getter;

/**
 * 账户类型枚举
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午1:37:45
 */
@Getter
public enum AccountTypeEnum {
	
	Saleman(new Byte("1"), "业务员"),
	CountryAgent(new Byte("2"), "区代理"),
	CityAgent(new Byte("3"), "市代理"),
	ProvinceAgent(new Byte("4"), "省代理"),
	Shop(new Byte("5"), "店铺")
	
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	AccountTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
