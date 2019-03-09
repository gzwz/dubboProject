package cn.qumiandan.product.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductBrandService;
import cn.qumiandan.product.entity.ProductBrand;
import cn.qumiandan.product.mapper.ProductBrandMapper;
import cn.qumiandan.product.vo.ProductBrandVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;

@Component
@Service(interfaceClass = IProductBrandService.class)
public class ProductBrandServiceImpl implements IProductBrandService {

	@Autowired
	private ProductBrandMapper productBrandMapper;

	/**
	 * 添加商品品牌
	 * 
	 * @param productBrandVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int addProductBrand(ProductBrandVO productBrandVO) throws QmdException {
		ProductBrand productBrandByName = productBrandMapper.selectOne(new QueryWrapper<ProductBrand>().eq("name", productBrandVO.getName()));
		// 品牌名不能重复
		if (productBrandByName != null) {
			throw new QmdException("该品牌名称已存在");
		}
		ProductBrand productBrand = CopyBeanUtil.copyBean(productBrandVO, ProductBrand.class);
		if (productBrand.getSort() == null) {
			Integer sort = productBrandMapper.getMaxSort();
			productBrand.setSort((sort != null ? sort : 0) + 1);
		}
		productBrand.setCreateDate(new Date());
		productBrand.setStatus(ToolUtil.intToByte(1));
		return productBrandMapper.insert(productBrand);
	}

	/**
	 * 更新商品品牌
	 * 
	 * @param productBrandVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int updateProductBrand(ProductBrandVO productBrandVO) throws QmdException {
		ProductBrand productBrandByName = productBrandMapper.selectOne(new QueryWrapper<ProductBrand>().eq("name", productBrandVO.getName()));
		ProductBrand productBrand = CopyBeanUtil.copyBean(productBrandVO, ProductBrand.class);
		productBrand.setUpdateDate(new Date());
		// 品牌名不能重复
		if (productBrandByName != null && !productBrandByName.getId().equals(productBrandVO.getId())) {
			throw new QmdException("该品牌名称已存在");
		}
		return productBrandMapper.updateById(productBrand);
	}

	/**
	 * 根据主键删除商品品牌（逻辑删除）
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int deleteProductBrandById(Long id) throws QmdException {
		ProductBrand productBrand = new ProductBrand();
		productBrand.setId(id);
		productBrand.setStatus(ToolUtil.intToByte(0));
		int i = productBrandMapper.updateById(productBrand);
		if (i < 0) {
			throw new QmdException("删除失败");
		} else {
			return i;
		}
	}

	/**
	 * 根据主键查询商品品牌信息
	 * 
	 * @return
	 */
	@Override
	public ProductBrandVO getProductBrandById(Long id) {
		ProductBrand productBrand = productBrandMapper.selectById(id);
		ProductBrandVO productBrandVO = null;
		if (productBrand != null) {
			productBrandVO = CopyBeanUtil.copyBean(productBrand, ProductBrandVO.class);
		}
		return productBrandVO;
	}

	/**
	 * 根据商品id查询商品品牌信息
	 * 
	 * @return
	 */
	@Override
	public List<ProductBrandVO> getAllProductBrand() {
		return productBrandMapper.selectAllProductBrand();
	}

}
