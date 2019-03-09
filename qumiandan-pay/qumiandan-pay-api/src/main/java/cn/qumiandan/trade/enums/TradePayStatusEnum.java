package cn.qumiandan.trade.enums;

import lombok.Getter;

/**
 * 交易流水状态枚举
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午3:32:40
 */
@Getter
public enum TradePayStatusEnum {
	
	/** 创建待支付*/
	CREATE(new Byte("1"), "创建支付"),
	/** 已支付*/
	PAYED(new Byte("2"), "已支付"),
	/** 取消支付*/
	CANCEL(new Byte("3"), "取消支付"),
	/** 退款*/
	REFUND(new Byte("4"), "退款"),
	/** 支付异常(目前处理  回调金额和流水金额 不一致)*/
	EXCEPTION(new Byte("5"), "订单出现异常支付"),
	
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	TradePayStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
