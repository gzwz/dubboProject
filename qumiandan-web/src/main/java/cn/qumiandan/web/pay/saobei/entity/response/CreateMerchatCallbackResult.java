package cn.qumiandan.web.pay.saobei.entity.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 商户回调结果 返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:36:38
 */
@Data
public class CreateMerchatCallbackResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 响应码：01成功 ，02失败，请求成功不代表业务处理成功*/
	@JsonProperty("return_code")
	private String returnCode;
	
	/** 返回信息提示，“处理成功”，“处理失败”等*/
	@JsonProperty("return_msg")
	private String returnMsg;
	
	/** 原通知流水号*/
	@JsonProperty("return_code")
	private String traceNo;
}
