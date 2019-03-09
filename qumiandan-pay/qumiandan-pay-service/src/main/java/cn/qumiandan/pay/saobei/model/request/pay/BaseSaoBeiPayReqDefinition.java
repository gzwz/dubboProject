package cn.qumiandan.pay.saobei.model.request.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.model.request.AbstractSaoBeiReqDefinition;
import lombok.Getter;

/**
 * 支付交易基础信息
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午2:19:41
 */
@Getter
public class BaseSaoBeiPayReqDefinition extends AbstractSaoBeiReqDefinition {

	private static final long serialVersionUID = 1L;

	/** 商户号*/
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;
	
	
	/** 令牌 每个商户有独立的token*/
	protected String accessToken;
	
	@Override
	public String getSign() {
		if (keySign == null || "".equals(keySign)) {
			createResolveMap();
			doGetSign();
		}
		return keySign;
	}

	protected void doGetSign() {
		if (!reqCondition.isEmpty()) {
			StringBuilder sign = new StringBuilder();
			reqCondition.entrySet().stream().forEach(condtion -> {
				sign.append(AND).append(condtion.getKey()).append(EQ).append(condtion.getValue());
			});
			// acToken必须加在最后 不参与排序
			sign.append(AND).append("access_token").append(EQ).append(accessToken).deleteCharAt(0);
			this.keySign = encrypt(sign.toString());
			reqCondition.put("key_sign", keySign);
		}
	}
	
	@Override
	public void initInherentParameter(Object... params) {
	}
	
	public BaseSaoBeiPayReqDefinition setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public BaseSaoBeiPayReqDefinition setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
		return this;
	}

	
}
