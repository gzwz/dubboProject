package cn.qumiandan.salemandata.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.salemandata.api.ISalemanDataService;
import cn.qumiandan.salemandata.mapper.SalemanDataMapper;
import cn.qumiandan.utils.DateUtil;

/**
 * 业务员端统计实现类
 * 
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = ISalemanDataService.class)
public class SalemanDataServiceImpl implements ISalemanDataService {

	@Autowired
	private SalemanDataMapper salemanDataMapper;

	/**
	 * 查询今日利润
	 */
	@Override
	public BigDecimal todayProfit(Long userId) {
		Date endTime = new Date();
		Date startDate = DateUtil.getDayBeginTime(endTime);
		BigDecimal todayProfit = salemanDataMapper.todayProfit(userId, startDate, endTime);
		return todayProfit != null ? todayProfit : new BigDecimal(0);
	}

}
