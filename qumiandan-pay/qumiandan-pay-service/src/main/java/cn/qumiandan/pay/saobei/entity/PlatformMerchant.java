package cn.qumiandan.pay.saobei.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 公司商户
 * @author yuleidian
 * @version 创建时间：2018年12月18日 下午2:01:06
 */
@Data
@TableName("qmd_platform_merchant")
public class PlatformMerchant implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号*/
	@TableId
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
