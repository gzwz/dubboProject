package cn.qumiandan.payaccount.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 账户资产返回参数
 * @author lrj
 *
 */
@Data
public class AcountMoneyVO implements Serializable {


	private static final long serialVersionUID = 1L;

	private Long id;	
	
	private Long shopId;//店铺编号 

	private String shopName;	//店铺名称
	
	private String shopLogo;	//店铺logo-url
	
	/** 银行卡持卡人姓名*/
	private String bankCardHolder;
	
	/** 账户余额*/
	private BigDecimal accountBalance;
	
	/** 可提现金额*/
	private BigDecimal settBalance;
}
