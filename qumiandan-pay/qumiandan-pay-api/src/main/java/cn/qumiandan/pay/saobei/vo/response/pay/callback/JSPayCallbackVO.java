package cn.qumiandan.pay.saobei.vo.response.pay.callback;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.vo.response.pay.BaseSaoBeiPayResVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 公众号支付回调请求参数VO
 * @author yuleidian
 * @version 创建时间：2018年12月10日 上午10:15:48
 */
@Getter
@Setter
public class JSPayCallbackVO extends BaseSaoBeiPayResVO {

	private static final long serialVersionUID = 1L;
	
	/** 请求类型，010微信，020 支付宝，060qq钱包，080京东钱包，090口碑，100翼支付*/
	@Expose
	@SerializedName("pay_type")
	private String payType;
	
	/** 付款方用户id，“微信openid”、“支付宝账户”、“qq号”等*/
	@SerializedName("user_id")
	private String userId;
	
	/** 商户名称*/
	@SerializedName("merchant_name")
	private String merchantName;	
	
	/** 商户号*/
	@SerializedName("merchant_no")
	private String merchantNo;
	
	/** 终端号*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;
	
	/** 终端流水号，此处传商户发起预支付或公众号支付时所传入的交易流水号*/
	@SerializedName("terminal_trace")
	private String terminalTrace;
	
	/** 终端交易时间，yyyyMMddHHmmss，全局统一时间格式（01时参与拼接）*/
	@SerializedName("terminalTime")
	private String terminalTime;
	
	/** 当前支付终端流水号，与pay_time同时传递，返回时不参与签名*/
	@Expose
	@SerializedName("payTrace")
	private String payTrace;
	
	/** 当前支付终端交易时间，yyyyMMddHHmmss，全局统一时间格式，与pay_trace同时传递*/
	@Expose
	@SerializedName("pay_time")
	private String payTime;
	
	/** 金额，单位分*/
	@Expose
	@SerializedName("total_fee")
	private String totalFee;
	
	/** 支付完成时间，yyyyMMddHHmmss，全局统一时间格式*/
	@Expose
	@SerializedName("end_time")
	private String endTime;
	/** 利楚唯一订单号*/
	@Expose
	@SerializedName("out_trade_no")
	private String outTradeNo;
	
	/** 通道订单号，微信订单号、支付宝订单号等*/
	@Expose
	@SerializedName("channel_trade_no")
	private String channelTradeNo;
	
	/** 附加数据，原样返回*/
	@Expose
	@SerializedName("attach")
	private String attach;
	
	/** 口碑实收金额，pay_type为090时必填*/
	@Expose
	@SerializedName("receipt_fee")
	private String receiptFee;
	
	/** 签名字符串,拼装所有必传参数+令牌，32位md5加密转换*/
	@Expose
	@SerializedName("key_sign")
	private String keySign;
	
	/** 根据参数生成签名   要与keySign做对比*/
	private String sign;
	
	/** 验签时使用*/
	private String accessToken;
	
	/**
	 * 适应扫呗的 文档序
	 * 验签使用
	 * @return
	 */
	public String sign(String accessToken) {
		if (StringUtils.isNotBlank(sign)) {
			return this.sign;
		}
		this.accessToken = accessToken;
		StringBuilder sign = new StringBuilder();
		if (StringUtils.isNotBlank(getReturnCode())) {
			sign.append("return_code").append(EQ).append(getReturnCode());
		}
		if (StringUtils.isNotBlank(getReturnMsg())) {
			sign.append(AND).append("return_msg").append(EQ).append(getReturnMsg());
		}
		if (StringUtils.isNotBlank(getReturnCode())) {
			sign.append(AND).append("result_code").append(EQ).append(getResultCode());
		}
		if (StringUtils.isNotBlank(getPayType())) {
			sign.append(AND).append("pay_type").append(EQ).append(getPayType());
		}
		if (StringUtils.isNotBlank(getUserId())) {
			sign.append(AND).append("user_id").append(EQ).append(getUserId());
		}
		if (StringUtils.isNotBlank(getMerchantName())) {
			sign.append(AND).append("merchant_name").append(EQ).append(getMerchantName());
		}
		if (StringUtils.isNotBlank(getMerchantNo())) {
			sign.append(AND).append("merchant_no").append(EQ).append(getMerchantNo());
		}
		if (StringUtils.isNotBlank(getTerminalId())) {
			sign.append(AND).append("terminal_id").append(EQ).append(getTerminalId());
		}
		if (StringUtils.isNotBlank(getTerminalTrace())) {
			sign.append(AND).append("terminal_trace").append(EQ).append(getTerminalTrace());
		}
		if (StringUtils.isNotBlank(getTerminalTime())) {
			sign.append(AND).append("terminal_time").append(EQ).append(getTerminalTime());
		}
		if (StringUtils.isNotBlank(getTotalFee())) {
			sign.append(AND).append("total_fee").append(EQ).append(getTotalFee());
		}
		if (StringUtils.isNotBlank(getEndTime())) {
			sign.append(AND).append("end_time").append(EQ).append(getEndTime());
		}
		if (StringUtils.isNotBlank(getOutTradeNo())) {
			sign.append(AND).append("out_trade_no").append(EQ).append(getOutTradeNo());
		}
		if (StringUtils.isNotBlank(getChannelTradeNo())) {
			sign.append(AND).append("channel_trade_no").append(EQ).append(getChannelTradeNo());
		}
		if (StringUtils.isNotBlank(getAttach())) {
			sign.append(AND).append("attach").append(EQ).append(getAttach());
		}
		if (StringUtils.isNotBlank(getAccessToken())) {
			sign.append(AND).append("access_token").append(EQ).append(getAccessToken());
		}
		this.sign = encrypt(sign.toString());
		return this.sign;
	}
	
	/**
	 * 获取支付完成时间
	 * @return
	 */
	public Date getPayedEndTime() {
		if (StringUtils.isNotBlank(endTime)) {
			try {
				return DateUtils.parseDate(endTime, "yyyyMMddHHmmss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
