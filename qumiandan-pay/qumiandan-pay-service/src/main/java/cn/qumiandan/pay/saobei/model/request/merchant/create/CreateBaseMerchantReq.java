package cn.qumiandan.pay.saobei.model.request.merchant.create;

import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.RequestDefinition;
import cn.qumiandan.pay.saobei.model.request.merchant.BaseMerchantReqDefinition;
import cn.qumiandan.pay.saobei.vo.SaoBeiShopAllInfoVO;
import cn.qumiandan.utils.AssertUtil;
import lombok.Getter;
/**
 *  inst_no	String	15	Y	机构编号，扫呗分配
	trace_no	String	32	Y	请求流水号，不带“-”的uuid
	merchant_name	String	50	Y	商户名称，扫呗系统全局唯一不可重复，最多50个汉字且不能包含特殊符号参考验重接口
	merchant_alias	String	15	Y	商户简称，最多15个汉字且不能包含特殊符号
	merchant_company	String	30	Y	商户注册名称/公司全称，须与营业执照名称保持一致，最多30个汉字且不能包含特殊符号
	merchant_province	String	10	Y	所在省
	merchant_province_code	String	3	Y	省编码
	merchant_city	String	10	Y	所在市
	merchant_city_code	String	4	Y	市编码
	merchant_county	String	16	Y	所在区县
	merchant_county_code	String	4	Y	所在区县编码
	merchant_address	String	200	Y	商户详细地址
	merchant_person	String	10	Y	商户联系人姓名
	merchant_phone	String	13	Y	商户联系人电话（唯一）
	merchant_email	String	50	Y	商户联系人邮箱（唯一）
	merchant_service_phone	String	13	N	客服电话
	daily_timely_status	Int	10	N	D1状态,0不开通，1开通
	daily_timely_code	String	20	N	D1手续费代码
	business_name	String	50	Y	行业类目名称
	business_code	String	4	Y	行业类目编码，由扫呗技术支持提供表格
	merchant_business_type	Int	2	Y	商户类型:1企业，2个体工商户，3小微商户
	account_type	String	2	Y	账户类型，1对公，2对私
	settlement_type	String	2	Y	结算类型:1.法人结算 2.非法人结算
	license_type	Int	2	Y	营业证件类型：0营业执照，1三证合一，2手持身份证
	license_no	String	20	N	营业证件号码
	license_expire	String	10	N	营业证件到期日（格式YYYY-MM-DD）
	artif_nm	String	10	N	法人名称
	legalIdnum	String	30	N	法人身份证号
	legalIdnumExpire	String	10	N	法人身份证有效期（格式YYYY-MM-DD）
	merchant_id_no	String	30	N	结算人身份证号码
	merchant_id_expire	String	8	N	结算人身份证有效期，格式YYYYMMDD，长期填写29991231
	account_name	String	100	Y	入账银行卡开户名（结算人姓名/公司名）
	account_no	String	25	Y	入账银行卡卡号
	account_phone	String	30	N	入账银行预留手机号
	bank_name	String	50	Y	入账银行卡开户支行
	bank_no	String	25	Y	开户支行联行号，由扫呗技术支持提供表格
	no_credit	Int	2	N	限制信用卡使用,0不限制，1限制
	settle_type	String	2	Y	清算类型：1自动结算；2手动结算，
	settle_amount	Int	11	Y	自动清算金额（单位分），清算类型为自动清算时有效，指帐户余额达到此值才清算。注：当前固定值为1分
	rate_code	String	10	N	支付费率代码，默认千分之六，取值范围见下表
	greenstatus	Int	2	N	是否绿洲商户：0非绿洲，1开通绿洲
	blueseastatus	Int	2	N	是否蓝海商户：0非蓝海 1开通蓝海
	img_license	String	255	N	营业执照照片
	img_idcard_a	String	255	N	法人身份证正面照片
	img_idcard_b	String	255	N	法人身份证反面照片
	img_bankcard_a	String	255	N	入账银行卡正面照片
	img_bankcard_b	String	255	N	入账银行卡反面照片
	img_logo	String	255	N	商户门头照片
	img_indoor	String	255	N	内部前台照片
	img_contract	String	255	N	店内环境照片
	img_other	String	255	N	其他证明材料
	img_idcard_holding	String	255	N	本人手持身份证照片
	img_open_permits	String	255	N	开户许可证照片
	img_org_code	String	255	N	组织机构代码证照片
	img_tax_reg	String	255	N	税务登记证照片
	img_unincorporated	String	255	N	入账非法人证明照片
	img_private_idcard_a	String	255	N	对私账户身份证正面照片
	img_private_idcard_b	String	255	N	对私账户身份证反面照片
	img_standard_protocol	String	255	N	商户总分店关系证明
	img_val_add_protocol	String	255	N	商户增值协议照片
	img_sub_account_promiss	String	255	N	分账承诺函
	img_cashier	String	255	N	微信支付物料照片
	img_3rd_part	String	255	N	第三方平台截图
	img_alicashier	String	255	N	支付宝支付物料照片
	img_salesman_logo	String	255	N	业务员门头合照
	img_salesman_poster	String	255	N	业务员门店海报合照
	img_green_annex	String	255	N	绿洲活动补充材料
	img_green_food_hygiene_permit	String	255	N	绿洲食品卫生许可证
	img_green_promiss	String	255	N	绿洲承诺函
	notify_url	String	255	N	审核状态通知地址
	wx_appid	String	18	N	微信公众号appid，普通商户公众号支付使用
	wx_appsecret	String	64	N	微信公众号appsecret，普通商户公众号支付使用
	key_sign	String	32	Y	签名检验串,拼装所有非空参数+令牌，32位md5加密转换
	
	
 * 创建商户信息请求  (此请求只传入扫呗要求的必传值)
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午2:55:06
 */
