package cn.qumiandan.ticket.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 优惠券使用记录实体
 * @author lrj
 *
 */
@Data
public class TicketUseRecordVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 资格券的id
	 */
	private String ticketId;
	
	/**
	 * 上个用户id
	 */
	private Long fromUserId;
	
	/**
	 * 当前用户id
	 */
	private Long currentUserId;
	
	/**
	 * 交易时间
	 */
	private Date createDate;
}
