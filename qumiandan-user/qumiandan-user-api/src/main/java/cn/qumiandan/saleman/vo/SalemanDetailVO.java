package cn.qumiandan.saleman.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 业务员详情返回参数类
 * @author lrj
 *
 */
@Data
public class SalemanDetailVO implements Serializable{


	private static final long serialVersionUID = 1L;
	private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色用户关联id
     */
    private Long userRoleId;

    /**
     * 费率码
     */
    private String rateCode;

    /**
     * 费率
     */
    private BigDecimal rate;
    
    /**
     * 父级id
     */
    private Long parentId;
    
    /**
     * 该业务员所属区域（后期 根据区域限制创建店铺）
     */
//    private String areaCode;
    
    /** 省code */
    private String proCode;
    
    /** 市code */
    private String cityCode;

    /** 县code*/
    private String countryCode;
    
    /** 乡code*/
    private String townCode;
    
//    /** 省级userId*/
//    private Long proUserId;
//    
//    /** 市级userId*/
//    private Long cityUserId;
    
   /* private Long countryUserId;
    
    private Long townUserId;*/
    
    /**
     * 类型（1：业务员;2:区代理；3：市代理；4：省代理；）
     */
    private Byte type;
    
    /**
     * 备注名
     */
    private String remarkName;
    

    /**
     * 状态(1:正常；0：删除)
     */
    private Byte status;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 更新人
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
     * 店铺数量
     */
    private Integer shopNum;
    
    /**
     * 可用核销券数量
     */
    private Integer ticketNum;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 可提现金额
     */
    private BigDecimal withdrawAmount; 
}
