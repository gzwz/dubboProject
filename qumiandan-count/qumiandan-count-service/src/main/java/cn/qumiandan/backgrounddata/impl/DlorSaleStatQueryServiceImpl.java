package cn.qumiandan.backgrounddata.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.backgrounddata.api.IDlorSaleStatQueryService;
import cn.qumiandan.backgrounddata.mapper.DlorSaleStatQueryMapper;
import cn.qumiandan.backgrounddata.vo.DlorSaleStatQueryVO2;
import cn.qumiandan.utils.DateUtil;
/**
 * 代理和业务员利润
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IDlorSaleStatQueryService.class)
public class DlorSaleStatQueryServiceImpl implements IDlorSaleStatQueryService{
	@Autowired
	private DlorSaleStatQueryMapper dlorSaleStatQueryMapper;
	
	private Date getYesterdayStartTime() {
		Date date = DateUtil.getDate(-1);
		Date dayBeginTime = DateUtil.getDayBeginTime(date);
		return dayBeginTime;
	}
	private Date getYesterdayEndTime() {
		Date date = DateUtil.getDate(0);
		Date dayBeginTime = DateUtil.getDayBeginTime(date);
		return dayBeginTime;
	}
	
	/**
	 * 查询代理和业务员利润
	 */
	@Override
	public List<DlorSaleStatQueryVO2> queryDlorSaleStat() {
		List<DlorSaleStatQueryVO2> queryDlorSaleStat = dlorSaleStatQueryMapper.queryDlorSaleStat(getYesterdayStartTime(), getYesterdayEndTime());
		return queryDlorSaleStat;
	}

}
