package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class AuditUnPassParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 退款申请id
	 */
	@NotNull(message="提现申请id不能为空")
	private Long id;
	/**
	 * 审批备注
	 */
	@NotNull(message="审核备注不能为空")
	private String auditRemark;

}

