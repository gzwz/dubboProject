package cn.qumiandan.shopcategory.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.shopcategory.api.IShopCustomCategoryService;
import cn.qumiandan.shopcategory.entity.ShopCustomCategory;
import cn.qumiandan.shopcategory.mapper.ShopCustomCategoryMapper;
import cn.qumiandan.shopcategory.vo.ShopCustomCategoryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;

/**
 * @description：店铺自定义分类接口实现类
 * @author：lrj
 * @date: 2018/11/10 14:35
 */

@Component
@Service(interfaceClass = IShopCustomCategoryService.class)
public class ShopCustomCategoryServiceImpl implements IShopCustomCategoryService {
	@Autowired
	private ShopCustomCategoryMapper customCategoryMapper;

	/**
	 * 添加自定义分类
	 * 
	 * @param shopCustomCategoryVO
	 * @return int
	 */
	@Override
	public int addShopCustomCategory(ShopCustomCategoryVO shopCustomCategoryVO) throws QmdException {
		// 判断店铺分类名是否已存在
		ShopCustomCategory nameCustomCategory = customCategoryMapper
				.selectByNameAndShopId(shopCustomCategoryVO.getShopId(), shopCustomCategoryVO.getName());
		if (nameCustomCategory != null) {
			throw new QmdException("添加失败，店铺自定义分类名已存在");
		}
		shopCustomCategoryVO.setCreateDate(new Date());
		shopCustomCategoryVO.setUpdateDate(new Date());
		ShopCustomCategory customCategory = CopyBeanUtil.copyBean(shopCustomCategoryVO, ShopCustomCategory.class);
		int i = customCategoryMapper.insert(customCategory);
		if (i < 1) {
			throw new QmdException("添加失败");
		}
		return i;
	}

	/**
	 * 更新自定义分类
	 * 
	 * @param shopCustomCategoryVO
	 * @return int
	 */
	@Override
	public int updateShopCustomCategory(ShopCustomCategoryVO shopCustomCategoryVO) throws QmdException {
		ShopCustomCategory nameCustomCategory = customCategoryMapper
				.selectByNameAndShopId(shopCustomCategoryVO.getShopId(), shopCustomCategoryVO.getName());
		shopCustomCategoryVO.setUpdateDate(new Date());
		int i = 0;
		// 每个店铺的自定义分类名不能重复
		if (nameCustomCategory != null && nameCustomCategory.getId().equals(shopCustomCategoryVO.getId())) {
			throw new QmdException("更新失败，店铺自定义分类名已存在");
		}
		ShopCustomCategory category = CopyBeanUtil.copyBean(shopCustomCategoryVO, ShopCustomCategory.class);
		i = customCategoryMapper.updateById(category);
		if (i < 1) {
			throw new QmdException("更新失败");
		}
		return i;
	}

	/**
	 * 删除自定义分类
	 * 
	 * @param id
	 * @return int
	 */
	@Override
	public int deleteShopCustomCategoryById(Long id) throws QmdException {
		ShopCustomCategory shopCustomCategory = customCategoryMapper.selectById(id);
		if (null == shopCustomCategory) {
			throw new QmdException("删除失败，自定义分类信息不存在");
		}
		shopCustomCategory.setStatus(ToolUtil.intToByte(0));
		shopCustomCategory.setUpdateDate(new Date());
		int i = customCategoryMapper.updateById(shopCustomCategory);
		if (i < 1) {
			throw new QmdException("删除失败");
		}
		return i;
	}

	/**
	 * 根据店铺Id获取自定义分类列表（按排序数排序）
	 * 
	 * @param shopId
	 * @return
	 */
	@Override
	public List<ShopCustomCategoryVO> getCustomCategoryListByShopId(Long shopId) {
		return customCategoryMapper.getCustomCategoryByShopId(shopId);
	}

	/**
	 * 根据id查询自定义分类详情
	 */
	@Override
	public ShopCustomCategoryVO getShopCustomCategoryById(Long id) {
		ShopCustomCategory shopCustomCategory = customCategoryMapper.selectById(id);
		ShopCustomCategoryVO shopCustomCategoryVO = null;
		if (shopCustomCategory != null) {
			shopCustomCategoryVO = CopyBeanUtil.copyBean(shopCustomCategory, ShopCustomCategoryVO.class);
		}
		return shopCustomCategoryVO;
	}
}
