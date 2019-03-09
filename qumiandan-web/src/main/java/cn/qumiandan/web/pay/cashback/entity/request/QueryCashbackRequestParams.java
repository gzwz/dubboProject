package cn.qumiandan.web.pay.cashback.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.page.PageParams;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class QueryCashbackRequestParams extends PageParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 状态集合（1. 审核中 2.未通过  3.等待打款  4.自动打款异常 5.打款完成  6.手动打款 ）
	 */
	private List<Byte> statusList;
	
	/**
	 * 创建时间查询条件：开始时间
	 */
	private Date startCreateDate;

	/**
	 * 创建时间查询条件：结束时间
	 */
	private Date endCreateDate;
	
	private Integer pageNum;
	
    private Integer pageSize;
}
