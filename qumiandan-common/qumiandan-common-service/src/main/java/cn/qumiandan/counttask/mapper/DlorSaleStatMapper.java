package cn.qumiandan.counttask.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.counttask.entity.DlorSaleManStat;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryResultVO;
import cn.qumiandan.counttask.vo.DlorSaleStatQueryVO;
@Mapper
public interface DlorSaleStatMapper extends BaseMapper<DlorSaleManStat>{
   
	/** 业务员查询跑批的历史记录*/
	List<DlorSaleStatQueryResultVO> getDlorSaleStatList(DlorSaleStatQueryVO vo);
	
	
}