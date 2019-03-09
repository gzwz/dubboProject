package cn.qumiandan.pay.saobei.model.request.merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * 验证商户名是否重复请求
 * @author yuleidian
 * @version 创建时间：2018年12月6日 下午2:31:00
 */
@Getter
public class ChecMerchantNameReq extends BaseMerchantReqDefinition {

	private static final long serialVersionUID = 1L;

	/** 待效验的商户名*/
	@Expose
	@SerializedName("merchant_name")
	private String merchantName;

	public ChecMerchantNameReq() {
		super();
	}
	
	public ChecMerchantNameReq(String merchantName) {
		super();
		this.merchantName = merchantName;
	}
	
	public static ChecMerchantNameReq create(String merchantName) {
		return new ChecMerchantNameReq(merchantName);
	}
	
	public ChecMerchantNameReq setMerchantName(String merchantName) {
		this.merchantName = merchantName;
		return this;
	}
}
