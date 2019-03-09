package cn.qumiandan.web.testController;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 *  return_code	String	2	Y	响应码：01成功 ，02失败，请求成功不代表业务处理成功
	return_msg	String	128	Y	返回信息提示，“查询成功”，“请求受限”，"签署成功","签署失败"等
	trace_no	String	32	Y	请求流水号
	result_code	String	2	Y	业务结果：01商户审核通过 ，02商户审核驳回， 03电子协议签署成功，04电子协议签署失败
	inst_no	String	15	Y	机构编号，扫呗分配
	merchant_no	String	15	Y	商户号
	key_sign	String	32	Y	签名检验串,拼装所有必传参数+令牌，32位md5加密转换（字典序）
 * @author yuleidian
 * @version 创建时间：2018年12月6日 下午3:57:05
 */
@Data
public class CreateMerchantReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	private String return_code;
	@Expose
	private String return_msg;
	@Expose
	private String trace_no;
	@Expose
	private String result_code;
	@Expose
	private String merchant_no;
	@Expose
	private String key_sign;
	
}
