package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;

import lombok.Data;
/**
 * 查询可用优惠券
 * @author lrj
 *
 */
@Data
public class QueryUsefulCouponParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long shopId;
	
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
