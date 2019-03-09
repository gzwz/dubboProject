package cn.qumiandan.pay.saobei.vo.response.merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMerchantResVO extends BaseMerchantResVO {

	private static final long serialVersionUID = 1L;

	/** 商户号 */
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;
	
	/** 签名检验串,拼装所有非空参数+令牌，32位md5加密转换*/
	@Expose
	@SerializedName("key_sign")
	private String keySign;
}
