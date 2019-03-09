package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 查询提现申请详情
 * @author yuleidian
 * @date 2019年1月22日
 */
@Data
public class WithdrawCashDetailParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "提现申请id不能为空")
	private Long id;
}
