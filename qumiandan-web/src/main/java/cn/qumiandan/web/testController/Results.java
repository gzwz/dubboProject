package cn.qumiandan.web.testController;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * 	return_code	String	2	Y	响应码：01成功 ，02失败，请求成功不代表业务处理成功
	return_msg	String	128	Y	返回信息提示，“处理成功”，“处理失败”等
	trace_no	String	32	Y	原通知流水号
 * @author yuleidian
 * @version 创建时间：2018年12月6日 下午4:03:39
 */
@Data
public class Results implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	private String return_code = "01";
	
	@Expose
	private String return_msg = "处理成功";
	
	@Expose
	private String trace_no;
}
