package cn.qumiandan.sellerIndexData.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.sellerIndexData.vo.CacuSellerIndexDataVO;
import cn.qumiandan.sellerIndexData.vo.SellerIndexDataVO;

@Mapper
public interface SellerIndexDataMapper extends BaseMapper<SellerIndexDataVO>{
/*	*//**
	 * 根据店铺id获取总收入金额
	 * @param shopId
	 * @param time
	 * @return
	 *//*
	BigDecimal getTradeAmountByShopId(@Param(value="shopId")Long shopId,@Param(value="startTime")Date startTime,@Param(value="endTime")Date endTime);
	
	
	 * List<Long> getOrderIdByShopId(@Param(value="shopId")Long
	 * shopId,@Param(value="startTime")Date startTime,@Param(value="endTime")Date
	 * endTime);
	 
	*//**
	 * 获取预估金额
	 * @param shopId
	 * @param startTime
	 * @param endTime
	 * @return
	 *//*
	BigDecimal getFutureAmount(@Param(value="shopId")Long shopId,@Param(value="startTime")Date startTime,@Param(value="endTime")Date endTime);
*/
	/**
	 * 商家端交易金额、预估金额
	 * @param shopIdList
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	CacuSellerIndexDataVO getSellerIndexData(@Param(value="shopIdList")List<Long> shopIdList ,@Param(value="startTime")Date startTime,@Param(value="endTime")Date endTime );

}
