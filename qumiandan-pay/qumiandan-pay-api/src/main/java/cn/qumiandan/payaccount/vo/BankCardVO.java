package cn.qumiandan.payaccount.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 银行卡信息VO
 * @author yuleidian
 * @version 创建时间：2018年12月17日 上午11:09:31
 */
@Data
public class BankCardVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号*/
	private Long id;
	
	/** 支付账户id*/
	private Long accountId;
	
	/** 银行开户支行名称*/
	private String bankName;
	
	/** 银行卡卡号*/
	private String bankCardNo;
	
	/** 银行卡持卡人姓名*/
	private String bankCardHolder;
	
	/** 开户行手机号*/
	private String bankMobile;
	
	/** 使用次数，用作自动排序的依据*/
	private Integer useTimes;
	
	/** 排列次序*/
	private Integer sort;
	
	/** 银行卡类型*/
	private Byte cardType;
	
	/** 状态*/
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
