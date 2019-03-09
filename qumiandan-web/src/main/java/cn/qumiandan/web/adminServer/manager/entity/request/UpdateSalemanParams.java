package cn.qumiandan.web.adminServer.manager.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
/**
 * 修改业务员信息请求参数
 * @author yuleidian
 * @date 2019年1月22日
 */
@Data
public class UpdateSalemanParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "id不能为空")
	private Long id;
	
	/**
     * 费率码
     */
    private String rateCode;

    /**
     * 费率
     */
    @NotNull(message = "费率不能为空")
    private BigDecimal rate;
    
	/** 店铺管理员备注名 */
	private String remarkName;
    
    /**
     * 更新时间
     */
    private Date updateDate = new Date();
    
    /**
     * 更新人id
     */
    private Long updateId;
    
    //========================银行卡信息================================
    
    
    /** 银行id*/
    @NotNull(message = "银行支行编号不能为空")
	private Long bankId;
    
    /** 银行卡卡号*/
    @NotNull(message = "银行卡号不能为空")
    @Range(min = 11, max = 25, message = "请输入正确的银行卡号")
	private String bankCardNo;
	
	/** 银行卡持卡人姓名*/
    @NotNull(message = "银行卡持卡人姓名不能为空")
	private String bankCardHolder;
	
	/** 开户行手机号*/
    @Range(min = 11, max = 15, message = "请输入正确的手机号")
	private String bankMobile;
	
    /**
     * 卡类型：1对公；2：对私
     */
	@NotNull(message = "银行卡对公对私类型不能为空")
    private Byte cardType;
	
    /**
     * 账户类型
     */
    private Byte accountType;
}
