package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 资格券转让(单个转让）参数
 * @author lrj
 *
 */
@Data
public class MakeOverSingleTicketParams implements Serializable{

	
	private static final long serialVersionUID = 1L;


	/**
	 * 转让者手机号
	 */
	@NotBlank(message = "转让者手机号不能为空")
	private String mobile;
	
	/**
	 * 验证码
	 */
	@NotBlank(message = "验证码不能为空")
	private String code;
	
	
	/**
	 * 资格券id
	 */
	@NotBlank(message = "资格券id不能为空")
	private String ticketId;
	
	/**
	 * 转让目标人手机号
	 */
	@NotBlank(message = "转让目标人手机号不能为空")
	private String targetMobil;
}
