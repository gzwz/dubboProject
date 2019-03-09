package cn.qumiandan.orderunprocessed.enums;

import lombok.Getter;

/**
 * 支付订单未处理状态枚举
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午1:40:19
 */
@Getter
public enum OrderUnprocessedStatusEnum {

	/** 未处理中*/
	PROCESSING(new Byte("0"), "未处理中"),
	/** 已处理*/
	PROCESSED(new Byte("1"), "已处理"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	OrderUnprocessedStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
