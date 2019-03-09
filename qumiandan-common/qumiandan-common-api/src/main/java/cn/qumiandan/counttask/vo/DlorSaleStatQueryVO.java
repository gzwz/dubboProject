package cn.qumiandan.counttask.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class DlorSaleStatQueryVO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 private Long userId; 
	 /** 创建时间*/
	 private Date startTime;
	 /** 统计的时间点 */
	 private Date endTime;
	 
	 
}
