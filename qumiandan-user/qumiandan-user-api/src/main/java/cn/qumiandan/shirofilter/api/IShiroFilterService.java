package cn.qumiandan.shirofilter.api;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.qumiandan.shirofilter.vo.ShiroFilterVO;

public interface IShiroFilterService {
	/**
	 * 添加权限信息
	 * @param shiroFilterVO
	 * @return
	 */
	ShiroFilterVO addShiroFilter(ShiroFilterVO shiroFilterVO) throws Exception ;
	
	/**
	 * 修改权限信息
	 * @param shiroFilterVO
	 * @return
	 */
	int updateShiroFilterVOById(ShiroFilterVO shiroFilterVO) throws Exception ;
	
	/**
	 * 删除权限信息
	 * @param shiroFilterVO
	 * @return
	 */
	int deleteShiroFilterVOById(Long id) throws Exception ;
	
	/**
	 *获取权限信息列表（按sort字段排序）
	 * @return
	 */
	List<ShiroFilterVO> getShiroFilterVOOrderBySort();
	
	/**
	 * 根据Id查询权限信息
	 * @param Id
	 * @return
	 */
	ShiroFilterVO getShiroFilterVOById(Long Id);
	
	
	/**
	 * 获取用户分页列表
	 * @return
	 */
	PageInfo<ShiroFilterVO> getShiroFilterList(int pageNum, int pageSize);
}
