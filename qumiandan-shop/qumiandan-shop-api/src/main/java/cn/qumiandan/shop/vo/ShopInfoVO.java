package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author yuleidian
 * @version 创建时间：2018年12月21日 下午5:43:09
 */
@Data
public class ShopInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;			//店铺名称
	
	private String description;		//店铺简介

	private String shopTypeName;	//店铺类型

	private String logo;			//店铺logo-url

	private String longitude;		//经度

	private String latitude;		//纬度

	private String address;			//详细地址
	
	/**
	 * 店铺联系人姓名
	 */
	private String merchantPerson;

	private String phone;			//店铺联系电话
	
	//=====================店铺扩展信息=======================

	private Date openTime;	//店铺营业起始时间

	private Date restTime;	//店铺营业结束时间

	private String item;	//特色信息

	private Byte isOpenShut;	//是否开启打烊(1:是；0：否)

	private Byte status;		// 店铺状态
	
	private BigDecimal perCapitaConsume;//人均消费
	//=====================店铺行业信息=======================
	private String industryName;		//行业id

	//=====================店铺图片信息=======================
	private List<ShopPictureVO> shopPictureVOList;
	
	//===================会员折扣信息==========================
	
	private BigDecimal discount;
	/**
	 * 店铺游戏支付开关是否开启
	 */
	private Byte gameSwitch;
}
