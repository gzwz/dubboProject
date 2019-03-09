package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
@Deprecated
@Data
public class AddMerchantParams implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	// ==========================扫呗=====================
	/** 商户名称，扫呗系统全局唯一不可重复，最多50个汉字且不能包含特殊符号 */
	@Expose
	@SerializedName("merchant_name")
	@NotBlank(message = "店铺名称不能为空")
	private String merchantName;

	/** 法人姓名 */
	@Expose
	@SerializedName("artif_nm")
	@NotBlank(message = "法人姓名不能为空")
	private String artifNm;

	/** 法人身份证号码 */
	@Expose
	@SerializedName("legalIdnum")
	@NotBlank(message = "法人身份证号码不能为空")
	private String legalIdnum;

	/** 商户简称，最多15个汉字且不能包含特殊符号 */
	private String merchantAlias;

	/** 商户注册名称/公司全称，须与营业执照名称保持一致，最多30个汉字且不能包含特殊符号 */
	@Expose
	@SerializedName("merchant_company")
	@NotBlank(message = "商户注册名称/公司全称不能为空")
	private String merchantCompany;

	/** 所在省 */
	private String merchantProvince;

	/** 省编码 */
	@Expose
	@SerializedName("merchant_province_code")
	@NotBlank(message = "省编码不能为空")
	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
	private String merchantProvinceCode;

	/** 所在市 */
	private String merchantCity;

	/** 市编码 */
	@Expose
	@SerializedName("merchant_city_code")
	@Pattern(regexp="^\\d{6}$",message="市编码格式不正确")
	@NotBlank(message = "市编码不能为空")
	private String merchantCityCode;

	/** 所在区县 */
	private String merchantCounty;

	/** 所在区县编码 */
	@Expose
	@SerializedName("merchant_county_code")
	@Pattern(regexp="^\\d{6}$",message="区县编码格式不正确")
	@NotBlank(message = "所在区县编码不能为空")
	private String merchantCountyCode;

	/** 商户详细地址 */
	@Expose
	@SerializedName("merchant_address")
	@NotBlank(message = "店铺详细地址不能为空")
	private String merchantAddress;

	/** 商户联系人姓名 */
	@Expose
	@SerializedName("merchant_person")
	@NotBlank(message = "店铺联系人姓名不能为空")
	private String merchantPerson;

	/** 商户联系人电话（唯一） */
	@Expose
	@SerializedName("merchant_phone")
	@Pattern(regexp="^1[34578]\\d{9}$",message="店铺联系人电话格式不正确")
	@NotBlank(message = "店铺联系人电话不能为空")
	private String merchantPhone;

	/** 商户联系人邮箱（唯一） */
	private String merchantEmail;

	// /** 行业类目名称*/
	// @Expose
	// @SerializedName("business_name")
	// private String businessName;

	// /** 行业类目编码，由扫呗技术支持提供表格*/
	// @Expose
	// @SerializedName("business_code")
	// @NotBlank(message = "行业类目编码不能为空")
	// private String businessCode;

	/** 商户类型: 1 企业，2 个体工商户，3 小微商户 */
	@Expose
	@SerializedName("merchant_business_type")
//	@NotBlank(message = "商户类型不能为空")
	protected String merchantBusinessType;

	/** 账户类型，1对公，2对私 */
	@Expose
	@SerializedName("account_type")
//	@NotBlank(message = "账户类型不能为空")
	protected String accountType;

	/** 结算类型:1.法人结算 2.非法人结算 */
	@Expose
	@SerializedName("settlement_type")
//	@NotBlank(message = "结算类型不能为空")
	protected String settlementType;

	/** 营业证件类型：0营业执照，1三证合一，2手持身份证 */
	@Expose
	@SerializedName("license_type")
	@NotNull(message = "营业证件类型不能为空")
	private Byte licenseType;

	/** 入账银行卡开户名（结算人姓名/公司名） */
	@Expose
	@SerializedName("account_name")
	@NotBlank(message = "入账银行卡开户名（结算人姓名/公司名）不能为空")
	private String accountName;

	/** 入账银行卡卡号 */
	@Expose
	@SerializedName("account_no")
	@NotBlank(message = "入账银行卡卡号不能为空")
	private String accountNo;

