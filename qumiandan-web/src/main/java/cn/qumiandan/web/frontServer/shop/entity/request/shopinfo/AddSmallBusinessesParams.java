package cn.qumiandan.web.frontServer.shop.entity.request.shopinfo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import cn.qumiandan.web.frontServer.shop.entity.request.AddMerchantParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Deprecated
@Getter
@Setter
@ToString
public class AddSmallBusinessesParams extends AddMerchantParams implements Serializable{
	

	private static final long serialVersionUID = 1L;

	// =============小微商户对私结算上传证件照信息==========
	/** 结算人身份证号码*/
	@NotBlank(message = "结算人身份证号码")
	private String merchantIdNo;
	
	/** 对私账户身份证正面照片 */
	private String imgPrivateIdcardA;

	/** 对私账户身份证反面照片 */
	private String imgPrivateIdcardB;

	/** 本人手持身份证照片 */
	private String imgIdcardHolding;

	/** 商户门头照片 */
	private String imgLogo;

	/** 内部前台照片 */
	private String imgIndoor;

	/** 入账银行卡正面照片 */
	private String imgBankcardA;
	
	public AddSmallBusinessesParams() {
		super();
		super.merchantBusinessType = "3";
		super.accountType = "2";
		super.settlementType = "1";
		super.setType(new Byte("1"));
	}
	
	
	/**
	 *  1：营业执照（营业执照(三证合一)不能为空）；
		2：其他资质；
		3：门头照片；
		4：本人手持身份证照片；
		5：商家相册;
		6：logo
		7：法人身份证正面照片
		8：法人身份证反面照片
		9：入账银行卡正面照片
		10：门脸(内设)照片(前台)
		11：入账非法人证明照片 
		12：对私账户身份证正面照片 
		13：对私账户身份证反面照片
	 */
	/*public ShopVO createShopVO() {
		ShopVO shopVO = CopyBeanUtil.copyBean(this ,ShopVO.class);
		shopVO.setCorporation(getArtifNm());
		shopVO.setName(getMerchantName());
		shopVO.setProCode(getMerchantProvinceCode());
		shopVO.setCityCode(getMerchantCityCode());
		shopVO.setCountyCode(getMerchantCountyCode());
		shopVO.setTownCode(getMerchantTownCode());
		shopVO.setAddress(getMerchantAddress());
		shopVO.setPhone(getMerchantPhone());
		shopVO.setBankAccount(getAccountNo());
		shopVO.setCardHolder(getAccountName());
		shopVO.setShopPictureVOList(CopyBeanUtil.copyList(getShopPictureParamsList(), ShopPictureVO.class));
		shopVO.setSaoBeiShopInfoVO(CopyBeanUtil.copyBean(this, SaoBeiShopInfoVO.class));
		for (ShopPictureVO pic : shopVO.getShopPictureVOList()) {
			switch (pic.getImageType()) {
				case 4:
					shopVO.getSaoBeiShopInfoVO().setImgIdcardHolding(pic.getImageUrl());
					break;
				case 6:
					shopVO.getSaoBeiShopInfoVO().setImgLogo(pic.getImageUrl());
					break;
				case 9:
					shopVO.getSaoBeiShopInfoVO().setImgBankcardA(pic.getImageUrl());
					break;
				case 10:
					shopVO.getSaoBeiShopInfoVO().setImgIndoor(pic.getImageUrl());
					break;
				case 12:
					shopVO.getSaoBeiShopInfoVO().setImgPrivateIdcardA(pic.getImageUrl());
					break;
				case 13:
					shopVO.getSaoBeiShopInfoVO().setImgPrivateIdcardB(pic.getImageUrl());
					break;
				default:
					break;
			}
		}
		return shopVO;
	}*/
}
