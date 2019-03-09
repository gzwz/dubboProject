package cn.qumiandan.constant;

import lombok.Getter;
/**
 * 父级id枚举
 * @author lrj
 *
 */
@Getter
public enum ParentDataEnum {
	/** 顶级父级id*/
	RootId(0L,"顶级父级id"),
	/** */
	PlatformAccountId(1L, "平台账户顶级id"),
	;
	private String message;
	
	private Long code;

	ParentDataEnum(Long code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
