package cn.qumiandan.web.pay.withdrawcash.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 总后台查询提现记录
 * @author lrj
 *
 */
@Data
public class BackstageQueryWithdrawCashParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态集合（1. 审核中 2.未通过  3.等待打款  4.自动打款异常 5.打款完成  6.手动打款 ）
	 */
	private List<Byte> statusList;

	/**
	 * 模糊查询条件；可为账户名、收款人姓名、银行卡号
	 */
	private String inputInfo;
	
	/**
	 * 类型：1业务员；2市代理；3省代理；4店铺
	 */
	private Byte accountType;
	
	
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
