package cn.qumiandan.saleman.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * 总后台查询业务员信息
 * @author lrj
 *
 */
@Data
public class SalemanAndUserVO implements Serializable{

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
    
    /** 省code */
    private String proCode;
    
    /**
     * 省份名字
     */
    private String proName;
    
    /** 市code */
    private String cityCode;
    
    /**
     * 城市名字
     */
    private String cityName;

    /**
     * 县名
     */
    private String countryName;
    
    /** 县code*/
    private String countryCode;
    
    /** 乡code*/
    private String townCode;
    
    /**
     * 乡镇名字
     */
    private String townName;
    
//    /** 省级userId*/
//    private Long proUserId;
//    
//    /** 市级userId*/
//    private Long cityUserId;
    
    /**
     * 类型（1：业务员;2:区代理；3：市代理；4：省代理；）
     */
    private Byte type;
    
    /**
     * 备注名
     */
    private String remarkName;
    
    /**
     * 类型集合（1：业务员;2:区代理；3：市代理；4：省代理；）
     */
    private List<Byte> typeList;

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
     * 账号
     */
    private String userName;

    /**
     * 名字
     */
    private String name;

    /**
     * 资格券数量
     */
    private Integer ticketNum;
    
    /**
     * 昵称
     */
    private String nickName;
    
    /**
     * 头像
     */
    private String portrait;
    
    /**
     * 性别；1男；2女；0未知
     */
    private Integer sex;
   
}
