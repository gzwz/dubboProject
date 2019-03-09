package cn.qumiandan.pay.withdraw.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 店铺申请提现结果
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午2:32:06
 */
@Data
public class ShopApplyWithdrawCashResultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 是否成功*/
	private Boolean success;
	/** 店铺id*/
	private Long shopId;
	/** 店铺名称*/
	private String shopName;
	/** 提现金额*/
	private BigDecimal withdrawCashAmount;
	/** 说明*/
	private String message;
	
}
