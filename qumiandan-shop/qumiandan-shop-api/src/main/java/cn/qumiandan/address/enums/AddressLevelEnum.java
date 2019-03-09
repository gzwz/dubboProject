package cn.qumiandan.address.enums;

import lombok.Getter;
/**
 * 地址级别枚举
 * @author lrj
 *
 */
@Getter
public enum AddressLevelEnum {
	Province(new Integer(1), "省级"),
	City(new Integer("2"), "市级"),
	District(new Integer("3"), "区级"),
	Town(new Integer("4"), "乡镇级"),
	;
	/** 状态*/
	private Integer code;
	/** 状态说明*/
	private String name;
	
	AddressLevelEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
