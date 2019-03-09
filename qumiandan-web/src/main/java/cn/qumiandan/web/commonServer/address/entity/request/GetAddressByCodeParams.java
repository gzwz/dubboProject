package cn.qumiandan.web.commonServer.address.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
@Data
public class GetAddressByCodeParams implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * （省/市/县/区）编号
	 */
	@NotBlank(message = "（省、市、县/区、乡镇/街道）编号不能为空")
	@Pattern(regexp="^\\d{6,9}$",message="（省、市、县/区、乡镇/街道）编码格式不正确")
	private String code;
	
	/**
	 * （省/市/县/区）编号级别
	 */
	@NotNull(message = "编号级别不能为空")
	private Integer level;
}
