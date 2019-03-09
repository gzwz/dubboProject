package cn.qumiandan.pay.saobei.vo.response;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.pay.ResponseDefinition;
import cn.qumiandan.pay.saobei.enums.SaoBeiResult;
import lombok.Data;
/**
 * 	return_code	String	2	Y	响应码：01成功 ，02失败，请求成功不代表业务处理成功
	return_msg	String	128	Y	返回信息提示，“查询成功”，“请求受限”等
	
	result_code String	2	Y	业务处理结果：01成功 ，02失败
	
 * @author yuleidian
 * @version 创建时间：2018年12月5日 上午10:02:34
 */
@Data
public abstract class AbstractSaoBeiResponse implements Serializable, ResponseDefinition {

	private static final long serialVersionUID = 1L;

	protected static final String AND = "&";
	protected static final String EQ = "=";
	
	/** 响应码：01成功 ，02失败，请求成功不代表业务处理成功*/
	@Expose
	@SerializedName("return_code")
	private String returnCode;
	
	/** 返回信息提示，“查询成功”，“请求受限”等*/
	@Expose
	@SerializedName("return_msg")
	private String returnMsg;
	
	/** 在return_code为`01`时返回  业务处理结果*/
	@Expose
	@SerializedName("result_code")
	private String resultCode;

	@Override
	public boolean isSuccess() {
		if (SaoBeiResult.RETURN_FAIL.getCode() == this.returnCode) {
			throw new QmdException(PayErrorCode.PAY8000.getCode(), "调用扫呗接口返回错误信息:" + returnMsg);
		}
		return SaoBeiResult.RETURN_SUCCESS.getCode().equals(resultCode) ? true : false;
	}
}
