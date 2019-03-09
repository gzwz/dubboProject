package cn.qumiandan.saleman.enums;

import cn.qumiandan.common.exception.QmdException;
import lombok.Getter;
/**
 * 业务员类型枚举
 * @author lrj
 *
 */
@Getter
public enum SalemanTypeEnums {

	/** 业务员 */
	Saleman((byte)1, "业务员"),
	/** 区代理*/
	CountryAgent((byte)2, "区代理"),
	/** 市代理 */
	CityAgent((byte)3, "市代理"),
	/** 省代理*/
	ProAgent((byte)4, "省代理"),
	
	;
	private Byte code;
	
	private String message;

	private SalemanTypeEnums(Byte code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
	public static SalemanTypeEnums getType(Byte type) {
		for (SalemanTypeEnums typeEnum: SalemanTypeEnums.values()) {
			if (typeEnum.getCode().equals(type)) {
				return typeEnum;
			}
		}
		throw new QmdException("目前不支持当前类型的代理类型");
	}
}
