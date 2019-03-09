package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AgentQueryWithdrawCashParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 账户id
	 */
	private  List<Long> accountIdList;
	
	/**
	 * 状态集合（1. 审核中 2.未通过  3.等待打款  4.自动打款异常 5.打款完成  6.手动打款 ）
	 */
	private List<Byte> statusList;
	
	/**
	 * 创建时间查询条件：开始时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startCreateDate;

	/**
	 * 创建时间查询条件：结束时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
