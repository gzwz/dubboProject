package cn.qumiandan.pay.withdraw.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
/**
 * 业务员等级的申请提现结果
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午3:04:03
 */
@Data
public class SalemanLevelApplyWithdrawCaseResultVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 是否成功*/
	private Boolean success;
	/** 提现金额*/
	private BigDecimal withdrawCashAmount;
	/** 说明*/
	private String message;

}
