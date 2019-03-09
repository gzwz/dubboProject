package cn.qumiandan.coupon.constant;

/**
 * 领取规则 
 * @author WLZ
 * 2018年12月5日
 */
public enum CouponTakeRulesEnum {
	 
	/** 领取数量限制 */
	TakeLimitNum("领取数量限制"),
	/** 新用户领取 */
	TakeLimitNewUser("新用户领取"),
	;

	private String desc;

	private CouponTakeRulesEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
