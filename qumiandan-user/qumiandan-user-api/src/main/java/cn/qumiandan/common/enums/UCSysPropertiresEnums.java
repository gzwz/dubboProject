package cn.qumiandan.common.enums;

import lombok.Getter;

/**
 * 用户中心 系统配置文件枚举
 * @author yuleidian
 * @version 创建时间：2018年11月26日 下午5:59:18
 */
@Getter
public enum UCSysPropertiresEnums {
	
	PLATFORMOPENROLES(1L, "平台放开可选角色"),
	;
	
	private Long id;
	
	private String name;
	
	UCSysPropertiresEnums(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
