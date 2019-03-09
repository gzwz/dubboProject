package cn.qumiandan.pay.saobei.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 创建基础类型
 * @author yuleidian
 * @version 创建时间：2018年12月10日 下午5:03:41
 */
@Data
public class SaoBeiShopAllInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商户号*/
	private String merchantNo;
	
	/** 编号*/
	private Long id;

	/** 店铺id*/
	private Long shopId;

	/**
	 * 类型（1:小微商户对私结算；2：个体户对私非法人结算；3：个体户对私法人结算）
	 */
	private Byte type;
	
	/**
	 * 商户名称
	 */
	private String merchantName;

	/**
	 * 商户简称
	 */
	private String merchantAlias;

	/**
	 * 商户注册名称/公司全称，须与营业执照名称保持一致，最多30个汉字且不能包含特殊符号
	 */
	private String merchantCompany;

	/**
	 * 所在省
	 */
	private String merchantProvince;

	/**
	 * 省编码
	 */
	private String merchantProvinceCode;

	/**
	 * 所在市
	 */
	private String merchantCity;

	/**
	 * 市编码
	 */
	private String merchantCityCode;

	/**
	 * 所在区县
	 */
	private String merchantCounty;

	/**
	 * 所在区县编码
	 */
	private String merchantCountyCode;

	/**
	 * 商户详细地址
	 */
	private String merchantAddress;

	/**
	 * 商户联系人姓名
	 */
	private String merchantPerson;

	/**
	 * 商户联系人电话（唯一）
	 */
	private String merchantPhone;

	/**
	 * 商户联系人邮箱（唯一）
	 */
	private String merchantEmail;

	/**
	 * 行业类目名称
	 */
	private String businessName;

	/**
	 * 行业类目编码，由扫呗技术支持提供表格
	 */
	private String businessCode;

	/**
	 * 商户类型:1企业，2个体工商户，3小微商户
	 */
	private Byte merchantBusinessType;

	/**
	 * 账户类型，1对公，2对私
	 */
	private Byte accountType;

	/**
	 * 结算类型:1.法人结算 2.非法人结算
	 */
	private Byte settlementType;

	/**
	 * 营业证件类型
	 */
	private Byte licenseType;

	/**
	 * 入账银行卡开户名（结算人姓名/公司名）
	 */
	private String accountName;

	/**
	 * 入账银行卡卡号
	 */
	private String accountNo;

	/**
	 * 入账银行卡开户支行
	 */
	private String bankName;

	/**
	 * 开户支行联行号，由扫呗技术支持提供表格
	 */
	private String bankNo;

	/**
	 * 清算类型：1自动结算；2手动结算
	 */
	private Byte settleType;

	/**
	 * 自动清算金额（单位分），清算类型为自动清算时有效，指帐户余额达到此值才清算。注：当前固定值为1分
	 */
	private Integer settleAmount;

	/**
	 * 结算费率码
	 */
	private String rateCode;

	/**
	 * 营业执照注册号或者社会信用代码
	 */
	private String licenseNo;

	/**
	 * 结算人证件号码
	 */
	private String merchantIdNo;

	/**
	 * 营业执照(三证合一)
	 */
	private String imgLicense;

	/**
	 * 入账银行卡正面照片
	 */
	private String imgBankcardA;

	/**
	 * 门头照片
	 */
	private String imgLogo;

	/**
	 * 门脸(内设)照片(前台)
	 */
	private String imgIndoor;

	/**
	 * 对私账户身份证正面照片
	 */
	private String imgPrivateIdcardA;

	/**
	 * 对私账户身份证反面照片
	 */
	private String imgPrivateIdcardB;

	/**
	 * 手持身份证
	 */
	private String imgIdcardHolding;

	/**
	 * 创建时间
	 */
	private Date createDate;
	
}
