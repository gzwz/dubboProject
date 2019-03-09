package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 更新店铺分润信息
 * @author lrj
 *
 */
@Data
public class UpdateShopProfitParams implements Serializable{

 
	private static final long serialVersionUID = 1L;

	/**
     * 店铺编号
     */
	@NotNull(message="店铺id不能为空")
    private Long shopId;

    /**
     * 扫呗费率code,与支付模块sb_rate_code相关
     */
//	@NotBlank(message="扫呗费率code不能为空")
    private String sbRateCode;
    
    /**
     * 费率
     */
    @NotNull(message="费率不能为空")
    private BigDecimal rate;

    /**
     * 是否使用平台行业默认费率(1：是；0：否,使用自定义费率
     */
    private Byte useDefaultFeeFlag;

}
