package cn.qumiandan.pay.saobei.vo.response.merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建终端返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月5日 上午11:47:11
 */
@Getter
@Setter
public class CreateTerminalResVO extends BaseMerchantResVO {

	private static final long serialVersionUID = 1L;
	
	/** 商户号*/
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;
	/** 门店编号*/
	@Expose
	@SerializedName("store_code")
	private String storeCode;
	/** 终端号*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;
	/** 令牌*/
	@Expose
	@SerializedName("access_token")
	private String accessToken;
	/** 签名检验串,拼装所有必填参数+令牌，32位md5加密转换*/
	@Expose
	@SerializedName("key_sign")
	private String keySign;
}
