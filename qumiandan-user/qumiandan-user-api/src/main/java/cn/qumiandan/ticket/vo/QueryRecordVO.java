package cn.qumiandan.ticket.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 查询转让记录参数
 * @author lrj
 *
 */
@Data
public class QueryRecordVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 资格券的id
	 */
	private String ticketId;
	
	/**
	 * 上个用户id
	 */
	private Long fromUserId;
	
	/**
	 * 上个用户名
	 */
	private String fromUserName;
	
	/**
	 * 上个用户名
	 */
	private String fromName;
	
	/**
	 * 上个用户类型
	 */
	private Byte fromUserType;
	
	/**
	 * 当前用户id
	 */
	private Long currentUserId;
	/**
	 * 当前用户手机号
	 */
	private String currentUserName;
	
	/**
	 * 当前用户名
	 */
	private String currentName;
	/**
	 * 当前用户id
	 */
	private Byte currentType;
	
	/**
	 * 交易时间
	 */
	private Date createDate;
}
