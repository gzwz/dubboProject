package cn.qumiandan.pay.saobei.vo.response.pay;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信扫码支付获取 认证信息返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月8日 下午2:44:33
 */
@Getter
@Setter
public class WechatAuthopenIdResVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 微信公众号用户唯一标识，成功授权后取得，用于微信支付JSAPI接口统一下单*/
	private String url;
	
	/** 商户主表id*/
	private Long merchantId;
	
}
