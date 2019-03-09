package cn.qumiandan.counttask.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class DlorSaleStatQueryResultVO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 private Long id; 
	 
	 /** 业务员id,或者代理id*/
	 private Long userId; 
	 /** 利润（入账）*/
	 private BigDecimal profit;
	 /** 创建时间*/
	 private Date createDate;
	 /** 统计的时间点 */
	 private Date countDate;
}
