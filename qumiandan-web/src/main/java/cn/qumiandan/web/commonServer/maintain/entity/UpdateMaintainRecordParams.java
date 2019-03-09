package cn.qumiandan.web.commonServer.maintain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 修改维护记录参数
 * @author lrj
 *
 */
@Data
public class UpdateMaintainRecordParams implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "维护记录id不能为空")
	 	private Long id;

	    /**
	     * 升级说明
	     */
	    private String description;

	    /**
	     * 维护开始时间
	     */
		@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	    private Date startDate;
	    
	    /**
	     * 结束时间
	     */
		@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	    private Date endDate;
	    
	    /**
	     * 更新者
	     */
	    private Long updateId;


}
