package cn.qumiandan.coupon.service.beanname;

/**
 * 所有优惠券规则的名字
 * @author WLZ
 * 2018年12月14日
 */
public enum TakeRuleBeanNames {

	/** 新用户领取 */
	TakeLimitNewUser("新用户领取"),
	
	;
	private String cnName;

	private TakeRuleBeanNames(String cnName) {
		this.cnName = cnName;
	}

	public String getCnName() {
		return cnName;
	}
	
	
}
