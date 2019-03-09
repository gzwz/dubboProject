package cn.qumiandan.shop.enums;

import lombok.Getter;

@Getter
public enum MerchantTypeEnum {
	
	SMALLBUSINESSES(new Byte("1"), "小微商户对私结算"),
	INDIVIDUALCORP(new Byte("2"), "个体户对私非法人结算"),
	INDIVIDUALUNINCORP(new Byte("3"), "个体户对私法人结算"),
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	MerchantTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
