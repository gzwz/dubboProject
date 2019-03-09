package cn.qumiandan.web.frontServer.shop.entity.request.updateshop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.qumiandan.shop.vo.ShopPictureVO;
import lombok.Data;

@Data
public class ManagerShopUpdateParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "店铺id不能为空")
	private Long id;			// 店铺编号
	
	private String description;	// 店铺简介
	
	private String logo;		// 店铺logo-url
	
	private String longitude;	// 经度

	private String latitude;	// 纬度
	
	private String address;		// 详细地址
	
	private String phone;		// 店铺联系电话
	
	@NotNull(message = "店铺营业起始时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date openTime; // 店铺营业起始时间

	
	@NotNull(message = "店铺营业结束时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date restTime; // 店铺营业结束时间

	private String item;		// 特色信息
	
	private Byte isOpenShut;	// 是否开启打烊(1:是；0：否)

	//private String supportPayment;	//支付方式
	
	/**
	 * 人均消费（单位：分）
	 */
	private BigDecimal perCapitaConsume;

	private List<ShopPictureVO> shopPictureVOList;
	
	private Long updateId;
	
	private Date updateDate = new Date();
	
	
}
