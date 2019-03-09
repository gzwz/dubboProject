package cn.qumiandan.pay.saobei.vo;

import java.io.Serializable;

import cn.qumiandan.pay.enums.PayTypeEnum;
import lombok.Data;

/**
 * 公众号预支付（统一下单）VO
 * @author yuleidian
 * @version 创建时间：2018年12月18日 上午11:36:19
 */
@Data
public class JSPayVO implements Serializable {

	private static final long serialVersionUID = 4190805771159861628L;

	/** 商户id 在扫呗1.4.1接口调用时返回给前端的*/
	private Long merchantId;
	
	/** 订单编号*/
	private String orderId;
	
	/** 游戏订单编号*/
	private String gameOrderId;
	
	/** 支付类型*/
	private PayTypeEnum payType;
	
	/** 公众号appid*/
	private String appId;
	
	/** 公众号openId*/
	private String openId;
	
	/** 创建人*/
	private Long createId;
	
}
