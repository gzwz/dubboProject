package cn.qumiandan.web.pay.cashback.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 
 * @author lrj
 *
 */
@Data
public class AuditCashbackParams implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "返现记录id不能未空")
	private Long id;

	/**
	 * 返现审核备注
	 */
	private String remarkAudit;

	/**
	 * 审核标识（1：通过；0不通过）
	 */
	@NotNull(message = "审核标识不能未空")
	private Byte status;

}
