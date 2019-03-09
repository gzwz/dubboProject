package cn.qumiandan.apply.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 查询开店申请参数
 * 
 * @author lrj
 *
 */
@Data
public class QueryApplyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 模糊查询条件
	 */
	private String inputInfo;
	/** 省code */
	private String proCode;
	/** 市code */
	private String cityCode;
	/** 区code */
	private String countryCode;
	/** 状态（0：未处理 1：已处理 ） */
	private Byte status;

	/**
	 * 类型
	 */
	private Byte type ;
	/**
	 * 创建时间查询条件：开始时间
	 */
	private Date startCreateDate;
	/**
	 * 创建时间查询条件：结束时间
	 */
	private Date endCreateDate;
	
	
	private Integer pageNum;
	
	private Integer PageSize;

}
