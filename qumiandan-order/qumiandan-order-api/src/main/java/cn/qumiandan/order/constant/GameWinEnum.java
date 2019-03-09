package cn.qumiandan.order.constant;

/**
 * 使用范围（1.全平台，2.类目，3.店铺）
 * @author WLZ
 * 2018年12月5日
 */
public enum GameWinEnum {
	
	NotWin((byte)1, "未中奖"),
	/** 中奖 */
	Win((byte)2, "中奖"),
	
	NotOpen((byte)1, "已开奖"),
	/** 中奖 */
	Open((byte)2, "未开奖"),
	
	//1.未玩过；2.玩过
	NotUse((byte)1, "未玩过"),
	/** 玩过 */
	Used((byte)2, "玩过"),
	
	;
	
	private Byte code;
	
	private String desc;

	private GameWinEnum(Byte code, String desc) {
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