@Getter
public class CreateBaseMerchantReq extends BaseMerchantReqDefinition {

	private static final long serialVersionUID = 1L;
	
	/** 商户号*/
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;
	
	/** 商户名称，扫呗系统全局唯一不可重复，最多50个汉字且不能包含特殊符号*/
	@Expose
	@SerializedName("merchant_name")
	private String merchantName;
	
	/** 商户简称，最多15个汉字且不能包含特殊符号*/
	@Expose
	@SerializedName("merchant_alias")
	private String merchantAlias;
	
	/** 商户注册名称/公司全称，须与营业执照名称保持一致，最多30个汉字且不能包含特殊符号*/
	@Expose
	@SerializedName("merchant_company")
	private String merchantCompany;
	
	/** 所在省*/
	@Expose
	@SerializedName("merchant_province")
	private String merchantProvince;
	
	/** 省编码*/
	@Expose
	@SerializedName("merchant_province_code")
	private String merchantProvinceCode;
	
	/** 所在市*/
	@Expose
	@SerializedName("merchant_city")
	private String merchantCity;
	/** 市编码*/
	@Expose
	@SerializedName("merchant_city_code")
	private String merchantCityCode;
	
	/** 所在区县*/
	@Expose
	@SerializedName("merchant_county")
	private String merchantCounty;
	
	/** 所在区县编码*/
	@Expose
	@SerializedName("merchant_county_code")
	private String merchantCountyCode;
	
	/** 商户详细地址*/
	@Expose
	@SerializedName("merchant_address")
	private String merchantAddress;
	
	/** 商户联系人姓名*/
	@Expose
	@SerializedName("merchant_person")
	private String merchantPerson;
	
	/** 商户联系人电话（唯一）*/
	@Expose
	@SerializedName("merchant_phone")
	private String merchantPhone;
	
	/** 商户联系人邮箱（唯一）*/
	@Expose
	@SerializedName("merchant_email")
	private String merchantEmail;
	
	/** 行业类目名称*/
	@Expose
	@SerializedName("business_name")
	private String businessName;
	
	/** 行业类目编码，由扫呗技术支持提供表格*/
	@Expose
	@SerializedName("business_code")
	private String businessCode;
	
	/** 商户类型: 1 企业，2 个体工商户，3 小微商户*/
	@Expose
	@SerializedName("merchant_business_type")
	protected String merchantBusinessType;
	
	/** 账户类型，1对公，2对私*/
	@Expose
	@SerializedName("account_type")
	protected String accountType;
	
	/** 结算类型:1.法人结算 2.非法人结算*/
	@Expose
	@SerializedName("settlement_type")
	protected String settlementType;
	
	/** 营业证件类型：0营业执照，1三证合一，2手持身份证*/
	@Expose
	@SerializedName("license_type")
	private Byte licenseType;
	
	/** 入账银行卡开户名（结算人姓名/公司名）*/
	@Expose
	@SerializedName("account_name")
	private String accountName;
	
	/** 入账银行卡卡号*/
	@Expose
	@SerializedName("account_no")
	private String accountNo;
	
	/** 入账银行卡开户支行*/
	@Expose
	@SerializedName("bank_name")
	private String bankName;
	
	/** 开户支行联行号，由扫呗技术支持提供表格*/
	@Expose
	@SerializedName("bank_no")
	private String bankNo;
	
	/** 清算类型：1自动结算；2手动结算，*/
	@Expose
	@SerializedName("settle_type")
	private String settleType;
	
