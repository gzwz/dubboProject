package cn.qumiandan.web.frontServer.supportpayment.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class GetSupportPaymentByCodeParams implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 支付方式code
	 */
	@NotBlank(message = "支付方式code不能为空")
	private String code;
}
