package cn.qumiandan.backgrounddata.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.backgrounddata.api.IBackGroundDataService;
import cn.qumiandan.backgrounddata.mapper.BackGroundDataMapper;
import cn.qumiandan.backgrounddata.vo.MoneyStatisticsVO;
import cn.qumiandan.backgrounddata.vo.NumberStatisticsVO;
/**
 * 总后台统计实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass=IBackGroundDataService.class)
public class BackGroundDataServiceImpl implements IBackGroundDataService{

	@Autowired
	private BackGroundDataMapper backGroundDataMapper;
	/**
	 * 查询用户、店铺、业务员、代理数量参数
	 * @param paramsVO
	 * @return
	 */
	@Override
	public NumberStatisticsVO getNumberStatistics(Date startTime,Date endTime ) {
		NumberStatisticsVO numsVO = new NumberStatisticsVO();
		//用户数量
		Integer userNum = backGroundDataMapper.getUserNum( startTime,endTime);
		numsVO.setUserNum(userNum != null ? userNum :0);
		//店铺数量
		Integer shopNum = backGroundDataMapper.getShopNum( startTime,endTime);
		numsVO.setShopNum(shopNum != null ? shopNum :0);
		//业务员数量
		Integer salemanNum = backGroundDataMapper.getSalemanNum(startTime,endTime);
		numsVO.setSalemanNum(salemanNum != null ? salemanNum :0);
		 //代理数量
		Integer agentNum = backGroundDataMapper.getAgentNum(startTime,endTime);
		numsVO.setAgentNum(agentNum != null ? agentNum :0);
		return numsVO;
	}

	/**
	 * 查询服务费、实收金额、支付笔数、利润
	 * @param paramsVO
	 * @return
	 */
	@Override
	public MoneyStatisticsVO getMoneyStatistics(Date startTime,Date endTime ) {
		MoneyStatisticsVO numsVO = new MoneyStatisticsVO();
		BigDecimal zeroValue = new BigDecimal(0);
		// 服务费
		BigDecimal serviceMoney = backGroundDataMapper.getServiceMoney(startTime,endTime);
		numsVO.setServiceMoney(serviceMoney != null ? serviceMoney : zeroValue);
		//实收金额
		BigDecimal actualMoney = backGroundDataMapper.getActualMoney( startTime,endTime);
		numsVO.setActualMoney(actualMoney != null ? actualMoney : zeroValue);
		//支付笔数
		Integer payNum = backGroundDataMapper.getPayNum( startTime,endTime);
		numsVO.setPayNum(payNum != null ? payNum : 0);
		//利润
		BigDecimal profit = backGroundDataMapper.getProfit( startTime,endTime);
		numsVO.setProfit(profit != null ? profit : zeroValue);
		return numsVO;
	}

}
