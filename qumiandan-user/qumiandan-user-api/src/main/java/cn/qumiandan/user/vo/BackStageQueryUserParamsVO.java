package cn.qumiandan.user.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 总后台查询用户参数
 * @author lrj
 *
 */
@Data
public class BackStageQueryUserParamsVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 性别
	 */
	private Integer sex;
	
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
	private Date startCreateDate;
	
	/**
	 * 创建时间查询条件：结束时间
	 */
	private Date endCreateDate;
	
	private Byte status;
	
	private Integer pageNum;
	
	private Integer pageSize;
	
    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

}
