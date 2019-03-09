package cn.qumiandan.pay.saobei.enums;

/**
 * 请求扫呗接口返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月5日 上午10:21:21
 */
public enum SaoBeiResult {
	
	/** 请求响应 : 成功*/
	RETURN_SUCCESS("01"),
	/** 请求响应 : 失败*/
	RETURN_FAIL("02"),
	
	/** 业务处理结果 : 成功*/
	RESULT_SUCCESS("01"),
	/** 业务处理结果 : 失败*/
	RESULT_FAIL("02"),
	;
	
	private String code;
	
	SaoBeiResult(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
