package cn.qumiandan.web.commonServer.sms.template;

public enum SmsTemplate {

	SMS_141220041("SMS_141220041","测试类短信",""),
	;
	/**模板编号*/
	private String code;
	/**模板说明*/
	private String desc;
	/**模板内容*/
	private String content;
	
	private SmsTemplate(String code, String desc,String content) {
		this.code = code;
		this.desc = desc;
		this.content = content;
	}
	/**模板编号*/
	public String getCode() {
		return code;
	}
	/**模板说明*/
	public String getDesc() {
		return desc;
	}
	/**
	 * 模板内容
	 */
	public String getContent() {
		return content;
	}
	
}
