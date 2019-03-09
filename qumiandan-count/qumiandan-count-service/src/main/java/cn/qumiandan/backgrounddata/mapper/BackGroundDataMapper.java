package cn.qumiandan.backgrounddata.mapper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface BackGroundDataMapper {

	/**
	 * 用户数量
	 */
	Integer getUserNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 店铺数量
	 */
	Integer getShopNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 业务员数量
	 * 
	 */
	Integer getSalemanNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 代理数量
	 */
	Integer getAgentNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 服务费
	 */
	BigDecimal getServiceMoney(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 实收金额
	 */
	BigDecimal getActualMoney(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 支付笔数
	 */
	Integer getPayNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime );

	/**
	 * 利润
	 */
	BigDecimal getProfit(@Param("startTime") Date startTime,@Param("endTime") Date endTime );
}
