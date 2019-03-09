package cn.qumiandan.counttask.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@Data
@TableName("qmd_platform_stats")
public class PlatformStat {

	 @TableId
	 private Long id; 
	 /** 店铺id*/
	 private Long  shopId; 
	 /** 省*/
	 private String proCode; 
	 /** 市*/
	 private String cityCode; 
	 /** 区*/
	 private String  countyCdoe;
	 /** 业务员用户id*/
	 private Long  salemanId;
	 /** 实收金额*/
	 private BigDecimal receivedAmount;
	 /** 商家利润*/
	 private BigDecimal merchantProfit; 
	 /** 平台利润*/
	 private BigDecimal platformProfit; 
	 /** 手续费支出*/
	 private BigDecimal serviceFee; 
	 /** 游戏支付金额*/
	 private BigDecimal gameAmount; 
	 /** 游戏中奖金额*/
	 private BigDecimal gameWinAmount; 
	 /** 提现金额*/
	 private BigDecimal withdrawAmount;
	 /** 创建时间*/
	 private Date createDate;
	 /** 统计的时间点 */
	 private Date countDate;
}