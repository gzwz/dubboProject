package cn.qumiandan.coupon.service.beanname;

/**
 * 所有优惠券规则的名字
 * @author WLZ
 * 2018年12月14日
 */
public enum UseRuleBeanNames {

	/** 立减 */
	UDirectCutCash("立减"),
	/** 满减 */
	UMoneyOff("满减"),
	/** 指定商品不能使用 */
	// 独立使用，不参与价格计算
	SomeProductCanNotUse("指定商品不能使用"),
	
	;
	private String cnName;

	private UseRuleBeanNames(String cnName) {
		this.cnName = cnName;
	}

	public String getCnName() {
		return cnName;
	}
	
	
}
