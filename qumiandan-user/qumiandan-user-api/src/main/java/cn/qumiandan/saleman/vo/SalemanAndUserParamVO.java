package cn.qumiandan.saleman.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 总后台查询业务员参数
 * @author lrj
 *
 */
@Data
public class SalemanAndUserParamVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    /**
     * 用户id
     */
    private Long userId;
    
    /** 省code */
    private String proCode;
    
    /** 市code */
    private String cityCode;

    /** 县code*/
    private String countryCode;
    
    /** 乡code*/
    private String townCode;
    


    
    /**
     * 类型（1：业务员;2:区代理；3：市代理；4：省代理；）
     */
    private Byte type;
    
    /**
     * 类型集合（1：业务员;2:区代理；3：市代理；4：省代理；）
     */
    private List<Byte> typeList;

	 /**
     * 账号
     */
    private String userName;

    /**
     * 名字
     */
    private String name;
    
    private String inputInfo;//模糊查询条件

    /**
     * 性别；1男；2女；0未知
     */
    private Integer sex;
    
    private Integer pageNum;
    
    private Integer pageSize;
	
}
