package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
/**
 * 资格券转让(多条转让)参数
 * @author lrj
 *
 */
@Data
public class MakeOverBatchTicketParams implements Serializable{

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
	 * 资格券id集合
	 */
	@NotEmpty(message = "资格券id集合不能为空")
	private  Set<String> ticketIdList ;
	
	/**
	 * 接受转让者手机号
	 */
	@NotBlank(message = "转让目标人手机号不能为空")
	private String targetMobil;
}
