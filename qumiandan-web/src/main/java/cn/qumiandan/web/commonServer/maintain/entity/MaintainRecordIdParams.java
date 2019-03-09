package cn.qumiandan.web.commonServer.maintain.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 维护记录id
 * @author lrj
 *
 */
@Data
public class MaintainRecordIdParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 维护记录id
	 */
	@NotNull(message = "维护记录id不能为空")
	private Long maintainRecordId;
}
