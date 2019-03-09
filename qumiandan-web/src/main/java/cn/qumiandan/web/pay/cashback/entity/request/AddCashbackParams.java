package cn.qumiandan.web.pay.cashback.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class AddCashbackParams implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 资格券id
	 */
	@NotBlank(message = "资格券id不能为空")
	private String ticketId;

	/**
	 * 返现申请备注
	 */
	private String remarkSubmit;

	/**
	 * 创建人
	 */
	private Long createId;


}
