package cn.qumiandan.ticket.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 用户资格券数量参数
 * @author lrj
 *
 */
@Data
public class TicketNumVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 资格券数量
	 */
	private Integer ticketNum;
	
	private Long userId;
	
}
