package cn.qumiandan.payaccount.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 因业务需求   账户主体关联两个字段 user_id shop_id
 * 
 * 店铺自己拥有一个账户  user_id存入 店铺管理员id  shopId存入店铺id
 * 
 * 业务创建账户时 只存入user_id
 * 支付账户
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午10:22:16
 */
@Data
@TableName("qmd_pay_account")
public class PayAccount implements Serializable {

	private static final long serialVersionUID = -8624031787100881932L;

	/** 编号*/
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 支付账户*/
	private String account;
	
	/** 用户id*/
	private Long userId;
	
	/** 店铺id*/
	private Long shopId;
	
	/**
	 * 账户名
	 */
	private String name;
	
	/** 账户累计总金额*/
	private BigDecimal balance;
	
	/** 账户余额*/
	private BigDecimal accountBalance;
	
	/** 可提现金额*/
	private BigDecimal settBalance;
	
	/** 冻结金额*/
	private BigDecimal unbalance;
	
	/** 保证金*/
	private BigDecimal securityMoney;
	
	/** 是否可提现（1.可以，0.不可以）*/
	private Byte withdrawStatus;
	
	/**
	 * 类型：1业务员；2市代理；3省代理；4店铺
	 */
	private Byte type;
	
	/** 账户状态（0.冻结,1.正常）*/
	private Byte status;
	
	/** 创建人*/
	private Long createId;
	
	/** 更新人*/
	private Long updateId;
	
	/** 创建时间*/
	private Date createDate;
	
	/** 更新时间*/
	private Date updateDate;
}
