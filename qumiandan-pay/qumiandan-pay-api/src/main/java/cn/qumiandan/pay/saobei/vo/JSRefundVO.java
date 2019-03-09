package cn.qumiandan.pay.saobei.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 申请退款
 * @author yuleidian
 * @version 创建时间：2018年12月29日 下午2:27:34
 */
@Data
public class JSRefundVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 申请退款id*/
	private Long refundId;
	
}
