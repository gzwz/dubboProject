package cn.qumiandan.indexmenu.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.indexmenu.api.IIndexMenuService;
import cn.qumiandan.indexmenu.entity.IndexMenu;
import cn.qumiandan.indexmenu.mapper.IndexMenuMapper;
import cn.qumiandan.indexmenu.vo.IndexMenuVO;
import cn.qumiandan.utils.CopyBeanUtil;

/**
 * 首页菜单管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IIndexMenuService.class)
public class IndexMenuServiceImpl implements IIndexMenuService{

	@Autowired
	private IndexMenuMapper indexMenuMapper;
	/**
	 * 添加首页菜单
	 * @param indexMenuVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int addIndexMenu(IndexMenuVO indexMenuVO) {
		indexMenuVO.setCreateDate(new Date());
		IndexMenu indexMenu = indexMenuMapper.getIndexMenuByName(indexMenuVO.getName());
		if(indexMenu != null) {
			throw new QmdException("菜单名不能重复");
		}
		//设置排序数
		if(indexMenuVO.getSort()==null) {
			Integer i = indexMenuMapper.getMaxSort();
			indexMenuVO.setSort((i!=null?i:0)+1);
		}
		indexMenu = CopyBeanUtil.copyBean(indexMenuVO, IndexMenu.class);
		indexMenu.setStatus(StatusEnum.normal.getCode());
		return indexMenuMapper.insert(indexMenu);
	}

	/**
	 * 修改首页菜单
	 * @param indexMenuVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int updateIndexMenu(IndexMenuVO indexMenuVO) {
		IndexMenu indexMenu  = indexMenuMapper.getIndexMenuByName(indexMenuVO.getName());
		if(indexMenu!=null&&indexMenuVO.getId()!=indexMenu.getId()) {
			throw new QmdException("菜单名不能重复");
		}
		indexMenu = CopyBeanUtil.copyBean(indexMenuVO, IndexMenu.class);
		indexMenu.setUpdateDate(new Date());
		return indexMenuMapper.updateById(indexMenu);
	}

	/**
	 * 删除首页菜单
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int deleteIndexMenu(Long id) {
		IndexMenu indexMenu = new IndexMenu();
		indexMenu.setId(id);
		indexMenu.setStatus(StatusEnum.deleted.getCode());
		return indexMenuMapper.updateById(indexMenu);
	}

	/**
	 * 根据id查询首页菜单
	 * @param id
	 * @return
	 */
	@Override
	public IndexMenuVO getIndexMenuById(Long id) {
		IndexMenu indexMenu = indexMenuMapper.selectById(id);
		if(indexMenu == null) {
			return null;
		}
		IndexMenuVO indexMenuVO = CopyBeanUtil.copyBean(indexMenu, IndexMenuVO.class);
		return indexMenuVO;
	}

	/**
	 * 查询所有首页菜单
	 * @return
	 */
	@Override
	public List<IndexMenuVO> getAllIndexMenu() {
		List<IndexMenu> indexMenuList = indexMenuMapper.getAllIndexMenu();
		if(indexMenuList.size()<=0) {
			return null;
		}
		List<IndexMenuVO> indexMenuVOList = CopyBeanUtil.copyList(indexMenuList, IndexMenuVO.class);
		return indexMenuVOList;
	}

}
