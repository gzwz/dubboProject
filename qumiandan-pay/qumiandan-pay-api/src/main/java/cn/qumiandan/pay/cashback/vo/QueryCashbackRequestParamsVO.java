package cn.qumiandan.pay.cashback.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * 总后台查询条件
 * @author lrj
 *
 */
@Data
public class QueryCashbackRequestParamsVO implements Serializable{

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

    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
