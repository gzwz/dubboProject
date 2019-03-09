package cn.qumiandan.trade.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * 查询账户流水参数类 
 * @author lrj
 *
 */
@Data
public class QueryTradeDetailParamsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 账户id
	 */
	private Long accountId;
	/**
	 * 账户id集合
	 */
	private List<Long> accountIds;
	
	private List<Byte> typeList;
	
	private List<Byte> statusList;
	
	/** 交易类型: 1.充值，2:消费，3.（转入）收到转账，4.（转出），5.提现 */
	private List<Byte> 	tradeTypeList;	
	
	/**
	 * 生成流水时间查询条件：开始时间
	 */
	private Date startCreateDate;
	
	/**
	 * 生成流水时间查询条件：结束时间
	 */
	private Date endCreateDate;
	
	private Integer pageNum;
	
	private Integer pageSize;
	
    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