	/** 自动清算金额（单位分），清算类型为自动清算时有效，指帐户余额达到此值才清算。注：当前固定值为1分*/
	@Expose
	@SerializedName("settle_amount")
	private Integer settleAmount;
	
	/** 结算费率 */
	@Expose
	@SerializedName("rate_code")
	private String rateCode;
	
	/** 审核结果回调地址*/
	@Expose
	@SerializedName("notify_url")
	private String notifyUrl;
	
	
	public static RequestDefinition getCreateMerchantRequest(SaoBeiShopAllInfoVO vo) {
		AssertUtil.isNull(vo, "SaoBeiMerchantServiceImpl|getCreateMerchantRequest|传入参数vo为空");
		switch (vo.getType()) {
		case 1:
			return SmallMerchantReq.create(vo);
		case 2:
			return PrivatelyToNonArtifMerchantReq.create(vo);
		case 3:
			return PrivatelyToArtifMerchantReq.create(vo);
		}
		throw new QmdException(PayErrorCode.PAY1002.getCode(), "暂不支持这种类型的商户注册");
	}
	
	public BaseMerchantReqDefinition setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
		return this;
	}
	
	public CreateBaseMerchantReq setMerchantName(String merchantName) {
		this.merchantName = merchantName;
		return this;
	}

	public CreateBaseMerchantReq setMerchantAlias(String merchantAlias) {
		this.merchantAlias = merchantAlias;
		return this;
	}

	public CreateBaseMerchantReq setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
		return this;
	}

	public CreateBaseMerchantReq setMerchantProvince(String merchantProvince) {
		this.merchantProvince = merchantProvince;
		return this;
	}

	public CreateBaseMerchantReq setMerchantProvinceCode(String merchantProvinceCode) {
		this.merchantProvinceCode = merchantProvinceCode;
		return this;
	}

	public CreateBaseMerchantReq setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
		return this;
	}

	public CreateBaseMerchantReq setMerchantCityCode(String merchantCityCode) {
		this.merchantCityCode = merchantCityCode;
		return this;
	}

	public CreateBaseMerchantReq setMerchantCounty(String merchantCounty) {
		this.merchantCounty = merchantCounty;
		return this;
	}

	public CreateBaseMerchantReq setMerchantCountyCode(String merchantCountyCode) {
		this.merchantCountyCode = merchantCountyCode;
		return this;
	}

	public CreateBaseMerchantReq setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
		return this;
	}

	public CreateBaseMerchantReq setMerchantPerson(String merchantPerson) {
		this.merchantPerson = merchantPerson;
		return this;
	}

	public CreateBaseMerchantReq setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
		return this;
	}

	public CreateBaseMerchantReq setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
		return this;
	}

	public CreateBaseMerchantReq setBusinessName(String businessName) {
		this.businessName = businessName;
		return this;
	}

	public CreateBaseMerchantReq setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
		return this;
	}

/*	public CreateBaseMerchantReqVO setMerchantBusinessType(String merchantBusinessType) {
		this.merchantBusinessType = merchantBusinessType;
		return this;
	}*/

	/*public CreateBaseMerchantReqVO setAccountType(String accountType) {
		this.accountType = accountType;
		return this;
	}*/

	/*public CreateBaseMerchantReqVO setSettlementType(String settlementType) {
		this.settlementType = settlementType;
		return this;
	}*/

	public CreateBaseMerchantReq setLicenseType(Byte licenseType) {
		this.licenseType = licenseType;
		return this;
	}

	public CreateBaseMerchantReq setAccountName(String accountName) {
		this.accountName = accountName;
		return this;
	}

	public CreateBaseMerchantReq setAccountNo(String accountNo) {
		this.accountNo = accountNo;
		return this;
	}

	public CreateBaseMerchantReq setBankName(String bankName) {
		this.bankName = bankName;
		return this;
	}

	public CreateBaseMerchantReq setBankNo(String bankNo) {
		this.bankNo = bankNo;
		return this;
	}

	public CreateBaseMerchantReq setSettleType(String settleType) {
		this.settleType = settleType;
		return this;
	}

	public CreateBaseMerchantReq setSettleAmount(Integer settleAmount) {
		this.settleAmount = settleAmount;
		return this;
	}

	public CreateBaseMerchantReq setRateCode(String rateCode) {
		this.rateCode = rateCode;
		return this;
	}

	@Override
	public void initInherentParameter(Object... params) {
		if (Objects.nonNull(params) && params.length > 0) {
			super.instNo = params[0].toString();
			super.key = params[1].toString();
			this.notifyUrl = params[2].toString();
		}
	}
}
