package cn.qumiandan.web.commonServer.maintain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 添加维护记录参数
 * @author lrj
 *
 */
@Data
public class AddMaintainRecordParams implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 维护模块id
	 */
	@NotNull(message = "维护类型id不能为空")
	private Long maintainTypeId;

	/**
	 * 升级说明
	 */
	@NotBlank(message = "升级说明不能为空")
	private String description;

	/**
	 * 维护开始时间
	 */
	@NotNull(message = "维护开始时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;

	/**
	 * 结束时间
	 */
	@NotNull(message = "维护结束时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;


	/**
	 * 创建者
	 */
	private Long createId;
}
