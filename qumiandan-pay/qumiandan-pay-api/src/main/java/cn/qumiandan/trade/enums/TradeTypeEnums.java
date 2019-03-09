package cn.qumiandan.trade.enums;

import lombok.Getter;

/**
 * 交易流水 交易类型
 * 交易类型: 1.充值，2:消费，3.（转入）收到转账，4.（转出），5.提现
 * @author yuleidian
 * @version 创建时间：2018年12月17日 下午3:55:19
 */
@Getter
public enum TradeTypeEnums {

	// 交易类型: 1.充值，2:消费，3.（转入）收到转账，4.（转出），5.提现
	RECHARGE(new Byte("1"), "充值"),
	CONSUME(new Byte("2"), "消费"),
	TRANSFERIN(new Byte("3"), "(转入)转账"),
	TRANSFEROUT(new Byte("4"), "(转出)转账"),
	WITHDRAW(new Byte("5"), "提现"),
	;
	/** 类型码*/
	private Byte code;
	
	/** 类型名称*/
	private String name;
	
	TradeTypeEnums(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
