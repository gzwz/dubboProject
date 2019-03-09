package cn.qumiandan.pay.saobei.vo.response.merchant.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.vo.response.merchant.BaseMerchantResVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 创建商户回调结果
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:21:06
 */
@Getter
@Setter
public class CreateMerchatCallbackVO extends BaseMerchantResVO {

	private static final long serialVersionUID = 1L;
	
	/** 机构编号，扫呗分配*/
	@Expose
	@SerializedName("inst_no")
	private String instNo;
	
	/** 商户号*/
	@Expose
	@SerializedName("merchant_no")
	private String merchantNo;
	
	/** 签名验证*/
	@Expose
	@SerializedName("key_sign")
	private String keySign;
	
}
