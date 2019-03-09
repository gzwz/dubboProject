package cn.qumiandan.ticket.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class QualificationTicketVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	/** 归属用户id */
	private Long userId;
	
	/** 归属用户名 */
	private String userName;
	/**
	 * 店铺id
	 */
	private Long shopId;
	
	/** 名字 */
	private String name;
	
	/** 现价 */
	private BigDecimal presentPrice;
	
	/** 返现金额 */
	private BigDecimal returnMoney;
	
	/**
	 * 返现门槛（创建店铺后营业额满多少以后才可以申请返现）
	 */
	private BigDecimal cashbackDoorsill;
	
	/** 状态（1.未消费，2.消费中；3已消费）*/
	private Byte status;
	
	/** 创建者id */
	private Long createId;
	
	/** 修改者id */
	private Long updateId;
	
	/** 创建时间 */
	private Date createDate;
	
	/** 修改时间 */
	private Date updateDate;
	
	/** 是否能返现：true能；false不能 ；*/
	private Boolean couldCashBack;
	
}
