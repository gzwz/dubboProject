package cn.qumiandan.saleman.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/**
 * 修改业务员vo
 * @author yuleidian
 * @date 2019年1月21日
 */
@Data
public class UpdateSalmanVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;
	
	private Byte status;
	
	/**
     * 费率码
     */
    private String rateCode;

    /**
     * 费率
     */
    private BigDecimal rate;
    
    /**
     * 备注名
     */
    private String remarkName;
    
    /**
     * 更新时间
     */
    private Date updateDate;
    
    /**
     * 更新人id
     */
    private Long updateId;
    
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
