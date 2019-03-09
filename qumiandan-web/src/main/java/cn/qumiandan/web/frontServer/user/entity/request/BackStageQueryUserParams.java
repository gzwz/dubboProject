package cn.qumiandan.web.frontServer.user.entity.request;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 总后台查询用户参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class BackStageQueryUserParams extends PageInfoParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 模糊查询条件
	 */
	private String inputInfo;
	
	/**
	 * 省code
	 */
	private String proCode;
	
	/**
	 * 市code
	 */
	private String cityCode;
	
	/**
	 * 区code
	 */
	private String countryCode;
	
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
	
	private Byte status;

}
