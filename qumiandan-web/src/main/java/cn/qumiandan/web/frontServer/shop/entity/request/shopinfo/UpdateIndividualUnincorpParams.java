package cn.qumiandan.web.frontServer.shop.entity.request.shopinfo;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.util.CollectionUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;
import cn.qumiandan.shop.vo.ShopPictureVO;
import cn.qumiandan.shop.vo.ShopUpdateVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.web.frontServer.shop.entity.request.AddMerchantParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Deprecated
@Getter
@Setter
@ToString
public class UpdateIndividualUnincorpParams extends AddMerchantParams implements Serializable{

	private static final long serialVersionUID = 1L;

	//==========个体户对私非法人结算====================
	/** 营业执照注册号或者社会信用代码*/
	@Expose
	@SerializedName("license_no")
	@NotBlank(message = "营业执照注册号或者社会信用代码不能为空")
	private String licenseNo;
	
//	/** 法人姓名*/
//	@Expose
//	@SerializedName("artif_nm")
//	private String artifNm;
//	
//	/** 法人姓名*/
//	@Expose
//	@SerializedName("legalIdnum")
//	private String legalIdnum;
	
	/** 结算人证件号码*/
	@Expose
	@SerializedName("merchant_id_no")
	@NotBlank(message = "结算人证件号码不能为空")
	private String merchantIdNo;
	
	/** 营业执照(三证合一)*/
//	@NotBlank(message = "营业执照(三证合一)不能为空")
	private String imgLicense;
	
	/** 法人身份证正面照片*/
	private String imgidcardA;
	
	/** 法人身份证反面照片*/
	private String imgIdcardB;
	
	/** 入账银行卡正面照片*/
	private String imgBankcardA;
	
	/** 门头照片*/
	private String imgLogo;
	
	/** 门脸(内设)照片(前台)*/
	private String imgIndoor;
	
	/** 入账非法人证明照片*/
	private String imgUnincorporated;
	
	/** 对私账户身份证正面照片*/
	private String imgPrivateIdcardA;
	
	/** 对私账户身份证反面照片*/
	private String imgPrivateIdcardB;

	public UpdateIndividualUnincorpParams() {
		super();
		super.merchantBusinessType = "2";
		super.accountType = "2";
		super.settlementType = "1";
		super.setType(new Byte("2"));
	}
	
	/**
	 *  1：营业执照（营业执照(三证合一)不能为空）；
		2：其他资质；
		3：门头照片；
		4：法人手持身份证照片；
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
	public ShopUpdateVO createShopVO() {
		ShopUpdateVO shopVO = CopyBeanUtil.copyBean(this ,ShopUpdateVO.class);
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
		if (!CollectionUtils.isEmpty(shopVO.getShopPictureVOList())) {
			for (ShopPictureVO pic : shopVO.getShopPictureVOList()) {
				if (Objects.nonNull(pic)) {
					switch (pic.getImageType()) {
					case 1:
						shopVO.getSaoBeiShopInfoVO().setImgLicense(pic.getImageUrl());
						break;
					case 6:
						shopVO.getSaoBeiShopInfoVO().setImgLogo(pic.getImageUrl());
						break;
					case 7:
						shopVO.getSaoBeiShopInfoVO().setImgIdcardA(pic.getImageUrl());
						break;
					case 8:
						shopVO.getSaoBeiShopInfoVO().setImgIdcardB(pic.getImageUrl());
						break;
					case 9:
						shopVO.getSaoBeiShopInfoVO().setImgBankcardA(pic.getImageUrl());
						break;
					case 10:
						shopVO.getSaoBeiShopInfoVO().setImgIndoor(pic.getImageUrl());
						break;
					case 11:
						shopVO.getSaoBeiShopInfoVO().setImgUnincorporated(pic.getImageUrl());
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
			}
		}
		return shopVO;
	}
}
