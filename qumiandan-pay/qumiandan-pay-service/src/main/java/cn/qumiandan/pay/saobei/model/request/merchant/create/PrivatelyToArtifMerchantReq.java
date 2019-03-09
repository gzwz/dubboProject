package cn.qumiandan.pay.saobei.model.request.merchant.create;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.Getter;

/**
 * 个体户对私法人结算
 * @author yuleidian
 * @version 创建时间：2018年12月7日 下午4:01:39
 */
@Getter
public class PrivatelyToArtifMerchantReq extends CreateBaseMerchantReq {

	private static final long serialVersionUID = 1L;
	
	/** 营业执照注册号或者社会信用代码*/
	@Expose
	@SerializedName("license_no")
	private String licenseNo;
	
	/** 结算人证件号码*/
	@Expose
	@SerializedName("merchant_id_no")
	private String merchantIdNo;
	
	/** 营业执照(三证合一)*/
	@Expose
	@SerializedName("img_license")
	private String imgLicense;
	
	/** 入账银行卡正面照片*/
	@Expose
	@SerializedName("img_bankcard_a")
	private String imgBankcardA;
	
	/** 门头照片*/
	@Expose
	@SerializedName("img_logo")
	private String imgLogo;
	
	/** 门脸(内设)照片(前台)*/
	@Expose
	@SerializedName("img_indoor")
	private String imgIndoor;
	
	/** 对私账户身份证正面照片*/
	@Expose
	@SerializedName("img_private_idcard_a")
	private String imgPrivateIdcardA;
	
	/** 对私账户身份证反面照片*/
	@Expose
	@SerializedName("img_private_idcard_b")
	private String imgPrivateIdcardB;

	public PrivatelyToArtifMerchantReq() {
		super();
		super.merchantBusinessType = "2";
		super.accountType = "2";
		super.settlementType = "2";
	}
	
	public static PrivatelyToArtifMerchantReq create(SaoBeiShopAllInfoVO vo) {
		PrivatelyToArtifMerchantReq result = null;
		result = CopyBeanUtil.copyBean(vo, PrivatelyToArtifMerchantReq.class);
		return result;
	}
	
	public PrivatelyToArtifMerchantReq setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
		return this;
	}

	public PrivatelyToArtifMerchantReq setMerchantIdNo(String merchantIdNo) {
		this.merchantIdNo = merchantIdNo;
		return this;
	}

	public PrivatelyToArtifMerchantReq setImgLicense(String imgLicense) {
		this.imgLicense = imgLicense;
		return this;
	}

	public PrivatelyToArtifMerchantReq setImgBankcardA(String imgBankcardA) {
		this.imgBankcardA = imgBankcardA;
		return this;
	}

	public PrivatelyToArtifMerchantReq setImgLogo(String imgLogo) {
		this.imgLogo = imgLogo;
		return this;
	}

	public PrivatelyToArtifMerchantReq setImgIndoor(String imgIndoor) {
		this.imgIndoor = imgIndoor;
		return this;
	}

	public PrivatelyToArtifMerchantReq setImgPrivateIdcardA(String imgPrivateIdcardA) {
		this.imgPrivateIdcardA = imgPrivateIdcardA;
		return this;
	}

	public PrivatelyToArtifMerchantReq setImgPrivateIdcardB(String imgPrivateIdcardB) {
		this.imgPrivateIdcardB = imgPrivateIdcardB;
		return this;
	}
}
