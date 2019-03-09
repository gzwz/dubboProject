package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 查询可用优惠券
 * @author lrj
 *
 */
@Data
public class QueryUsefulCouponParamsVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 发行者id集合
	 */
	private List<Long> publisherList;
	
	/**
	 * 有效时间
	 */
	private Date date;
	
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
