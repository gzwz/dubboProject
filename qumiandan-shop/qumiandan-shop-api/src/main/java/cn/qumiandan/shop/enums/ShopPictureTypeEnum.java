package cn.qumiandan.shop.enums;

import lombok.Getter;
/**
 * 店铺图片类型枚举
 * @author lrj
 *
 */
@Getter
public enum ShopPictureTypeEnum {
//	类型(1：营业执照（营业执照(三证合一)不能为空）；
//	2：其他资质；
//	3：门头照片；
//	4：法人手持身份证照片；
//	5：商家相册;
//	6：logo；
//	7：法人身份证正面照片；
//	8：法人身份证反面照片；
//	9：入账银行卡正面照片；
//	10：门脸(内设)照片(前台)；
//	11：入账非法人证明照片 ；
//	12：对私账户身份证正面照片；
//	13：对私账户身份证反面照片；)
// 	14: 食品或卫生许可证
//  15: 特种行业许可证
//  16: 其他	
	
	
	BusinessLicense(new Byte("1"),"营业执照"),
	OtherQualifications(new Byte("2"),"其他资质"),
	DoorPhotos(new Byte("3"),"门头照片"),
	LegalPersonHoldingIDCard(new Byte("4"),"法人手持身份证照片"),
	MerchantAlbum(new Byte("5"),"商家相册"),
	Logo(new Byte("6"),"logo"),
	FrontOfCorporateIDCard(new Byte("7"),"法人身份证正面照片"),
	OppositeOfCorporateIDCard(new Byte("8"),"法人身份证反面照片"),
	FrontOfBankCard(new Byte("9"),"入账银行卡正面照片"),
	Reception(new Byte("10"),"门脸(内设)照片(前台)"),
	IllegalOersonCertificate(new Byte("11"),"入账非法人证明照片"),
	FrontOfPrivateAccountIDCard(new Byte("12"),"对私账户身份证正面照片"),
	OppositePrivateAccountIDCard(new Byte("13"),"对私账户身份证反面照片"),
	FoodHygieneLicense(new Byte("14"),"对私账户身份证反面照片"),
	SpecialIndustryLicense(new Byte("15"), "特种行业许可证"),
	Other(new Byte("16"), "其他")
	;
	/** 状态*/
	private Byte code;
	/** 状态说明*/
	private String name;
	
	ShopPictureTypeEnum(Byte code, String name) {
		this.code = code;
		this.name = name;
	}
}
