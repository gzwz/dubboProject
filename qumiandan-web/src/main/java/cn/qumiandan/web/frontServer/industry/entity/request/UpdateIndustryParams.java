package cn.qumiandan.web.frontServer.industry.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class UpdateIndustryParams implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
     * 编号
     */
	@NotNull(message="id不能为空")
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
     * 所属行业费率
     */
    private BigDecimal fee;
    
    /**
     * 费率code
     */
    private String rateCode;

    /**
     * 父级id(保留)
     */
    private Long parentId;

    /**
     * 结算周期(T+n)
     */
    private Integer setInterval;

    /**
     * 更新者
     */
    private Long updateId;

}
