package cn.qumiandan.ticket.enums;

import lombok.Getter;

@Getter
public enum TicketStatusEnums {
	UNUSE(new Byte("1"), "未消费"),
	USING(new Byte("2"), "消费中"),
	USED(new Byte("3"), "已消费"),
	Returning(new Byte("4"), "返现审核中"),
	Returned(new Byte("5"), "已返现"),
	Deleted(new Byte("6"), "已删除"),
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	TicketStatusEnums(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
