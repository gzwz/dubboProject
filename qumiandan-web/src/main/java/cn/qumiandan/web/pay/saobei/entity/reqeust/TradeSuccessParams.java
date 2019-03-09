package cn.qumiandan.web.pay.saobei.entity.reqeust;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取支付流水是否成功请求
 * @author yuleidian
 * @version 创建时间：2018年12月25日 下午5:44:29
 */
@Data
public class TradeSuccessParams implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 交易流水编号*/
	@NotNull(message = "流水编号不能为空")
	private Long tradeId;
	
	private Long shopId;
	
}
