package cn.qumiandan.pay.withdraw.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;

import lombok.Data;
/**
 * 查询提现记录返回参数
 * @author lrj
 *
 */
@Data
public class QueryResponseParamsVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 提现记录
	 */
	PageInfo<WithdrawCashVo> pageInfo;
	
	/**
	 * 提现总金额
	 */
	private BigDecimal sumWithdrawalAmount;
}
