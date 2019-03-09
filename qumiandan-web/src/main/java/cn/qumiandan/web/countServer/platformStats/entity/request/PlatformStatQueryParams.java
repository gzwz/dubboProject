package cn.qumiandan.web.countServer.platformStats.entity.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PlatformStatQueryParams {

	 /**查询开始时间*/
	 @NotNull(message = "startTime 不能为空")
	 @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
	 private Date startTime;
	 
	 /**查询开始时间*/
	 @NotNull(message = "endTime 不能为空")
	 @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
	 private Date endTime;
	 
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
