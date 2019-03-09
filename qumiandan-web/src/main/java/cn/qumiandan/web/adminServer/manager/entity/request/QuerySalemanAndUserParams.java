package cn.qumiandan.web.adminServer.manager.entity.request;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import lombok.Data;
/**
 * 总后台查询业务员参数
 * @author lrj
 *
 */
@Data
public class QuerySalemanAndUserParams implements Serializable {


	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/** 省code */
	@Pattern(regexp="^\\d{6}$",message="省编码格式不正确")
    private String proCode;
    
    /** 市code */
	@Pattern(regexp="^\\d{6}$",message="市编码格式不正确")
    private String cityCode;

    /** 县code*/	
	@Pattern(regexp="^\\d{6}$",message="县编码格式不正确")
    private String countryCode;
    
    /** 乡code*/
	@Pattern(regexp="^\\d{9}$",message="乡编码格式不正确")
    private String townCode;
	
    /**
     * 类型（3：省代理；2：市代理；1：业务员）
     */
    private Byte type;
    
    private String inputInfo;//模糊查询条件

    /**
     * 性别；1男；2女；0未知
     */
    private Integer sex;
	
	/**
	 * 页码
	 */
	private Integer pageNum;
	
	/**
	 * 页面大小
	 */
	private Integer pageSize;
	
	public Integer getPageNum() {
		return this.pageNum == null ? 1 : this.pageNum;
	}

	public Integer getPageSize() {
		return this.pageSize == null ? 10 : this.pageSize;
	}

	
}