//	/** 入账银行卡开户支行 */
//	@Expose
//	@SerializedName("bank_name")
//	@NotBlank(message = "入账银行卡开户支行不能为空")
//	private String bankName;
//
//	/** 开户支行联行号，由扫呗技术支持提供表格 */
//	@Expose
//	@SerializedName("bank_no")
//	@NotBlank(message = "开户支行联行号不能为空")
//	private String bankNo;

	/** 清算类型：1自动结算；2手动结算， */
	@Expose
	@SerializedName("settle_type")
	private String settleType;

	/** 自动清算金额（单位分），清算类型为自动清算时有效，指帐户余额达到此值才清算。注：当前固定值为1分 */
	@Expose
	@SerializedName("settle_amount")
	private Integer settleAmount;

//	/** 结算费率 */
//	@Expose
//	@SerializedName("rate_code")
//	@NotBlank(message = "结算费率不能为空")
//	private String rateCode;
	
//	@NotBlank(message = "店铺营业执照编号不能为空")
//	private String licenseNo; // 营业执照编号

	// /** 审核结果回调地址*/
	// @Expose
	// @SerializedName("notify_url")
	// private String notifyUrl;

	// ==================趣免单=====================
	// =====================店铺基础信息=======================

	private String description; // 店铺简介

	@NotNull(message = "店铺类型不能为空")
	private Long shopTypeId; // 店铺类型

	// @NotBlank(message = "店铺logo-url不能为空")
	// private String logo; //店铺logo-url

	@NotBlank(message = "店铺经度不能为空")
	private String longitude; // 经度

	@NotBlank(message = "店铺纬度不能为空")
	private String latitude; // 纬度

	@NotBlank(message = "乡镇编号不能为空")
	@Pattern(regexp="^\\d{9}$",message="乡镇编号格式不正确")
	private String merchantTownCode; // 乡镇编号

	// =====================店铺扩展信息=======================
	// private Long shopId; //店铺编号

	// @NotNull(message = "店铺最大扫码付款金额不能为空")
	private BigDecimal maxScan; // 最大扫码付款金额

	// @NotNull(message = "店铺最大商品金额不能为空")
	private BigDecimal maxSale; // 最大商品金额

	// @NotNull(message = "店铺最大免单金额不能为空")
	private BigDecimal maxFree; // 最大免单金额
	
	private Long bankId;

	// @NotNull(message = "店铺起送金额不能为空")
	private BigDecimal dispatchBeginMoney; // 起送金额

	@NotNull(message = "店铺营业起始时间不能为空")
	//@Pattern(regexp="([0-1][0-9]|2[0-3]):([0-5][0-9])",message="开始营业时间格式不正确")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date openTime; // 店铺营业起始时间

	
	@NotNull(message = "店铺营业结束时间不能为空")
	//@Pattern(regexp="([0-1][0-9]|2[0-3]):([0-5][0-9])",message="营业结束时间格式不正确")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date restTime; // 店铺营业结束时间

	@NotBlank(message = "店铺特色信息不能为空")
	private String item; // 特色信息

	private Byte isOpenShut; // 是否开启打烊(1:是；0：否)

	private BigDecimal totalIncome; // 总营业额

//	@NotBlank(message = "支付方式不能为空")
	private String supportPayment; // 支付方式

//	@NotNull(message = "店铺类型不能为空")
	@SerializedName("type")
	private Byte type; // 店铺类型

	// =====================店铺行业信息=======================
	@NotNull(message = "行业id不能为空")
	private Long industryId; // 行业id

	// =====================店铺图片信息=======================
	//@NotEmpty(message = "图片信息不能为空")
	private List<ShopPictureParams> shopPictureParamsList;

	// =====================店铺人员信息=======================
	 @NotBlank(message = "手机号不能为空")
	 @Pattern(regexp="^1[34578]\\d{9}$",message="手机号格式不正确")
	 private String mobile; //电话

	// =====================店铺人员信息=======================
	private Long createId; // 创建人
	
}
