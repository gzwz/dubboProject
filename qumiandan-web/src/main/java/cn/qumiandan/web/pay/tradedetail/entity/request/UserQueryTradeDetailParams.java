package cn.qumiandan.web.pay.tradedetail.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserQueryTradeDetailParams extends PageInfoParams implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * 类型集合
	 */
	private List<Byte> typeList;
	
	/**
	 * 状态集合
	 */
	private List<Byte> statusList;
	
	/** 交易类型: 1.充值，2:消费，3.（转入）收到转账，4.（转出），5.提现 */
	private List<Byte> 	tradeTypeList;
	
	/**
	 * 生成流水时间查询条件：开始时间
	 */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startCreateDate;
	
	/**
	 * 生成流水时间查询条件：结束时间
	 */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endCreateDate;
	
}
