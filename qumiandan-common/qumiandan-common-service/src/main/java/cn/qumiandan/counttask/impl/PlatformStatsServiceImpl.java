package cn.qumiandan.counttask.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.backgrounddata.api.IPlatformStatsQueryService;
import cn.qumiandan.backgrounddata.vo.StatVO;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.counttask.api.IPlatformStatsService;
import cn.qumiandan.counttask.entity.PlatformStat;
import cn.qumiandan.counttask.mapper.PlatformStatMapper;
import cn.qumiandan.counttask.vo.AdminStatQueryVO;
import cn.qumiandan.counttask.vo.DLStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;

@Component
@Service(interfaceClass = IPlatformStatsService.class)
public class PlatformStatsServiceImpl implements IPlatformStatsService {
	
	@Autowired
	private PlatformStatMapper mapper;
	
	@Reference
	private IPlatformStatsQueryService statsService;
	
	@Override
	public PlatformStatVO addPlatformStat(PlatformStatVO vo) {
		AssertUtil.isNull(vo, "参入不能为空");
		PlatformStat entity = CopyBeanUtil.copyBean(vo, PlatformStat.class);
		int i = mapper.insert(entity);
		if (i != 1) {
			throw new QmdException("插入跑批统计出错");
		}
		vo.setId(entity.getId());
		return vo;
	}
	
	@Override
	public void taskQuery() {
		List<StatVO> list = statsService.getAllStats();
		if (list != null) {
			list.forEach(ss -> {
				addPlatformStat(CopyBeanUtil.copyBean(ss, PlatformStatVO.class));
			});
		}
		
		
	}

	@Override
	public List<PlatformStatQueryVO> getPlatformStatPage(AdminStatQueryVO vo) {
			//如果所有条件没传，则默认查询所有
			AssertUtil.isNull(vo.getStartTime(), "开始时间必传");
			AssertUtil.isNull(vo.getEndTime(), "结束时间必传");
			List<PlatformStatQueryVO> queryAll = mapper.queryAll(vo);
			return queryAll;
	}
	
	
	@Override
	public List<PlatformStatQueryVO> getShopStatForShop(CommonParams params) {
		AssertUtil.isNull(params, "PlatformStatsServiceImpl|getShopStatPageForShop|传入参数params为空");
		Calendar time = Calendar.getInstance();
		if (Objects.nonNull(params.getTime())) {
			time.setTime(params.getTime());
		} 
		time.set(Calendar.DATE, time.getMinimum(Calendar.DATE));
		params.setStartTime(time.getTime());
		time.set(Calendar.DATE, time.getMaximum(Calendar.DATE));
		params.setEndTime(time.getTime());
		return mapper.getShopStatPageForShop(params);
	}

	@Override
	public List<PlatformStatQueryVO> getShopStatPageForDL(DLStatQueryVO vo) {
		List<PlatformStatQueryVO> shopStatPageForDL = mapper.getShopStatPageForDL(vo);
		return shopStatPageForDL;
	}

}
