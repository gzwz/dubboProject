package cn.qumiandan.pay.saobei.model.request.merchant.create;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.Getter;

/**
 * 小微商户对私结算
 * 小微商户创建商户信息
 * @author yuleidian
 * @version 创建时间：2018年12月6日 下午2:08:02
 */
@Getter
public class SmallMerchantReq extends CreateBaseMerchantReq {

	private static final long serialVersionUID = 1L;

	/** 对私账户身份证正面照片*/
	@Expose
	@SerializedName("img_private_idcard_a")
	private String imgPrivateIdcardA;
	
	/** 对私账户身份证反面照片*/
	@Expose
	@SerializedName("img_private_idcard_b")
	private String imgPrivateIdcardB;
	
	/** 本人手持身份证照片*/
	@Expose
	@SerializedName("img_idcard_holding")
	private String imgIdcardHolding;

	/** 商户门头照片*/
	@Expose
	@SerializedName("img_logo")
	private String imgLogo;

	/** 内部前台照片*/
	@Expose
	@SerializedName("img_indoor")
	private String imgIndoor;

	/** 结算人身份证号码*/
	@Expose
	@SerializedName("merchant_id_no")
	private String merchantIdNo;

	/** 入账银行卡正面照片*/
	@Expose
	@SerializedName("img_bankcard_a")
	private String imgBankcardA;
	
	public SmallMerchantReq() {
		super();
		super.merchantBusinessType = "3";
		super.accountType = "2";
		super.settlementType = "1";
	}
	
	
	public static SmallMerchantReq create(SaoBeiShopAllInfoVO vo) {
		SmallMerchantReq result = CopyBeanUtil.copyBean(vo, SmallMerchantReq.class);
		return result;
	}
	
	public SmallMerchantReq setImgPrivateIdcardA(String imgPrivateIdcardA) {
		this.imgPrivateIdcardA = imgPrivateIdcardA;
		return this;
	}

	public SmallMerchantReq setImgPrivateIdcardB(String imgPrivateIdcardB) {
		this.imgPrivateIdcardB = imgPrivateIdcardB;
		return this;
	}

	public SmallMerchantReq setImgIdcardHolding(String imgIdcardHolding) {
		this.imgIdcardHolding = imgIdcardHolding;
		return this;
	}

	public SmallMerchantReq setImgLogo(String imgLogo) {
		this.imgLogo = imgLogo;
		return this;
	}

	public SmallMerchantReq setMerchantIdNo(String merchantIdNo) {
		this.merchantIdNo = merchantIdNo;
		return this;
	}

	public SmallMerchantReq setImgBankcardA(String imgBankcardA) {
		this.imgBankcardA = imgBankcardA;
		return this;
	}

	public SmallMerchantReq setImgIndoor(String imgIndoor) {
		this.imgIndoor = imgIndoor;
		return this;
	}
}
