package cn.qumiandan.order.constant;

/**
 * 
 * @author WLZ
 * 2018年12月5日
 */
public enum GameStatusEnum {
	
	// 1.未付款，2.已付款.，3.已消费
	/** 未付款 */
	NotPay((byte)1, "未付款"),
	/** 已付款 */
	Paid((byte)2, "已付款"),
	/** 交易完成  已消费 */
	TradeComplete((byte)3, "已消费"),
	 
	;
	
	private Byte code;
	
	private String desc;

	private GameStatusEnum(Byte code, String desc) {
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
