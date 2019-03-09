package cn.qumiandan.pay.saobei.model.request.merchant.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.Getter;

/**
 * 个体户对私非法人结算
 * @author yuleidian
 * @version 创建时间：2018年12月6日 下午2:07:29
 */
@Getter
public class PrivatelyToNonArtifMerchantReq extends CreateBaseMerchantReq {


	private static final long serialVersionUID = 1L;

	
	/** 营业执照注册号或者社会信用代码*/
	@Expose
	@SerializedName("license_no")
	private String licenseNo;
	
	/** 法人姓名*/
	@Expose
	@SerializedName("artif_nm")
	private String artifNm;
	
	/** 法人身份证号码*/
	@Expose
	@SerializedName("legalIdnum")
	private String legalIdnum;
	
	/** 结算人证件号码*/
	@Expose
	@SerializedName("merchant_id_no")
	private String merchantIdNo;
	
	/** 营业执照(三证合一)*/
	@Expose
	@SerializedName("img_license")
	private String imgLicense;
	
	/** 法人身份证正面照片*/
	@Expose
	@SerializedName("img_idcard_a")
	private String imgidcardA;
	
	/** 法人身份证反面照片*/
	@Expose
	@SerializedName("img_idcard_b")
	private String imgIdcardB;
	
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
	
	/** 入账非法人证明照片*/
	@Expose
	@SerializedName("img_unincorporated")
	private String imgUnincorporated;
	
	/** 对私账户身份证正面照片*/
	@Expose
	@SerializedName("img_private_idcard_a")
	private String imgPrivateIdcardA;
	
	/** 对私账户身份证反面照片*/
	@Expose
	@SerializedName("img_private_idcard_b")
	private String imgPrivateIdcardB;

	
	public PrivatelyToNonArtifMerchantReq() {
		super();
		super.merchantBusinessType = "2";
		super.accountType = "2";
		super.settlementType = "1";
	}
	
	public static PrivatelyToNonArtifMerchantReq create(SaoBeiShopAllInfoVO vo) {
		PrivatelyToNonArtifMerchantReq result =
		 CopyBeanUtil.copyBean(vo, PrivatelyToNonArtifMerchantReq.class);
		return result;
	}
	public PrivatelyToNonArtifMerchantReq setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setArtifNm(String artifNm) {
		this.artifNm = artifNm;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setLegalIdnum(String legalIdnum) {
		this.legalIdnum = legalIdnum;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setMerchantIdNo(String merchantIdNo) {
		this.merchantIdNo = merchantIdNo;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgidcardA(String imgidcardA) {
		this.imgidcardA = imgidcardA;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgIdcardB(String imgIdcardB) {
		this.imgIdcardB = imgIdcardB;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgBankcardA(String imgBankcardA) {
		this.imgBankcardA = imgBankcardA;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgLogo(String imgLogo) {
		this.imgLogo = imgLogo;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgIndoor(String imgIndoor) {
		this.imgIndoor = imgIndoor;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgUnincorporated(String imgUnincorporated) {
		this.imgUnincorporated = imgUnincorporated;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgPrivateIdcardA(String imgPrivateIdcardA) {
		this.imgPrivateIdcardA = imgPrivateIdcardA;
		return this;
	}

	public PrivatelyToNonArtifMerchantReq setImgPrivateIdcardB(String imgPrivateIdcardB) {
		this.imgPrivateIdcardB = imgPrivateIdcardB;
		return this;
	}
	
	
	
}
