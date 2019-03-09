package cn.qumiandan.web.frontServer.industry.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class AddIndustryParams implements Serializable{

	
	private static final long serialVersionUID = 1L;

    /**
     * 行业自定义id（使用扫呗的行业信息）
     */
	private Long sbIndustryId;

    /**
     * 行业名称
     */
	@NotBlank(message="行业名称不能为空")
    private String name;

    /**
     * 行业分3级
     */
	@NotNull(message="行业级别不能为空")
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
     * 创建者
     */
    private Long createId;
    
    

}
