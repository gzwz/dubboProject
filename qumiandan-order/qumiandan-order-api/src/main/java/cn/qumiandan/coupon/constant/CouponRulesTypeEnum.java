package cn.qumiandan.coupon.constant;

public enum CouponRulesTypeEnum {

	/*归属（1.创建规则，2.领取规则，3.使用规则）*/
	/** 1.创建规则 */
	CreateRules((byte) 1, "创建规则"),
	
	/** 2.领取规则 */
	TakeRules((byte) 2, "领取规则"),
	
	/** 3.使用规则 */
	UseRules((byte) 3, "使用规则"),
	
	;

	private Byte code;
	private String desc;

	private CouponRulesTypeEnum(Byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Byte getCode() {
		return this.code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
