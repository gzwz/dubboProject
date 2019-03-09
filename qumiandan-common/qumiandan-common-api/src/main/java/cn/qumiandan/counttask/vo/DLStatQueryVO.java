package cn.qumiandan.counttask.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DLStatQueryVO implements Serializable{
	 private static final long serialVersionUID = 1L;
	 
	 /** 店铺id*/
	 private List<Long>  shopIds; 
	 
	 /**
	  * 开始时间
	  */
	 private Date startTime;
	 
	 /**
	  * 结束时间
	  */
	 private Date endTime;
	 
}
