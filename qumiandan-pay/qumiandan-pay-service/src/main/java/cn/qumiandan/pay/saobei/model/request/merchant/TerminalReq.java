package cn.qumiandan.pay.saobei.model.request.merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * 创建终端请求VO
 * @author yuleidian
 * @version 创建时间：2018年12月5日 上午11:43:28
 */
@Getter
public class TerminalReq extends BaseMerchantReqDefinition {

	private static final long serialVersionUID = 1L;
	
	/** 商户号*/
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;

	public TerminalReq() {
		super();
	}
	
	public TerminalReq(String merchantNo) {
		super();
		this.merchantNo = merchantNo;
	}
	
	public static TerminalReq create(String merchantNo) {
		return new TerminalReq(merchantNo);
	}
	
	public TerminalReq setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
		return this;
	}

}
