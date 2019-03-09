package cn.qumiandan.backgrounddata.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 代理和业务员利润
 * @author lrj
 *
 */
@Data
public class DlorSaleStatQueryVO2 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 private Long id; 
	 /** 业务员或者代理的用户id*/
	 private Long  userId; 
	 /** 利润（入账）*/
	 private BigDecimal profit;
	 /** 创建时间*/
	 private Date createDate;
	 /** 统计的时间点 */
	 private Date countDate;
}
