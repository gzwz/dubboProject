package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.qumiandan.saobeishopinfo.vo.SaoBeiShopInfoVO;
import lombok.Data;

/**
 * @description：店铺修改传输对象
 * @author：zhuyangyong
 * @date: 2018/11/26 10:06
 */
@Data
public class ShopUpdateVO implements Serializable  {
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

	private String proCode;		//省编号

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
	 * 店铺初创管理id
	 */
	private Long shopAdminId;
	
	/**
	 * 店铺状态
	 * 1:创建待审核中；
	 * 2：创建审核未通过；
	 * 3：审核通过正常营业；
	 * 4：更新审核中；
	 * 5：更新审核未通过（审核通过为3）；
	 * 6：主动申请关闭；
	 * 7：平台强制关闭
	 */
//	private Byte status;

	//=====================店铺扩展信息=======================
//	private Long shopId;	//店铺编号

	private String bankAccount;	//银行卡号

	private Long bankId;		//开户行
	
	private String cardHolder;	//持卡人
	
	/**
	 * 法人身份证号
	 */
	private String legalIdNum;

	private String corporation;	// 法人

	private BigDecimal maxScan;	// 最大扫码付款金额

	private BigDecimal maxSale;	// 最大商品金额

	private BigDecimal maxFree;	// 最大免单金额

	private BigDecimal dispatchBeginMoney;	//起送金额

	private Date openTime;	//店铺营业起始时间

	private Date restTime;	//店铺营业结束时间

	private String item;	//特色信息

	private Byte isOpenShut;	//是否开启打烊(1:是；0：否)

	private BigDecimal totalIncome;	//总营业额
	
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

	//=====================店铺图片信息=======================
	private List<ShopPictureVO> shopPictureVOList;
//	private String imageName;	//图片名称

//	private String imageUrl;	//图片url

//	private String skipUrl;	//图片跳转链接

//	private String suffix;	//图片后缀名

//	private Long imageSize;	//图片大小

//	private String intro;	//图片介绍

//	private Integer imageSort;	//排列次序

//	private Byte imageType;	//类型(1：营业执照；2：其他资质；3：门头照片：4：营业执照法人手持身份证照片；5：商家相册)

	//=====================店铺分类信息=======================
//	private Long categoryId; //店铺分类id

	//=====================店铺人员信息=======================
	private String mobile;	//电话

	//=====================店铺人员信息=======================
	private Long updateId;	//修改人

	private Date updateDate;
	
	/** 扫呗信息*/
	private SaoBeiShopInfoVO saoBeiShopInfoVO;

}