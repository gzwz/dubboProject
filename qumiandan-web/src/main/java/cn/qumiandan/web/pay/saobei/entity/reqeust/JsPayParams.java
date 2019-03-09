package cn.qumiandan.web.pay.saobei.entity.reqeust;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonValue;

import cn.qumiandan.pay.enums.PayTypeEnum;
import lombok.Data;

/**
 * 公众号预支付（统一下单）请求参数
 * @author yuleidian
 * @version 创建时间：2018年12月18日 上午11:36:19
 */
@Data
public class JsPayParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商户id 在扫呗1.4.1接口调用时返回给前端的*/
	@NotNull(message = "必须传入商户id")
	private Long merchantId;
	
	/** 订单编号*/
	@NotBlank(message = "必须传入订单编号")
	private String orderId;
	
	/** 支付类型*/
	@NotNull(message = "必须传入支持类型")
	@JsonValue
	private PayTypeEnum payType;
	
	/** 公众号appid*/
	private String appId;
	
	/** 公众号openId*/
	private String openId;
	
	/** 创建人*/
	private Long createId;
	
}
