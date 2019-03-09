package cn.qumiandan.coupon.vo;

import java.io.Serializable;
import java.util.List;

import cn.qumiandan.coupon.constant.CouponEnum;
import lombok.Data;

/**
 * 查询优惠券领取记录参数
 * @author lrj
 *
 */
@Data
public class QueryCouponTakeRecordParamsVO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String mobile;
	
	/** 生成优惠券的id */
	private Long couponId;
	
	/**状态（1.未使用，2.已使用，3.已过期，4.销毁；0：已删除）
	 * {@link CouponEnum}
	 */
	private List<Byte> statusList;
	
	


}
