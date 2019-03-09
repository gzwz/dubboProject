package cn.qumiandan.sellerIndexData.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 店铺分润实体
 * @author W
 *
 */
@Data
@TableName("qmd_shop_profit")
public class ShopProfit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分润表 id
	 */
	@TableId(value="id")
	private Long id;
	/**
	 * 店铺id
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
