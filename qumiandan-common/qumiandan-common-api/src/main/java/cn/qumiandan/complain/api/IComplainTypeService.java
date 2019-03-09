package cn.qumiandan.complain.api;

import java.util.List;

import cn.qumiandan.complain.vo.ComplainTypeVO;

public interface IComplainTypeService {
	
	/**
	 * 查询所有投诉类型
	 * @return
	 */
	public List<ComplainTypeVO> getAllComplain();


}
