package cn.qumiandan.salemandata.mapper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SalemanDataMapper {

	/**
	 * 业务员端今日利润
	 * @param userId
	 * @return
	 */
	BigDecimal todayProfit(@Param("userId")Long userId ,@Param("startTime")Date startTime , @Param("endTime")Date endTime);
}
