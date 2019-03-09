package cn.qumiandan.common.enums;

import lombok.Getter;

/**
 * 用户中心 系统配置文件枚举
 * @author yuleidian
 * @version 创建时间：2018年11月26日 下午5:59:18
 */
@Getter
public enum SysPropertiresEnums {
	
	PLATFORMOPENROLES(1L, "平台放开可选角色"),
	SHOPMAXMEMBERS(2L, "系统限制店铺成员管理数量"),
	SHOPMANAGERROLE(3L, "店铺管理员角色ID"),
	VALIDATIONTICKETENDTIME(4L, "订单核销券到期时间"),
	PLATFORMOPENMERCHAT(5L, "平台开放注册商户结算类型(对应扫呗)"),
	
	;
	
	private Long id;
	
	private String name;
	
	SysPropertiresEnums(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
