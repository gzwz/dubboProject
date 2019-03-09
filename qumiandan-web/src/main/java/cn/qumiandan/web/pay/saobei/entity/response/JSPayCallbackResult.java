package cn.qumiandan.web.pay.saobei.entity.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.qumiandan.pay.saobei.enums.SaoBeiResult;
import lombok.Data;

/**
 * 平台公众号支付回调结果返回
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:44:04
 */
@Data
public class JSPayCallbackResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 响应码：01成功 ，02失败，请求成功不代表业务处理成功*/
	@JsonProperty("return_code")
	private String returnCode;
	
	/** 返回信息提示，“处理成功”，“处理失败”等*/
	@JsonProperty("return_msg")
	private String returnMsg;
	
	
	public static JSPayCallbackResult createFail(String returnMsg) {
		JSPayCallbackResult result = new JSPayCallbackResult();
		result.setReturnCode(SaoBeiResult.RESULT_SUCCESS.getCode());
		result.setReturnMsg(returnMsg);
		return result;
	}
	
}
