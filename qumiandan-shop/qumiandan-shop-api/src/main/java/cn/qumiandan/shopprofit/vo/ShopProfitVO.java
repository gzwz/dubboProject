package cn.qumiandan.shopprofit.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ShopProfitVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
	/**
     * 主键
     */
    private Long id;

    /**
     * 店铺编号
     */
    private Long shopId;

    /**
     * 扫呗费率code,与支付模块sb_rate_code相关
     */
    private String sbRateCode;
    
    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 是否使用平台行业默认费率(1：是；0：否,使用自定义费率
     */
    private Byte useDefaultFeeFlag;

    /**
     * 状态(1:正常；0：删除)
     */
    private Byte status;

}
