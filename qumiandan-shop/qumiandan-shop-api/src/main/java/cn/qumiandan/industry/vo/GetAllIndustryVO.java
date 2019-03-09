package cn.qumiandan.industry.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GetAllIndustryVO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    /**
     * 行业自定义id（使用扫呗的行业信息）
     */
    private Long sbIndustryId;

    /**
     * 行业名称
     */
    private String name;

    /**
     * 行业分3级
     */
    private Integer level;
    
    /**
     * 费率code
     */
    private String rateCode;

    /**
     * 所属行业费率
     */
    private BigDecimal fee;

    /**
     * 父级id(保留)
     */
    private Long parentId;

    /**
     * 结算周期(T+n)
     */
    private Integer setInterval;
    
   
    /**
     * 状态（1：正常；0：已删除）
     */
    private Byte status;

    /**
     * 创建者
     */
    private Long createId;

    /**
     * 更新者
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;
    
    /**
     * 行业分类集合
     */
    private List<Object> objectList;
    
    

}
