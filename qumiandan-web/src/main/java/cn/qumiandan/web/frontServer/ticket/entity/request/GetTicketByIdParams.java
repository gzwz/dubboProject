package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 
 * 根据券id 查询当前券参数
 * @author lrj
 *
 */
@Data
public class GetTicketByIdParams implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 资格券id
	 */
	@NotBlank(message = "资格券id不能为空")
	private String ticketId;
}
