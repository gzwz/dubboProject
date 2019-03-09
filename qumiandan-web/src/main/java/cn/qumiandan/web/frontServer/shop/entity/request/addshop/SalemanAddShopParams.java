package cn.qumiandan.web.frontServer.shop.entity.request.addshop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.qumiandan.shop.vo.ShopPictureVO;
import lombok.Data;
/**
 * 业务员端添加店铺
 * @author lrj
 *
 */
@Data
public class SalemanAddShopParams implements Serializable
{

	private static final long serialVersionUID = 1L;
	// =====================店铺基础信息=======================

	@NotBlank(message = "店铺名称不能为空")
	private String name; // 店铺名称

	private String description; // 店铺简介
	
	@NotNull(message = "店铺类型不能为空")
	private Long shopTypeId; // 店铺类型
	
	/**
	 * 营业证件类型：0营业执照，1三证合一
	 */
	@NotBlank(message = "营业证件类型不能为空")
	private String licenseType;
	
	@NotBlank(message = "营业执照编号不能为空")
	private String license; // 营业执照编号
	
	/**
	 * 商户注册名称/公司全称
	 */
	@NotBlank(message = "商户注册名称/公司全称不能为空")
	private String  merchantCompany;

	private String logo; // 店铺logo-url
	
	@NotBlank(message = "店铺经度不能为空")
	private String longitude; // 经度

	@NotBlank(message = "店铺纬度不能为空")
	private String latitude; // 纬度
	
	@NotBlank(message = "省编号不能为空")
	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
	private String proCode; // 省编号

	@NotBlank(message = "市编号不能为空")
	@Pattern(regexp="^\\d{6}$",message="市编号格式不正确")
	private String cityCode; // 市编号

	@NotBlank(message = "区/县编号不能为空")
	@Pattern(regexp="^\\d{6}$",message="区/县编号格式不正确")
	private String countyCode; // 区/县编号

	@NotBlank(message = "乡镇编号不能为空")
	@Pattern(regexp="^\\d{9}$",message="乡镇编号格式不正确")
	private String townCode; // 乡镇编号

	@NotBlank(message = "详细地址不能为空")
	private String address; // 详细地址
	
	/**
	 * 店铺联系人姓名
	 */
	@NotBlank(message = "店铺联系人姓名不能为空")
	private String merchantPerson;

	@NotBlank(message = "店铺联系电话不能为空")
	@Pattern(regexp="^1[34578]\\d{9}$",message="店铺联系人电话格式不正确")
	private String phone; // 店铺联系电话

	/**
	 * 店铺状态 1:创建待审核中； 2：创建审核未通过； 3：审核通过正常营业； 4：更新审核中； 5：更新审核未通过（审核通过为3）； 6：主动申请关闭；
	 * 7：平台强制关闭
	 */
	// private Byte status;

	// =====================店铺扩展信息=======================
	@NotBlank(message = "银行卡号不能为空")
	private String bankAccount; // 银行卡号
	
	@NotNull(message = "开户行id不能为空")
	private Long bankId; // 开户行

	@NotBlank(message = "持卡人不能为空")
	private String cardHolder; // 持卡人
	
	/**
	 * 法人身份证号
	 */
	@NotBlank(message = "法人身份证号不能为空")
	private String legalIdNum;

	@NotBlank(message = "法人不能为空")
	private String corporation; // 法人

	private BigDecimal maxScan; // 最大扫码付款金额

	private BigDecimal maxSale; // 最大商品金额

	private BigDecimal maxFree; // 最大免单金额

	private BigDecimal dispatchBeginMoney; // 起送金额

	@NotNull(message = "店铺营业起始时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date openTime; // 店铺营业起始时间

	@NotNull(message = "店铺营业结束时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date restTime; // 店铺营业结束时间
	
	@NotBlank(message = "特色信息不能为空")
	private String item; // 特色信息

	private Byte isOpenShut; // 是否开启打烊(1:是；0：否)

	private BigDecimal totalIncome; // 总营业额

	private Integer membersLimit; // 成员数量限制

	private String supportPayment; // 支付方式

	private String merchantNo;// 商户号

	private String terminalId; // 设备id

	private String accessToken;// 访问令牌

	private Long salesVolume;// 销量
	

	/**
	 * 人均消费（单位：分）
	 */
	private BigDecimal perCapitaConsume;

	private BigDecimal hotExponent;// 热门指数

	// =====================店铺行业信息=======================
	@NotNull(message = "行业id不能为空")
	private Long industryId; // 行业id

	// =====================店铺图片信息=======================
	@NotEmpty(message = "店铺图片不能为空")
	private List<ShopPictureVO> shopPictureVOList;
	// private String imageName; //图片名称

	// private String imageUrl; //图片url

	// private String skipUrl; //图片跳转链接

	// private String suffix; //图片后缀名

	// private Long imageSize; //图片大小

	// private String intro; //图片介绍

	// private Integer imageSort; //排列次序

	// private Byte imageType; //类型(1：营业执照；2：其他资质；3：门头照片：4：营业执照法人手持身份证照片；5：商家相册)

	// =====================店铺分类信息=======================
	// private Long categoryId; //店铺分类id

	// =====================店铺人员信息=======================
	@NotBlank(message = "总店管理员手机号不能为空")
	private String mobile; // 总店管理员手机号

	// =====================店铺人员信息=======================
	private Long createId; // 创建人

//	private SaoBeiShopInfoVO saoBeiShopInfoVO;

	/** 使用卷的id */
	@NotBlank(message = "资格券id不能为空")
	private String ticketId;
}
