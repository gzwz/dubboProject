package cn.qumiandan.web.pay.saobei.entity.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 获取支付流水是否成功返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月25日 下午5:49:02
 */
@Data
public class TradeSuccessResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	/** 是否支付成功*/
	private boolean paySuccess;
	
	
	public TradeSuccessResult() {}
	
	public TradeSuccessResult(boolean paySuccess) {
		this.paySuccess = paySuccess;
	}
}
