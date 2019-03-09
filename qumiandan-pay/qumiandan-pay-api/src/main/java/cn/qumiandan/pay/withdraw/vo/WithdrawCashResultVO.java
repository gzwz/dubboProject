package cn.qumiandan.pay.withdraw.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 提现结果
 * @author yuleidian
 * @date 2019年1月21日
 */
@Data
public class WithdrawCashResultVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 是否成功*/
	private Boolean success = false;
	/** 返回结果*/
	private String message;
	
	
}
