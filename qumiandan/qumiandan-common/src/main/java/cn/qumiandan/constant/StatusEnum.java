package cn.qumiandan.constant;

public enum StatusEnum {
	
	/**正常*/
	normal((byte)1,"正常"),
	
	/**删除*/
	deleted((byte)0,"删除"),
	/**
	 * 失效
	 */
	Invalid((byte)2,"失效"),
	
	/**正常*/
	TRUE((byte)1,"是"),
	
	/**删除*/
	FALSE((byte)0,"否"),
	
	/**已处理*/
	Deal((byte)1,"已处理"),
	
	/**未处理 */
	UnDeal((byte)0,"未处理 "),
	/**拒绝处理 */
	RefuseDeal((byte)2,"拒绝处理 ")
	
	
	;
	

	private Byte code;
	
	private String desc;

	private StatusEnum(Byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * @return the code
	 */
	public Byte getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Byte code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
