package cn.qumiandan.industry.api;

import java.util.List;

import cn.qumiandan.industry.vo.GetAllIndustryVO;
import cn.qumiandan.industry.vo.IndustryVO;

public interface IIndustryService {
	
	/**
	 * 添加行业信息
	 * @param industryVO
	 * @return
	 */
	public int addIndustry(IndustryVO industryVO);
	
	/**
	 * TODO 同步更新店铺费率
	 * 更新行业信息
	 * @param industryVO
	 * @return
	 */
	public int updateIndustry(IndustryVO industryVO);
	
	/**
	 * 查询所有行业信息
	 * @return
	 */
	public List<GetAllIndustryVO> getAllIndustry();
	
	/**
	 * 删除行业信息（逻辑删除）
	 * @param id
	 * @return
	 */
	public int deleteIndustry(Long id);
	
	/**
	 * 根据主键查询行业信息
	 * @param id
	 * @return
	 */
	IndustryVO getIndustryVOById(Long id);
	
	/**
	 * 根据父级Id查询行业信息
	 * @return
	 */
	List<IndustryVO> getIndustryByLevelAndParentId( Long parentId);
	
}
