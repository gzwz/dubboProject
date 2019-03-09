package cn.qumiandan.analyseData.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.analyseData.api.IAnalyseDataService;
import cn.qumiandan.analyseData.mapper.AnalyseDataMapper;
import cn.qumiandan.utils.DateUtil;
@Component
@Service(interfaceClass=IAnalyseDataService.class)
public class AnalyserDataServiceImpl implements IAnalyseDataService{
	@Autowired
	private AnalyseDataMapper analyseDataMapper;
	
	@Override
	public BigDecimal actualAmount(Date date) {	
		Date startTime = DateUtil.getDayStart(date);
		Date nextday = DateUtil.addDay(date, 1);
		Date endTime = DateUtil.getDayStart(nextday);
		BigDecimal amount = analyseDataMapper.actualAmount(startTime, endTime);
		
		return amount;
	}

	@Override
	public Long totalPayNum() {
		
		Long num = analyseDataMapper.totalPayNum();
		return num;
	}

}
