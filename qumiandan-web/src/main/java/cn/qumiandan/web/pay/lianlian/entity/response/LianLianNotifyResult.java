package cn.qumiandan.web.pay.lianlian.entity.response;

import java.io.Serializable;

/**
 * 连连支付回调 返回结果
 * @author yuleidian
 * @version 创建时间：2019年1月5日 下午3:32:31
 */
public class LianLianNotifyResult implements Serializable {
	

	private static final long serialVersionUID = 1243721631785017233L;

	/** 交易结果code . */
	public String ret_code;

	/** 交易结果描述 . */
	public String ret_msg;

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

}
