package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class BackstageQueryCounponParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** id */
	private Long id;

	/** 模板id */
	private Long templeteId;
	
	/** 优惠券名称 */
	private String name;

	/** 发行者(模板中的使用范围，是店铺，则提供店铺id) */
	private Long publisher;

	/** 发行者名称(是店铺，则提供店铺名称) */
	private String publisherName;
	
	/** 生效时间查询条件：开始时间 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startValidTime;

	/** 生效时间查询条件：结束时间*/
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endValidTime;
	
	/** 失效时间查询条件：开始时间 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startInvalidTime;

	/** 失效时间查询条件：结束时间*/
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endInvalidTime;
	
	/**
	 * 页码
	 */
	private Integer pageNum;
	
	/**
	 * 页面大小
	 */
	private Integer pageSize;
	
	public Integer getPageNum() {
		return this.pageNum == null ? 1 : this.pageNum;
	}

	public Integer getPageSize() {
		return this.pageSize == null ? 10 : this.pageSize;
	}

}
