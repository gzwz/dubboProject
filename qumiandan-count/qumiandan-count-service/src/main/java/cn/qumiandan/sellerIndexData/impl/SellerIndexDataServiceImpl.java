package cn.qumiandan.sellerIndexData.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.sellerIndexData.api.ISellerIndexDataService;
import cn.qumiandan.sellerIndexData.mapper.SellerIndexDataMapper;
import cn.qumiandan.sellerIndexData.vo.CacuSellerIndexDataVO;
import cn.qumiandan.sellerIndexData.vo.SellerIndexDataVO;
import cn.qumiandan.user.api.IShopUserService;
import cn.qumiandan.utils.DateUtil;
import cn.qumiandan.utils.ObjectUtils;
/**
 * 商家端统计
 * @author lrj
 *
 */
@Component
@Service(interfaceClass=ISellerIndexDataService.class)
public class SellerIndexDataServiceImpl implements ISellerIndexDataService{
	@Autowired
	private SellerIndexDataMapper sellerIndexDataMapper;

	@Reference
	private IShopUserService shopUserService;
	
	/**
	 * 商家端首页统计：交易额、预估利润
	 */
	@Override
	public SellerIndexDataVO getSellerIndexData(Long userId  ,Byte timeStatus ) {
		List<Long> shopIdList = shopUserService.getShopIdListByUserId(userId);
		if(ObjectUtils.isEmpty(shopIdList)) {
			return new SellerIndexDataVO();
		}
		Date now = new Date();
		Date startDate ;
		Date endDate ;
		switch (timeStatus) {
		case 0:
			//近7天（过去6天加当前时间）
			endDate = DateUtil.getDayBeginTime(now);
			startDate = DateUtil.addDay(endDate, -6);
			endDate = now;
			break;
		case 1:
			//当天
			startDate = DateUtil.getDayBeginTime(now);
			endDate =  now;
			break;
		case 2:
			//昨天
			endDate = DateUtil.getDayBeginTime(now);
			startDate = DateUtil.addDay(endDate, -1);
			break;
		default:
			throw new QmdException("时间段参数错误");
		}
		CacuSellerIndexDataVO cacuSellerIndexDataVO = sellerIndexDataMapper.getSellerIndexData(shopIdList, startDate, endDate);
		SellerIndexDataVO sellerIndexData = new SellerIndexDataVO();
		if(cacuSellerIndexDataVO == null) {
			return sellerIndexData;
		}
		BigDecimal zeroValue = new BigDecimal(0);
		BigDecimal tradeAmount = cacuSellerIndexDataVO.getTradeAmount() != null ? cacuSellerIndexDataVO.getTradeAmount() : zeroValue;
		BigDecimal actualMoney = cacuSellerIndexDataVO.getActualMoney() != null ?cacuSellerIndexDataVO.getActualMoney() : zeroValue ;
		BigDecimal allCost = cacuSellerIndexDataVO.getAllCost() != null ?cacuSellerIndexDataVO.getAllCost() : zeroValue ;
		sellerIndexData.setTradeAmount(tradeAmount);
		sellerIndexData.setFutureAmount(actualMoney.subtract(allCost));
		return sellerIndexData;
	}

}
