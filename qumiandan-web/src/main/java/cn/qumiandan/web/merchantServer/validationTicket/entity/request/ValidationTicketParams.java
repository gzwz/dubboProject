package cn.qumiandan.web.merchantServer.validationTicket.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ValidationTicketParams {
	
	/**核销码*/
	@NotNull(message = "核销码不能为空")
	private String ticketCode;
	///**持有核销的用户id*/
	//@NotNull(message = "用户id不能为空")
	//private Long userId;
	/**核销商家*/
	//@NotNull(message = "店铺id不能为空")
	//private Long shopId;

}
