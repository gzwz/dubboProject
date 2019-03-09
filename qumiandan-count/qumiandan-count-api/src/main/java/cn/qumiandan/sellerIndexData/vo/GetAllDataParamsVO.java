package cn.qumiandan.sellerIndexData.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * 总后台分析查询条件参数
 * @author lrj
 *
 */
@Data
public class GetAllDataParamsVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id集合
	 */
	private List<Long> shopIds;
	
	/**
	 * 账户id集合
	 */
	private List<Long> accountIds;
	
	/**
	 * 开始时间
	 */
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	private Date endDate;
	
}
