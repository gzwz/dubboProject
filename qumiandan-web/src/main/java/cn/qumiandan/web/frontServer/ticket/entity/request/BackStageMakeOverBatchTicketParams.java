package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
/**
 * 总后台划券参数
 * @author lrj
 *
 */
@Data
public class BackStageMakeOverBatchTicketParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
