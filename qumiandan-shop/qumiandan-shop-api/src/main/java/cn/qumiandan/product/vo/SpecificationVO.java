package cn.qumiandan.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SpecificationVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	/**商品编号*/
	private Long productId;
	
	/**规格名称*/
	private String name;

	/**规格值*/
	private String value;
	
	/**市场价格*/
	private BigDecimal marketPrice;
	
	/**成本价格*/
	private BigDecimal costPrice;
	
	/**商品价格*/
	private BigDecimal price;
	
	/**规格单位*/
	private String units;
	
	/**商品库存*/
	private Integer stock;
	
	
}
