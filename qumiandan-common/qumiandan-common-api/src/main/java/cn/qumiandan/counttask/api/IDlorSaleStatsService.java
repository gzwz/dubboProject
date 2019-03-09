package cn.qumiandan.counttask.api;

import java.util.List;

import cn.qumiandan.counttask.vo.DlorSaleStatQueryResultVO;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryVO;
import cn.qumiandan.counttask.vo.DlorSaleStatVO;

public interface IDlorSaleStatsService {
	
	/** 跑批查询调用接口*/
	void taskQuery() ;

	/** 添加跑批查询的结果**/
	void addDlorSaleStat(DlorSaleStatVO vo);
	
	/** 分页查询跑批的历史记录*/
	List<DlorSaleStatQueryResultVO> getSaleStatList(DlorSaleStatQueryVO vo);
	
	
}
