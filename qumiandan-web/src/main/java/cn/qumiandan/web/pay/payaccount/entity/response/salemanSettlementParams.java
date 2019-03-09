package cn.qumiandan.web.pay.payaccount.entity.response;

import java.io.Serializable;

import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.saleman.vo.SalemanVO;
import lombok.Data;
@Data
public class salemanSettlementParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 账户信息
	 */
	private PayAccountVO accountVO;
	
	/**
	 * 银行卡信息
	 */
	private BankCardVO bankCardVO;
	
	/**
	 * 业务员id
	 */
	private SalemanVO salemanVO;

}
