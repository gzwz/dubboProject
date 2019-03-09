package cn.qumiandan.common.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 基类.
 * VO基类
 * @author WLZ
 */
@Data
public class BaseVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Byte status;// 状态 StatusEnum
	private String createId;// 创建人.
	private String updateId;// 修改人.
	private Date createdDate = new Date();// 创建时间.
	private Date updateDatee;// 修改时间.
}