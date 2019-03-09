package cn.qumiandan.counttask.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@Data
@TableName("qmd_dlorsaleId_stats")
public class DlorSaleManStat {

	 @TableId
	 private Long id; 
	 /** 业务员id,或者代理id*/
	 private Long  userId; 
	 /** 利润（入账）*/
	 private BigDecimal profit;
	 /** 创建时间*/
	 private Date createDate;
	 /** 统计的时间点 */
	 private Date countDate;
}