package cn.qumiandan.web.adminServer.complain.entity.request;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class QueryComplainParams{
	
	/**
	 * 投诉类型id
	 */
	private Long typeId;
	
	/**
	 * 处理状态（1.创建，2.投诉成功，3,投诉失败）
	 */
	private Byte status;
	
	@NotNull(message = "pageNum 不能为空")
	private Integer pageNum;
	
	@NotNull(message = "pageSize 不能为空")
	private Integer pageSize;
}
