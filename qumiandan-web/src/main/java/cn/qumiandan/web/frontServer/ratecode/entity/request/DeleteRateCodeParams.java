package cn.qumiandan.web.frontServer.ratecode.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class DeleteRateCodeParams implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 费率id
	 */
	@NotNull(message = "费率id不能为空")
	private Long id;

}
