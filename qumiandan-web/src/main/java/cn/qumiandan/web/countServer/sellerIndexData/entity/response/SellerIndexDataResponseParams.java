package cn.qumiandan.web.countServer.sellerIndexData.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SellerIndexDataResponseParams implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 总交易额
	 */
	private BigDecimal totalAmount;
	/**
	 * 预估金额
	 */
	private BigDecimal actualAmount;
}
