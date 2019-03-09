package cn.qumiandan.web.frontServer.announcement.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询平台、平台->业务员、平台->商家公告数量参数
 * @author lrj
 *
 */
@Data
public class GetCountParams implements Serializable{
	
	/**
	 * 归属id
	 */
	private String belongId;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 查询的数据量
	 */
	private Integer count;
}
