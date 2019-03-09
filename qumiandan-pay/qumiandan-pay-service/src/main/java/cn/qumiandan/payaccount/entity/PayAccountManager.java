package cn.qumiandan.payaccount.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 支付账户管理关联信息
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:48:14
 */
@Data
@TableName("pay_account_manager")
public class PayAccountManager implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号*/
	@TableId
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
