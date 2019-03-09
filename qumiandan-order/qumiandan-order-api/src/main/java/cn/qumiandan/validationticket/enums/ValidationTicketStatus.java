package cn.qumiandan.validationticket.enums;

public enum ValidationTicketStatus {

	//1.未消费，2.已消费，3.未消费，已过期
	/** 未付款 */
	UnUse((byte)1, "未消费"),
	/** 已消费*/
	Used((byte)2, "已消费"),
	/** 已经过期*/
	Expired((byte)3, "已经过期"),
	;
	
	private Byte code;
	
	private String desc;

	private ValidationTicketStatus(Byte code, String desc) {
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
