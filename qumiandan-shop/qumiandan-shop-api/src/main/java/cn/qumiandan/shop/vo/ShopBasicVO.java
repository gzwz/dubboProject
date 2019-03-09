package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 店铺、店铺扩展表传输类
 * @author lrj
 *
 */
@Data
public class ShopBasicVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

		//=====================店铺基础信息=======================
		private Long id;	//店铺编号

		private String name;	//店铺名称

		private String description;	//店铺简介

		private Long shopTypeId;	//店铺类型

		private String license;	//营业执照编号
		
		/**
		 * 营业证件类型：0营业执照，1三证合一
		 */
		private String licenseType;
		
		private String  merchantCompany;

		private String logo;	//店铺logo-url

		private String longitude;	//经度

		private String latitude;	//纬度

		private String proCode;	//省编号

		private String cityCode;	//市编号

		private String countyCode;	//区/县编号

		private String townCode;	//乡镇编号

		private String address;	//详细地址
		
		/**
		 * 店铺联系人姓名
		 */
		private String merchantPerson;

		private String phone;	//店铺联系电话

		private Long salemanId ;//营业员id
		
		/**
		 * 初创店铺 管理员id
		 */
		private Long shopAdminId;
		
		/**
		 *  店铺状态(
		 *  2.营业员提交待审核，
		 *  3.审核通过待扫呗审核，
		 *  4.扫呗审核未通过 待用户修改，
		 *  5.用户修改后待审核，
		 *  6.店铺审核未通过，
		 *  7.店铺审核通过，
		 *  9.更新提交待审核，
		 *  10.更新审核未通过(审核通过为7)，
		 *  11.主动申请关闭，
		 *  12.平台强制关闭
		 */
		private Byte status;

		//=====================店铺扩展信息=======================
//		private Long shopId;	//店铺编号

		private String bankAccount;	//银行卡号

		private Long bankId;	//开户行

		private String cardHolder;	//持卡人
		
		/**
		 * 法人身份证号
		 */
		private String legalIdNum;

		private String corporation;	//法人

		private BigDecimal maxScan;	//最大扫码付款金额

		private BigDecimal maxSale;	//最大商品金额

		private BigDecimal maxFree;	//最大免单金额

		private BigDecimal dispatchBeginMoney;	//起送金额
		
		private Byte gameSwitch; //否开启游戏支付（1.是，0.否）

		private Date openTime;	//店铺营业起始时间

		private Date restTime;	//店铺营业结束时间

		private String item;	//特色信息

		private Byte isOpenShut;	//是否开启打烊(1:是；0：否)

		private BigDecimal totalIncome;	//总营业额

		private Integer membersLimit;	//成员数量限制
		
		private String supportPayment;	//支付方式
		
		private String merchantNo;//商户号
		
		private String terminalId; //设备id
		
		private String accessToken;//访问令牌
				
		private  Long salesVolume;//销量
		
		private BigDecimal hotExponent;//热门指数
		
		/**
		 * 人均消费（单位：分）
		 */
		private BigDecimal perCapitaConsume;
		
		private Long industryId;	//行业id

}
