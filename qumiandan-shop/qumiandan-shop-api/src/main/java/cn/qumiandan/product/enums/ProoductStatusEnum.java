package cn.qumiandan.product.enums;

import lombok.Getter;

@Getter
public enum ProoductStatusEnum {
//	状态（1：待审核；2：审核未通过；3：审核通过，上架；4：下架；5：强制下架；6：已删除）
	WaitingAudit(new Byte("1"), "待审核"),
	AuditUnPass(new Byte("2"), "审核未通过"),
	AuditPass(new Byte("3"), "审核通过，上架"),
	UnSelf(new Byte("4"), "下架"),
	ForceUnSelf(new Byte("5"), "强制下架"),
	Deleted(new Byte("6"), "已删除"),
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	ProoductStatusEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
