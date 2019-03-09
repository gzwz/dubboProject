package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplyAuditPassParams implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 提现申请id
	 */
	@NotNull(message="提现申请id不能为空")
	private Long id;	
}