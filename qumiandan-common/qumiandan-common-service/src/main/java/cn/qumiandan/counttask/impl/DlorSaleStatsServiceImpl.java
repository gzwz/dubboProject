package cn.qumiandan.counttask.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.backgrounddata.api.IDlorSaleStatQueryService;
import cn.qumiandan.backgrounddata.vo.DlorSaleStatQueryVO2;
import cn.qumiandan.counttask.api.IDlorSaleStatsService;
import cn.qumiandan.counttask.entity.DlorSaleManStat;
import cn.qumiandan.counttask.mapper.DlorSaleStatMapper;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryResultVO;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryVO;
import cn.qumiandan.counttask.vo.DlorSaleStatVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.DateUtil;

@Component
@Service(interfaceClass = IDlorSaleStatsService.class)
public class DlorSaleStatsServiceImpl implements IDlorSaleStatsService {
	
	@Autowired
	private DlorSaleStatMapper mapper;
	
	@Reference
	private IDlorSaleStatQueryService dlorSaleStatQueryService;

	@Override
	public void taskQuery() {
		List<DlorSaleStatQueryVO2> list = dlorSaleStatQueryService.queryDlorSaleStat();
		if (list != null) {
			list.forEach(ss -> {
				addDlorSaleStat(CopyBeanUtil.copyBean(ss, DlorSaleStatVO.class));
			});
		}
	}

	@Override
	public void addDlorSaleStat(DlorSaleStatVO vo) {
		DlorSaleManStat copyBean = CopyBeanUtil.copyBean(vo, DlorSaleManStat.class);
		copyBean.setCreateDate(new Date());
		copyBean.setCountDate(DateUtil.getDate(-1));
		mapper.insert(copyBean);
	}

	@Override
	public List<DlorSaleStatQueryResultVO> getSaleStatList(DlorSaleStatQueryVO vo) {
		List<DlorSaleStatQueryResultVO> dlorSaleStatList = mapper.getDlorSaleStatList(vo);
		return dlorSaleStatList;
	}
 

}
