package cn.qumiandan.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("qmd_coupon")
public class Coupon {
 
	/** id */
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 模板id */
	private Long templeteId;
	
	/** 序列号 */
	private String serialNo;
	
	/** 优惠券名称 */
	private String name;
	
	/** 优惠券面值 */
	private BigDecimal faceValue;

	/** 发行者(模板中的使用范围，是店铺，则提供店铺id) */
	private Long publisher;

	/** 领取规则  json key:规则id, value:规则内容 */
	private String takeRule;
	
	/** 使用规则  json key:规则id, value:规则内容 */
	private String useRule;
	
	/** 发行者名称(是店铺，则提供店铺名称) */
	private String publisherName;

	/** 使用条件说明 */
	private String conditionDesc;
	
	/** 生效时间 */
	private Date startTime;

	/** 失效时间 */
	private Date endTime;
	
	/** 有效期 */
	private Integer validity;
	
	/** 发行总数量 */
	private Long totalNumber;
	
	/** 剩余数量(剩余数量 < 发行总数量) */
	private Long remainNumber;
	
	/**
	 * 已使用数量
	 */
	private Long usedNumber;
	
	/** 0。删除 1.正常*/
	private Byte status;
	
	/**创建人*/
	private Long createId;
	
	/**更新人*/
	private Long updateId;
	
	/**更新时间*/
	private Date updateDate;
	
	/**创建时间*/
	private Date createDate;
	
	/** 需要排除的商品*/
	private String excProductIds;
}
