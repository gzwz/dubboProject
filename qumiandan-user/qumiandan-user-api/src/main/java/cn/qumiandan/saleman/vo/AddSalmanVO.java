package cn.qumiandan.saleman.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 添加业务员参数
 * @author lrj
 *
 */
@Data
public class AddSalmanVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名（手机号）
	 */
	private String userName;
	
	private Long userId;
	
	private Byte status;
	
	private Date createDate;
	
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
     * 备注名
     */
    private String remarkName;
    

    
//    /** 省级userId*/
//    private Long proUserId;
//    
//    /** 市级userId*/
//    private Long cityUserId;

    /**
     * 创建人id
     */
    private Long createId;
    
    //========================银行卡信息================================
    
    
    /** 银行id*/
	private Long bankId;
    
    
    /** 银行卡卡号*/
	private String bankCardNo;
	
	/** 银行卡持卡人姓名*/
	private String bankCardHolder;
	
	/** 开户行手机号*/
	private String bankMobile;
	
    /**
     * 卡类型：1对公；2：对私
     */
    private Byte cardType;
    
    /**
     * 账户类型
     */
    private Byte accountType;
    
}
