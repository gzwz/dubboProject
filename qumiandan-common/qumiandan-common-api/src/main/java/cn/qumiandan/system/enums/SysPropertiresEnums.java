package cn.qumiandan.system.enums;

import lombok.Getter;

/**
 * 用户中心 系统配置文件枚举
 * @author yuleidian
 * @version 创建时间：2018年11月26日 下午5:59:18
 */
@Getter
public enum SysPropertiresEnums {
	
	/** 平台放开可选角色 ---->数据格式   :  1,2,3,4,5  */
	PLATFORMOPENROLES(1L, "平台放开可选角色"),
	SHOPMAXMEMBERS(2L, "系统限制店铺成员管理数量"),
	SHOPMANAGERROLE(3L, "店铺管理员角色ID"),
	VALIDATIONTICKETENDTIME(4L, "订单核销券到期时间"),
	PLATFORMOPENMERCHAT(5L, "平台开放注册商户结算类型(对应扫呗)"),
	Saleman(6L,"业务员"),
	ProvinceAgent(7L,"省代理"),
	CityAgent(8L,"市代理"),
	CountryAgent(8L,"市代理"),
	/** 规定能提现的时间 ---->数据格式   :  00-24  */
	WithdrawTimeLimit(9L,"规定能提现的时间"),
	/** 提现最小金额限制 ---->数据格式   :  1000 单位分  */
	WithdrawAmountMinLimit(10L, "提现最小金额限制"),
	/** 总台后角色  */
	BackgroundRoleId(11L, "总台后角色");
	;
	
	private Long id;
	
	private String name;
	
	SysPropertiresEnums(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
