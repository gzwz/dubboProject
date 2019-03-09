package cn.qumiandan.sellerIndexData.api;

import cn.qumiandan.sellerIndexData.vo.SellerIndexDataVO;

public interface ISellerIndexDataService {
	/**
	 * 根据店铺id获取指定日期的交易金额
	 * @param shopId
	 * @param time
	 * @return
	 *//*
	BigDecimal getTradeAmount(Long userId,Byte dateStatus);
	
	*//**
	 * 获取预估金额
	 * @param shopId
	 * @param time
	 * @return
	 *//*
	BigDecimal getFutureAmount(Long userId,Byte dateStatus);*/
	
	/**
	 * 商家端交易金额、预估金额
	 * @param userId
	 * @param dateStatus
	 * @return
	 */
	SellerIndexDataVO getSellerIndexData(Long userId ,Byte dateStatus );
}
