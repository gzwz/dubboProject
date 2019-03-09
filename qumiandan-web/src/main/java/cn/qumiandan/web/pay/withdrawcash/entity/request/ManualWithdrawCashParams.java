package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改提现申请为线下打款请求参数
 * @author yuleidian
 * @date 2019年1月22日
 */
@Data
public class ManualWithdrawCashParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "提现申请id不能为空")
	private Long id;
	
}
