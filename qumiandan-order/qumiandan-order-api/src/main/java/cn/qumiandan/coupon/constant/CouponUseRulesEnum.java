package cn.qumiandan.coupon.constant;

/**
 * 使用规则
 * @author WLZ
 * 2018年12月5日
 */
public enum CouponUseRulesEnum {
	
	/** 立减 */
	UDirectCutCash("立减"),
	/** 满减 */
	UMoneyOff("满减"),
	/** 指定商品不能使用 */
	USomeProductCanNotUse("指定商品不能使用"),
	
	;

	private String desc;

	private CouponUseRulesEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
