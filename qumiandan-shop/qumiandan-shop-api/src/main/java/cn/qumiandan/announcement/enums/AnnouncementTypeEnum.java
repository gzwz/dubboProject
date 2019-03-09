package cn.qumiandan.announcement.enums;

import lombok.Getter;

@Getter
public enum AnnouncementTypeEnum {
	SHOPANNOUNCEMENT(new Byte("1"), "店铺公告"),
	PLATFORMANNOUNCEMENT(new Byte("2"), "平台公告"),
	PFTOSALEMANANNOUNCEMENT(new Byte("3"), "平台对业务员公告"),
	PFTOSHOPANNOUNCEMENT(new Byte("4"), "平台对店铺公告"),
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	
	private AnnouncementTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
	

}
