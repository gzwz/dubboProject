package cn.qumiandan.shop.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 平台审核店铺开店信息记录VO
 * @author yuleidian
 * @version 创建时间：2018年12月10日 下午2:11:23
 */
@Data
public class ShopAuditRecordVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 审核记录*/
	private Long id;
	
	/** 店铺id*/
	private Long shopId;
	
	/** 审核状态*/
	private Byte status;
	
	/** 审核人id*/
	private Long auditor;
	
	/** 审核日期*/
	private Date auditDate;
	
	/** 审核说明内容*/
	private String remark;

	private Long createId;					// 创建人.
	private Long updateId;					// 修改人.
	
	private Date createDate;				// 创建时间.
	
	private Date updateDate;				// 修改时间.
}
