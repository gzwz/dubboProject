package cn.qumiandan.counttask.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.qumiandan.common.request.CommonParams;
import cn.qumiandan.counttask.entity.PlatformStat;
import cn.qumiandan.counttask.vo.AdminStatQueryVO;
import cn.qumiandan.counttask.vo.DLStatQueryVO;
import cn.qumiandan.counttask.vo.PlatformStatQueryVO;
@Mapper
public interface PlatformStatMapper extends BaseMapper<PlatformStat>{
   
	List<PlatformStatQueryVO> queryAll(AdminStatQueryVO vo);
	
	List<PlatformStatQueryVO> queryAll();
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<PlatformStatQueryVO> getShopStatPageForShop(@Param("params")CommonParams params);
	
	/** 店铺分页查询跑批的历史记录*/
	List<PlatformStatQueryVO> getShopStatPageForDL(@Param("vo")DLStatQueryVO vo);
}