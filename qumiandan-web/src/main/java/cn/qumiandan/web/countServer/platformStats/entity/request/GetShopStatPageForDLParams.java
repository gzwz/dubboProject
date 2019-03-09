package cn.qumiandan.web.countServer.platformStats.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class GetShopStatPageForDLParams implements Serializable{
 private static final long serialVersionUID = 1L;
	 
	 /** 店铺id*/
 	@NotNull(message = "店铺id不能为空")
	 private List<Long>  shopIds; 
	 
	 /**
	  * 开始时间
	  */
 	@NotNull(message = "开始时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
	 private Date startTime;
	 
	 /**
	  * 结束时间
	  */
 	@NotNull(message = "结束时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
	 private Date endTime;
	 
}
