package cn.qumiandan.pay.saobei.model.request.pay;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.utils.DateUtil;
import lombok.Getter;

/**
 * 公众号预支付（统一下单）请求
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午5:04:29
 */
@Getter
public class JSPayReq extends BaseSaoBeiPayReqDefinition {

	private static final long serialVersionUID = 1L;

	/** 版本号，当前版本100*/
	@Expose
	@SerializedName("pay_ver")
	private String payVer;
	
	/** 请求类型，010微信，020支付宝，060qq钱包，090口碑，100翼支付*/
	@Expose
	@SerializedName("pay_type")
	private String payType;
	
	/** 接口类型，当前类型012*/
	@Expose
	@SerializedName("service_id")
	private String serviceId;
	
	/** 设备号*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;
	
	
	/** 终端流水号，填写商户系统的订单号*/
	@Expose
	@SerializedName("terminal_trace")
	private String terminalTrace;
	
	/** 终端交易时间，yyyyMMddHHmmss，全局统一时间格式*/
	@Expose
	@SerializedName("terminal_time")
	private String terminalTime;
	
	/** 金额，单位分*/
	@Expose
	@SerializedName("total_fee")
	private String totalFee;
	
	/** 公众号appid，公众号支付时使用的appid（若传入，则open_id需要保持一致）*/
	@Expose
	@SerializedName("sub_appid")
	private String subAppid;
	
	/** 用户标识（微信openid，支付宝userid），pay_type为010及020时需要传入*/
	@Expose
	@SerializedName("open_id")
	private String openId;
	
	/** 订单描述*/
	@Expose
	@SerializedName("order_body")
	private String orderBody;	
	
	/** 外部系统通知地址*/
	@Expose
	@SerializedName("notify_url")
	private String notifyUrl;
	
	/** 附加数据，原样返回*/
	@Expose
	@SerializedName("attach")
	private String attach;
	
	private Long tradeId;
	
	/** 订单包含的商品列表信息，Json格式。pay_type为090时，可选填此字段*/
	/*@Expose
	@SerializedName("goods_detail")*/
	//private GoodsDetail goodsDetail;
	
	public JSPayReq() {
		this.payVer = "100";
		this.serviceId = "012";
		this.terminalTime = DateUtil.parseDateToStr(new Date(), "yyyyMMddHHmmss");
	}
	
	@Override
	public String getSign() {
		if (StringUtils.isBlank(keySign)) {
			createResolveMap();
			doGetSign();
		}
		return keySign;
	}

	@Override
	protected void doGetSign() {
		StringBuilder sign = new StringBuilder();
		if (StringUtils.isNotBlank(payVer)) {
			sign.append("pay_ver").append(EQ).append(payVer);
		}
		if (StringUtils.isNotBlank(payType)) {
			sign.append(AND).append("pay_type").append(EQ).append(payType);
		}
		if (StringUtils.isNotBlank(serviceId)) {
			sign.append(AND).append("service_id").append(EQ).append(serviceId);
		}
		if (StringUtils.isNotBlank(getMerchantNo())) {
			sign.append(AND).append("merchant_no").append(EQ).append(getMerchantNo());
		}
		if (StringUtils.isNotBlank(terminalId)) {
			sign.append(AND).append("terminal_id").append(EQ).append(terminalId);
		}
		if (StringUtils.isNotBlank(terminalTrace)) {
			sign.append(AND).append("terminal_trace").append(EQ).append(terminalTrace);
		}
		if (StringUtils.isNotBlank(terminalTime)) {
			sign.append(AND).append("terminal_time").append(EQ).append(terminalTime);
		}
		if (StringUtils.isNotBlank(totalFee)) {
			sign.append(AND).append("total_fee").append(EQ).append(totalFee);
		}
		if (StringUtils.isNotBlank(accessToken)) {
			sign.append(AND).append("access_token").append(EQ).append(accessToken);
		}
		this.keySign = encrypt(sign.toString());
		reqCondition.put("key_sign", keySign);
	}
	
/*	
	public JSPayReqVO setPayVer(String payVer) {
		this.payVer = payVer;
		return this;
	}*/

	public JSPayReq setPayType(String payType) {
		this.payType = payType;
		return this;
	}

/*	public JSPayReqVO setServiceId(String serviceId) {
		this.serviceId = serviceId;
		return this;
	}*/

	public JSPayReq setTerminalTrace(String terminalTrace) {
		this.terminalTrace = terminalTrace;
		return this;
	}

	/*public JSPayReqVO setTerminalTime(String terminalTime) {
		this.terminalTime = terminalTime;
		return this;
	}*/

	public JSPayReq setTotalFee(String totalFee) {
		this.totalFee = totalFee;
		return this;
	}

	public JSPayReq setSubAppid(String subAppid) {
		this.subAppid = subAppid;
		return this;
	}

	public JSPayReq setOpenId(String openId) {
		this.openId = openId;
		return this;
	}

	public JSPayReq setOrderBody(String orderBody) {
		this.orderBody = orderBody;
		return this;
	}

	public JSPayReq setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
		return this;
	}

	public JSPayReq setAttach(String attach) {
		this.attach = attach;
		return this;
	}

	public JSPayReq setTradeId(Long tradeId) {
		this.tradeId = tradeId;
		return this;
	}

	public JSPayReq setPayVer(String payVer) {
		this.payVer = payVer;
		return this;
	}

	public JSPayReq setServiceId(String serviceId) {
		this.serviceId = serviceId;
		return this;
	}

	public JSPayReq setTerminalId(String terminalId) {
		this.terminalId = terminalId;
		return this;
	}

	public JSPayReq setTerminalTime(String terminalTime) {
		this.terminalTime = terminalTime;
		return this;
	}

	
}
