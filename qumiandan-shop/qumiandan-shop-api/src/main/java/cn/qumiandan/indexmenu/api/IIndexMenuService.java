package cn.qumiandan.indexmenu.api;

import java.util.List;

import cn.qumiandan.indexmenu.vo.IndexMenuVO;

/**
 * 首页菜单管理
 * @author lrj
 *
 */
public interface IIndexMenuService {

	/**
	 * 添加首页菜单
	 * @param indexMenuVO
	 * @return
	 */
	int addIndexMenu(IndexMenuVO indexMenuVO );

	/**
	 * 修改首页菜单
	 * @param indexMenuVO
	 * @return
	 */
	int updateIndexMenu(IndexMenuVO indexMenuVO );
	
	/**
	 * 删除首页菜单
	 * @param id
	 * @return
	 */
	int deleteIndexMenu(Long id );
	
	/**
	 * 根据id查询首页菜单
	 * @param id
	 * @return
	 */
	IndexMenuVO getIndexMenuById(Long id);
	
	/**
	 * 查询所有首页菜单
	 * @return
	 */
	List<IndexMenuVO> getAllIndexMenu();
}
