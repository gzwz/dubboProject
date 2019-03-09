package cn.qumiandan.shop.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;

/**
 * 店铺、店铺扩展表传输类
 * @author lrj
 *
 */
@Data
public class ShopDetailVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

		//=====================店铺基础信息=======================
		private Long id;	//店铺编号

		private String name;	//店铺名称

		private String description;	//店铺简介

		private Long shopTypeId;	//店铺类型
		
		/**
		 * 商户注册名称/公司全称
		 */
		private String  merchantCompany;
		
		/**
		 * 营业证件类型：0营业执照，1三证合一
		 */
		private String licenseType;

		private String license;	//营业执照编号

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
		
		private Long salemanId ;//业务员用户id
		
		private String salemanUserName ;//业务员用户userName

		/**
		 * @ShopStatusEnum
		 */
		private Byte status;

		//=====================店铺扩展信息=======================

		private Byte gameSwitch;//是否开启游戏支付（1.是，0.否）
		
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

		private Date openTime;	//店铺营业起始时间

		private Date restTime;	//店铺营业结束时间

		private String item;	//特色信息

		private Byte isOpenShut;	//是否开启打烊(1:是；0：否)

		private BigDecimal totalIncome;	//总营业额

		private Integer membersLimit;	//成员数量限制
		
		private String supportPayment;	//支持支付方式
		
		private String merchantNo;//商户号
		
		private String terminalId; //设备id
		
		private String accessToken;//访问令牌
				
		private  Long salesVolume;//销量
		
		private BigDecimal hotExponent;//热门指数
		
		/**
		 * 人均消费（单位：分）
		 */
		private BigDecimal perCapitaConsume;

		//=====================店铺行业信息=======================
		private Long industryId;	//行业id

		//=====================店铺图片信息=======================
		private List<ShopPictureVO> shopPictureVOList;
		//------------------------扫呗相关信息-------------------------------
		private SaoBeiShopInfoVO saoBeiShopInfoVO;
		
		//======================店铺费率==============================
		/**
		 * 店铺费率
		 */
		private BigDecimal shopProfit;
		
		/** 店铺管理员账号*/
		private String mobile;
}
