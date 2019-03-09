package cn.qumiandan.supportpayment.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 支付参数传输类
 * @author lrj
 *
 */
@Data
public class SupportPaymentVO implements Serializable{

	 private Long id;

	    /**
	     * 扫呗支付码
	     */
	    private String code;

	    /**
	     * 支付方式
	     */
	    private String name;

	    /**
	     * 支付方式logo
	     */
	    private String logoUrl;

	    /**
	     * 状态：1正常；0删除
	     */
	    private Byte status;

	    private static final long serialVersionUID = 1L;
}
