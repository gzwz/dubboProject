package cn.qumiandan.web.pay.withdrawcash.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;

import lombok.Data;
/**
 * 查询
 * @author lrj
 *
 */
@Data
public class QueryResponseParams implements Serializable{

	private static final long serialVersionUID = 1L;


	/**
	 * 提现记录
	 */
	PageInfo<WithdrawCashVO2Params> pageInfo;
	
	/**
	 * 提现总金额
	 */
	private BigDecimal sumWithdrawalAmount;
}
