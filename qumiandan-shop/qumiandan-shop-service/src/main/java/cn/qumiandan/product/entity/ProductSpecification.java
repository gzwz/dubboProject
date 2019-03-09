package cn.qumiandan.product.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "qmd_product_specification")
public class ProductSpecification {

	/**商品编号*/
	@TableId(type = IdType.AUTO)
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
