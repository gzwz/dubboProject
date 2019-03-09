package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.util.Set;

import javax.validation.constraints.Null;

import lombok.Data;

@Data
public class DelQualificationTicketParmas {

	@Null(message = "ids 不能为空")
	private Set<String> ids;
}
