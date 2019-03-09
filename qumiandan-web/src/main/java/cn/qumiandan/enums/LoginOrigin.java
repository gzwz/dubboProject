package cn.qumiandan.enums;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.interfaces.IByteEnum;
import lombok.Getter;

/**
 * 登录端来源
 * @author yuleidian
 * @date 2019年1月18日
 */
@Getter
//@Component
public enum LoginOrigin implements IByteEnum {

	/** 普通用户端*/
	User(new Byte("1"), "普通用户端"),
	/** 商家端*/
	Merchant(new Byte("2"), "商家端"),
	/** 业务员端*/
	Samleman(new Byte("3"), "业务员端"),
	/** 总代理端*/
	Agent(new Byte("4"), "总代理端") ,
	/** 后台登录*/
	Background(new Byte("5"), "总后台端"),
	;
	private String name;
	
	private Byte code;

	LoginOrigin(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static LoginOrigin getOrigin(int code) {
		for (LoginOrigin o : LoginOrigin.values()) {
			if (code == o.getCode()) {
				return o;
			}
		}
		throw new QmdException("暂不支持此端登录");
	}
}
