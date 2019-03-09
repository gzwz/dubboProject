package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class AgentQueryTicketByUserIdParams extends PageInfoParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 下线userId
	 */
	@NotNull(message = "下线用户id不能为空")
	private Long offlineUserId;
	
	/**
	 * 状态（1.未消费,2.消费中,3.已消费,4.已返现,5.已经删除）
	 */
	private Byte status;
	
}
