package cn.qumiandan.ticket.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 优惠券使用记录实体
 * @author lrj
 *
 */
@Data
@TableName(value="qmd_ticket_use_record")
public class TicketUseRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
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
