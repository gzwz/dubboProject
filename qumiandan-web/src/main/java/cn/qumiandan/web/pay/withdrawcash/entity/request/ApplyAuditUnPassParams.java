package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplyAuditUnPassParams implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 提现申请id
	 */
	@NotNull(message="提现申请id不能为空")
	private Long id;	
	/**
	 * 提现备注审核
	 */
	@NotNull(message="审核备注不能为空")
	private String remarkAudit;
}