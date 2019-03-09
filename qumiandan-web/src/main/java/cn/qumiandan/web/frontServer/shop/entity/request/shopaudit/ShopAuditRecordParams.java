package cn.qumiandan.web.frontServer.shop.entity.request.shopaudit;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 平台审核店铺开店信息参数
 * @author lrj
 * @version 创建时间：2018年12月21日 下午2:11:23
 */
@Data
public class ShopAuditRecordParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 店铺id*/
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	/** 审核状态*/
	@NotNull(message = "审核状态不能为空")
//	@Pattern(regexp="[6-7]*",message="审核状态只能为6（未通过）,或7（通过）")
	private Byte status;
	
	/** 审核人id*/
	private Long auditor;
	
	/** 审核说明内容*/
	private String remark;

	private Long createId;					// 创建人.

}
