package cn.qumiandan.payaccount.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 支付账户管理关联信息VO
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:48:14
 */
@Data
public class PayAccountManagerVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号*/
	private Long id;
	
	/** 用户id*/
	private Long userId;
	
	/** 账户信息*/
	private Long accountId;
	
	/** 管理员类型()*/
	private Byte managerType;
	
	/** 创建时间*/
	private Date createDate;
}
