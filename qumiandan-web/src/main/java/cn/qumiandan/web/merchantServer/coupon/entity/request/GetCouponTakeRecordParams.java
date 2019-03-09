package cn.qumiandan.web.merchantServer.coupon.entity.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.coupon.constant.CouponEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 查询优惠券领取记录参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class GetCouponTakeRecordParams extends CommonParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	private String mobile;
	
	/** 生成优惠券的id */
	@NotNull(message = "优惠券id不能为空")
	private Long couponId;
	
	/**状态（1.未使用，2.已使用，3.已过期，4.销毁；0：已删除）
	 * {@link CouponEnum}
	 */
	private List<Byte> statusList;
	
}
