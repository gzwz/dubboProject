package cn.qumiandan.complain.api;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.complain.vo.ComplainVO;
import cn.qumiandan.complain.vo.QueryComplainVO;

public interface IComplainService {
	
	/**
	 * 添加投诉信息
	 * @param complainVO
	 * @return
	 */
	void addComplain(ComplainVO complainVO);
	
	/**
	 * 修改投诉信息
	 * @param complainVO
	 * @return
	 */
	void updateComplain(ComplainVO complainVO);
	
	/**
	 * 根据主键查看投诉
	 * @param complainVO
	 * @return
	 */
	ComplainVO getComplainById(Long id);
	
	/**
	 * 总后台查询所有投诉
	 * @param paramsVO
	 * @return
	 */
	PageInfo<ComplainVO> queryComplain(QueryComplainVO paramsVO);
}
