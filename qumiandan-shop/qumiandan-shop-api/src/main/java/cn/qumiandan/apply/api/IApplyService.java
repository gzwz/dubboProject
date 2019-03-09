package cn.qumiandan.apply.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.apply.vo.ApplyVO;
import cn.qumiandan.apply.vo.QueryApplyVO;

public interface IApplyService {
	
	/**添加*/
	ApplyVO addApply(ApplyVO applyVO);
	
	/**查询单条*/
	ApplyVO queryApplyById(Long id);
	
	/**标记为已经处理*/
	ApplyVO dealApplyById(Long id, Byte status);
	
	/**查询分页*/
	PageInfo<ApplyVO> queryApply(QueryApplyVO queryApplyVO);
	
	
}
