package cn.qumiandan.shop.enums;

import lombok.Getter;

/**
 * 店铺审核状态
 * @author yuleidian
 * @version 创建时间：2018年12月11日 下午3:43:58
 */
@Getter
public enum ShopAuditStatusEnum {

	PASS(new Byte("1"), "审核通过"),
	UNPASS(new Byte("2"), "未通过"),
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	ShopAuditStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
