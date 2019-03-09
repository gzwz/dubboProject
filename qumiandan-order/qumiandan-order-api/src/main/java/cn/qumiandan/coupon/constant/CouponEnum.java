package cn.qumiandan.coupon.constant;

/**
 * 使用范围（1.全平台，2.类目，3.店铺）
 * @author WLZ
 * 2018年12月5日
 */
public enum CouponEnum {
	/**1.平台*/
	All((byte)0, "全平台"),
	
	/** 2.类目 */
	Category((byte)2, "类目"),
	
	/** 3.店铺 */
	Shop((byte)3, "店铺"),
	
	// 优惠券使用状态（1.未使用，2.已使用，3.已过期，4.销毁；0：已删除）
	/** 未使用 */
	UnUse((byte)1, "未使用"),
	/** 已删除 */
	Deleted((byte)0, "已删除"),
	/** 已使用 */
	HaveBeenUsed ((byte)2, "已使用"),
	/** 已过期 */
	Expired((byte)3, "已过期"),
	/** 销毁 */
	Destroyed((byte)4, "销毁"),
	
	
	;
	
	
	private Byte code;
	
	private String desc;

	private CouponEnum(Byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
