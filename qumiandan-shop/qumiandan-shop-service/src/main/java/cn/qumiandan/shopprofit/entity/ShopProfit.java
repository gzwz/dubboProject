package cn.qumiandan.shopprofit.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 店铺分润实体
 * @author lrj
 *
 */
@Data
@TableName(value = "qmd_shop_profit")
public class ShopProfit implements Serializable {
    /**
     * 主键
     */
	@TableId
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
     * 是否使用平台行业默认费率(1：是；2：否,使用自定义费率
     */
    private Byte useDefaultFeeFlag;

    /**
     * 状态(1:正常；0：删除)
     */
    private Byte status;
    
    

    private static final long serialVersionUID = 1L;

    
}