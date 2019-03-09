package cn.qumiandan.complain.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class QueryComplainVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 投诉类型id
	 */
	private Long typeId;
	
	/**
	 * 处理状态（1.创建，2.投诉成功，3,投诉失败）
	 */
	private Byte status;
	
	private Integer pageNum;
	
	private Integer pageSize;
}
