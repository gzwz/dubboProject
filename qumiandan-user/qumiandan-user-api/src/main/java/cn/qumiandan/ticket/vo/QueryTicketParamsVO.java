package cn.qumiandan.ticket.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 总后台查询资格券参数
 * @author lrj
 *
 */
@Data
public class QueryTicketParamsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/** 归属用户id */
	private String userName;
	/**
	 * 店铺id
	 */
	private Long shopId;
	
	/** 状态（1.未消费，2.消费中；3已消费）*/
	private List<Byte> statusList;
}
