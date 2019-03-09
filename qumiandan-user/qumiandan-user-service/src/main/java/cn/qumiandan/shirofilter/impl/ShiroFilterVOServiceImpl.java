package cn.qumiandan.shirofilter.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.shirofilter.api.IShiroFilterService;
import cn.qumiandan.shirofilter.entity.ShiroFilter;
import cn.qumiandan.shirofilter.mapper.ShiroFilterMapper;
import cn.qumiandan.shirofilter.vo.ShiroFilterVO;
import cn.qumiandan.utils.CopyBeanUtil;
/**
 * @description：权限控制接口实现类
 * @author：lrj
 * @date: 2018/11/9 16:20
 */

@Component
@Service(interfaceClass = IShiroFilterService.class)
public class ShiroFilterVOServiceImpl implements IShiroFilterService {
	public static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterVOServiceImpl.class);
    
	@Autowired
	private ShiroFilterMapper shiroFilterMapper;
	/**
	 * 添加权限控制信息
	 * @param shiroFilterVO
	 * @return
	 */
	@Override
	public ShiroFilterVO addShiroFilter(ShiroFilterVO shiroFilterVO) throws Exception {
		ShiroFilter shiroFilter = CopyBeanUtil.copyBean(shiroFilterVO, ShiroFilter.class);
		int i = shiroFilterMapper.insert(shiroFilter);
		if(i < 1) {
			LOGGER.info("添加权限控制失败，addShiroFilter -> 数据库异常");
	        throw new QmdException("添加失败");
		}else {
			shiroFilterVO = CopyBeanUtil.copyBean(shiroFilter,ShiroFilterVO.class );
			return shiroFilterVO;
		}
	}

	/**
	 * 修改权限信息
	 * @param shiroFilterVO
	 * @return
	 */
	@Override
	public int updateShiroFilterVOById(ShiroFilterVO shiroFilterVO) throws Exception  {
		ShiroFilter shiroFilter= 
		CopyBeanUtil.copyBean(shiroFilterVO, ShiroFilter.class);
		int i= shiroFilterMapper.updateById(shiroFilter);
		if(i<1) {
			LOGGER.info("更新权限控制失败，updateShiroFilterVOById -> 数据库异常");
	        throw new QmdException("更新失败");
		}else {
			return i;
		}
	}

	/**
	 * 删除权限信息
	 * @param id
	 * @return
	 */
	@Override
	public int deleteShiroFilterVOById(Long id) throws Exception {
		int i= shiroFilterMapper.deleteById(id);
		if(i<1) {
			LOGGER.info("删除权限控制失败，deleteShiroFilterVOById -> 数据库异常");
	        throw new QmdException("删除失败");
		}else {
			return i;
		}
	}

	/**
	 *获取权限信息列表（按sort字段排序）
	 * @return
	 */
	@Override
	public List<ShiroFilterVO> getShiroFilterVOOrderBySort() {
		return shiroFilterMapper.getShiroFilterVOOrderBySort();
	}

	/**
	 * 根据Id查询权限信息
	 * @param Id
	 * @return
	 */
	@Override
	public ShiroFilterVO getShiroFilterVOById(Long id) {
		ShiroFilter shiroFilter= shiroFilterMapper.selectById(id);
		ShiroFilterVO shiroFilterVO = null;
		if(shiroFilter!=null) {
			shiroFilterVO = CopyBeanUtil.copyBean(shiroFilter, ShiroFilterVO.class);
		}else {
			return null;
		}
		shiroFilterVO = CopyBeanUtil.copyBean(shiroFilter, ShiroFilterVO.class);
		return shiroFilterVO;
	}

	@Override
	public PageInfo<ShiroFilterVO> getShiroFilterList(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ShiroFilterVO> shiroFilterVOOrderBySort = shiroFilterMapper.getShiroFilterVOOrderBySort();
		PageInfo<ShiroFilterVO> pageInfo = new PageInfo<>(shiroFilterVOOrderBySort);
		return pageInfo;
	}
}
