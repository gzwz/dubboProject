package cn.qumiandan.web.pay.saobei.entity.reqeust;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 扫呗创建商户回调通知信息
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:28:19
 */
@Data
public class CreateMerchatCallbackParams implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 响应码：01成功 ，02失败，请求成功不代表业务处理成功*/
	@JsonProperty("return_code")
	private String returnCode;
	
	/** 返回信息提示，“查询成功”，“请求受限”，"签署成功","签署失败"等*/
	@JsonProperty("return_msg")
	private String returnMsg;
	
	/** 请求流水号*/
	@JsonProperty("trace_no")
	private String traceNo;
	
	/** 业务结果：01商户审核通过 ，02商户审核驳回， 03电子协议签署成功，04电子协议签署失败*/
	@JsonProperty("result_code")
	private String resultCode;	
	
	/** 机构编号，扫呗分配*/
	@JsonProperty("inst_no")
	private String instNo;
	
	/** 商户号*/
	@JsonProperty("merchant_no")
	private String merchantNo;
	
	/** 签名检验串,拼装所有必传参数+令牌，32位md5加密转换（字典序）*/
	@JsonProperty("key_sign")
	private String keySign;
}
