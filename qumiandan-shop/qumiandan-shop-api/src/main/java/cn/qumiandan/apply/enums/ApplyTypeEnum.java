package cn.qumiandan.apply.enums;

import lombok.Getter;
/**
 * 申请类型枚举
 * @author lrj
 *
 */
@Getter
public enum ApplyTypeEnum {
//	申请类型（5：申请开通店铺，4.申请省代理，3.申请市代理，2.申请区代，1.申请成为业务员）
	ApplyOpenShop(new Byte("5"),"申请开通店铺"),
	ApplyToBeProAgent(new Byte("4"),"申请省代理"),
	ApplyToBeCityAgent(new Byte("3"),"申请市代理"),
	ApplyToBeCountryAgent(new Byte("2"),"申请区代"),
	ApplyToBeSaleman(new Byte("1"),"申请成为业务员"),
	;
	private Byte code;
	private String message;
	ApplyTypeEnum(Byte code,String message){
		this.code = code;
		this.message = message;
	}
}
