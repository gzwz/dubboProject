package cn.qumiandan.ticket.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("qmd_qualification_ticket")
public class QualificationTicket {
	
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	
	/** 归属用户id */
	private Long userId;
	
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
	
	/** 状态（1.未消费,2.消费中,3.已消费,4.已返现,5.已经删除）*/
	private Byte status;
	
	/** 创建者id */
	private Long createId;
	
	/** 修改者id */
	private Long updateId;
	
	/** 创建时间 */
	private Date createDate;
	
	/** 修改时间 */
	private Date updateDate;
	
}
