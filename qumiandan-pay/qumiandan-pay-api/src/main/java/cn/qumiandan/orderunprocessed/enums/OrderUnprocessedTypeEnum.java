package cn.qumiandan.orderunprocessed.enums;

import lombok.Getter;

/**
 * 支付订单未处理类型枚举
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午1:40:19
 */
@Getter
public enum OrderUnprocessedTypeEnum {

	/** 回调金额和预支付订单金额不一致*/
	CALLBACKMONEY_EXCEPTION(new Byte("1"), "回调金额和预支付订单金额不一致"),
	/** 并发下订单多次尝试处理回调结果失败*/
	ORDERUNPROCESSED_EXCEPTION(new Byte("2"), "并发下订单多次尝试处理回调结果失败"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	OrderUnprocessedTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
