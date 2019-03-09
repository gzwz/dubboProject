package cn.qumiandan.indexpicture.enums;

import lombok.Getter;

/**
 * 首页轮播图枚举
 * @author lrj
 *
 */
@Getter
public enum IndexPictureEnums {
	Deleted(new Byte("0"), "删除"),
	Normal(new Byte("1"), "启用"),
	Prohibit(new Byte("2"), "禁用"),
	
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	IndexPictureEnums(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
