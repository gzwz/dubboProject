package cn.qumiandan.pay.withdraw.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 体现审核记录实体
 * @author lrj
 *
 */

@Data
@TableName(value="qmd_withdraw_cash_audit_record")
public class WithdrawCashAuditRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 编号
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long id;
	
	/**
	 * 提现申请编号
	 */
	private Long withdrawId;
	
	/**
	 * 审核状态 1 审核通过  2 审核不通过
	 */
	private Byte status ;
	
	/**
	 * 审核人id
	 */
	private Long auditor;
	
	/**
	 * 审核人时间
	 */
	private Date auditDate;
	
	/**
	 * 审核说明
	 */
	private String remark;
	
	/**
	 * 请求参数
	 */
	private String requestParam;

	/**
	 * 创建者
	 */
	private Long createId;

	/**
	 * 更新者
	 */
	private Long updateId;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;
}
