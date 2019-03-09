package cn.qumiandan.pay.saobei.model.request.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * 
 * 微信公众号JSAPI支付授权
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午2:21:29
 */
@Getter
public class WechatAuthopenIdReq extends BaseSaoBeiPayReqDefinition {
	
	private static final long serialVersionUID = 1L;
	
	/** 
	 * 回调地址
	 * 您的收银台的完整路径，并urlEncode
	 *（get请求拼接需要urlEncode，签名拼接不需要urlEncode），带参数则会原样返回
	 */
	@Expose
	@SerializedName("redirect_uri")
	private String redirectUri;

	/** 终端号*/
	@Expose
	@SerializedName("terminal_no")
	private String terminalNo;
	
	/**
	 * 获取url
	 * @param requestUrl
	 * @return
	 */
	public String getUrl(String requestUrl) {
		getSign();
		StringBuilder url = new StringBuilder(requestUrl).append("?");
		url.append("merchant_no").append(EQ).append(getMerchantNo())
			.append(AND).append("terminal_no").append(EQ).append(getTerminalNo())
			.append(AND).append("redirect_uri").append(EQ).append(getRedirectUri())
			.append(AND).append("key_sign").append(EQ).append(reqCondition.get("key_sign"));
		return url.toString();
	}
	
	@Override
	protected void doGetSign() {
		if (!reqCondition.isEmpty()) {
			StringBuilder sign = new StringBuilder();
			reqCondition.entrySet().stream().forEach(condtion -> {
				if ("redirect_uri".equals(condtion.getKey())) {
					try {
						sign.append(AND).append(condtion.getKey()).append(EQ).append(URLDecoder.decode(condtion.getValue(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					sign.append(AND).append(condtion.getKey()).append(EQ).append(condtion.getValue());
				}
				
			});
			// acToken必须加在最后 不参与排序
			sign.append(AND).append("access_token").append(EQ).append(accessToken).deleteCharAt(0);
			this.keySign = sign.toString();
			reqCondition.put("key_sign", encrypt(keySign));
		}
	}
	
	public WechatAuthopenIdReq() {
		super();
	}

	public WechatAuthopenIdReq setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
		return this;
	}

	public WechatAuthopenIdReq setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
		return this;
	}
	
	/*public static WechatAuthopenIdReq create(WechatAuthopenIdVO vo) {
		WechatAuthopenIdReq req =  
		CopyBeanUtil.copyBean(vo, WechatAuthopenIdReq.class);
		return req;
	}*/
	
	
}
