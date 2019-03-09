package cn.qumiandan.web.adminServer.manager.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
/**
 * 市级代理添加业务员
 * @author lrj
 *
 */
@Data
public class CityAgentAddSalemanParams implements Serializable{

	  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		/**
	     * 用户名
	     */
		@NotBlank(message = "用户名不能为空")
	    private String userName;


	    /**
	     * 费率码
	     */
//		@NotBlank(message = "费率码不能为空")
	    private String rateCode;

	    /**
	     * 费率
	     */
		@NotNull(message = "费率不能为空")
	    private BigDecimal rate;
	    
	    /**
	     * 父级id
	     */
	    private Long parentId;
		
	    /** 省code */
		@NotBlank(message = "该业务员所属省编码不能为空")
	    @Pattern(regexp="^\\d{6}$",message="该业务员所属省编码格式不正确")
	    private String proCode;
	    
	    /** 市code */
		@NotBlank(message = "该业务员所属市编码不能为空")
	    @Pattern(regexp="^\\d{6}$",message="该业务员所属市编码格式不正确")
	    private String cityCode;

	    /** 县code*/
	    @Pattern(regexp="^\\d{6}$",message="该业务员所属县、区编码格式不正确")
	    private String countryCode;
	    
	    /** 乡code*/
	    private String townCode;
	    
	    /**
	     * 类型（3：省代理；2：市代理；1：业务员）
	     */
	    @NotNull(message = "类型不能为空")
	    private Byte type;
	    
	    /**
	     * 卡类型：1对公；2：对私
	     */
	    @NotNull(message = "卡类型s不能为空")
	    private Byte cardType;
	    
	    /**
	     * 创建人id
	     */
	    private Long createId;
	    
	    //========================银行卡信息================================
	    
	    /** 银行id*/
	    @NotNull(message = "银行id不能为空")
		private Long bankId;
	    
	    /** 银行卡卡号*/
	    @NotBlank(message = "银行卡卡号不能为空")
		private String bankCardNo;
		
		/** 银行卡持卡人姓名*/
	    @NotBlank(message = "银行卡持卡人姓名不能为空")
		private String bankCardHolder;
		
		/** 开户行手机号*/
	    @NotBlank(message = "开户行手机号不能为空")
		private String bankMobile;

}
