package cn.qumiandan.counttask.api;

import java.util.List;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.counttask.vo.AdminStatQueryVO;
import cn.qumiandan.counttask.vo.DLStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatVO;

public interface IPlatformStatsService {
	
	/** 跑批查询调用接口*/
	void taskQuery() ;

	/** 添加跑批查询的结果**/
	PlatformStatVO addPlatformStat(PlatformStatVO vo);
	
	/** 分页查询跑批的历史记录*/
	List<PlatformStatQueryVO> getPlatformStatPage(AdminStatQueryVO vo);
	
	/** 店铺分页查询跑批的历史记录*/
	List<PlatformStatQueryVO> getShopStatForShop(CommonParams params);
	
	/** 店铺分页查询跑批的历史记录*/
	List<PlatformStatQueryVO> getShopStatPageForDL(DLStatQueryVO vo);
	
}
