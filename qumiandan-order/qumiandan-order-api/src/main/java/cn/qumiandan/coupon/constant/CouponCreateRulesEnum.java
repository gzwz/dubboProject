package cn.qumiandan.coupon.constant;

/**
 * 创建规则
 * @author WLZ
 * 2018年12月5日
 */
public enum CouponCreateRulesEnum {
	
	/** 总量限制 */
	AllPublishNum("总量限制"),
	
	
	;
	private String desc;

	private CouponCreateRulesEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}