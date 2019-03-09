package cn.qumiandan.sellerIndexData.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
/**
 * 商家端首页数据展示vo
 * 
 * @author W
 *
 */
@Data
public class SellerIndexDataVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;
	/**
	 * 预估金额
	 */
	private BigDecimal futureAmount;
	
	
	public SellerIndexDataVO(){
		tradeAmount = new BigDecimal(0);
		futureAmount = new BigDecimal(0);
	}
}
