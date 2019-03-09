package cn.qumiandan.web.countServer.dlorsalestats.entity.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 代理端业务员端统计参数
 * @author lrj
 *
 */
@Data
public class GetSaleStatListParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
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
