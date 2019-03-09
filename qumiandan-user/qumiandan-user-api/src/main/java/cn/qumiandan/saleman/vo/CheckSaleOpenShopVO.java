package cn.qumiandan.saleman.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CheckSaleOpenShopVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**店铺所属区Code*/
	private String shopAreaCode;
	
	/**业务员id*/
	private Long salemanId;
	
}
