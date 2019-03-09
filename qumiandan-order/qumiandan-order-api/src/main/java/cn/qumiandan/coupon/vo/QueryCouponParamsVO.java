package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 总后台查询优惠券参数
 * @author lrj
 *
 */
@Data
public class QueryCouponParamsVO implements Serializable{

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
	private Date startValidTime;

	/** 生效时间查询条件：结束时间*/
	private Date endValidTime;
	
	/** 失效时间查询条件：开始时间 */
	private Date startInvalidTime;

	/** 失效时间查询条件：结束时间*/
	private Date endInvalidTime;
	
	/**
	 * 页码
	 */
	private Integer pageNum;
	
	/**
	 * 页面大小
	 */
	private Integer pageSize;
	

}
