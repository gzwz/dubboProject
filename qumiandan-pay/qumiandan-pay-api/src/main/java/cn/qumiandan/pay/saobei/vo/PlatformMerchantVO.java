package cn.qumiandan.pay.saobei.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 平台商户vo
 * @author yuleidian
 * @version 创建时间：2018年12月18日 下午2:43:21
 */
@Data
public class PlatformMerchantVO implements Serializable {

	private static final long serialVersionUID = 5676091181231303063L;

	/** 编号*/
	private Long id;
	
	/** 商户名称*/
	private String name;
	
	/** 商户号*/
	private String merchantNo;
	
	/** 终端号*/
	private String terminalNo;
	
	/** token*/
	private String accessToken;
	
	/** 商户的账号*/
	private String account;
	
	/** 商户的密码*/
	private String password;
	
	/** 权重*/
	private Double weight;
	
	/** 1:启动 2:禁用*/
	private Byte status;
	
	/** 创建人*/
	private Long createId;
	
	/** 创建时间*/
	private Date createDate;
	
	/** 更新人*/
	private Long updateId;
	
	/** 更新时间*/
	private Date updateDate;
	
}
