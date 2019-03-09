package cn.qumiandan.ticket.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 代理查询下线的资格券列表
 * @author lrj
 *
 */
@Data
public class AgentQueryTicketByUserIdVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 代理用户id
	 */
	private Long agentUserId;
	
	/**
	 * 下线userId
	 */
	private Long offlineUserId;
	
	/**
	 * 状态（1.未消费,2.消费中,3.已消费,4.已返现,5.已经删除）
	 */
	private Byte status;
	
	private Integer pageNum;
	
	private Integer pageSize;
}
