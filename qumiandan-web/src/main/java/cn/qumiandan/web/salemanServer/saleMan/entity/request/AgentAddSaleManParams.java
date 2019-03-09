package cn.qumiandan.web.salemanServer.saleMan.entity.request;

import javax.validation.constraints.NotBlank;

public class AgentAddSaleManParams {

	/**
     * 用户名
     */
	@NotBlank(message = "用户名不能为空")
    private String userName;



	
}
