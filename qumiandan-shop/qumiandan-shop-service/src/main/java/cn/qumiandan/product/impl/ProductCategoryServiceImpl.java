package cn.qumiandan.product.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductCategoryService;
import cn.qumiandan.product.entity.ProductCategory;
import cn.qumiandan.product.mapper.ProductCategoryMapper;
import cn.qumiandan.product.vo.ProductCategoryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;

/**
 * @description：商品分类接口实现类
 * @author：zhuyangyong
 * @date: 2018/11/26 18:33
 */
@Component
@Service(interfaceClass = IProductCategoryService.class)
public class ProductCategoryServiceImpl implements IProductCategoryService{

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 添加商品分类
     * @param productCategoryVO
     * @return
     * @throws QmdException 
     */
    @Override
    public int addProductCategory(ProductCategoryVO productCategoryVO){
    	ProductCategoryVO productCategoryVOByName = productCategoryMapper.getProductCategoryByName(productCategoryVO.getName());
    	//分类名不能重复
    	if(productCategoryVOByName!=null) {
    		throw new QmdException("该分类名已存在");
    	}
    	ProductCategory productCategory = 
    	CopyBeanUtil.copyBean(productCategoryVO, ProductCategory.class);
    	productCategory.setCreateDate(new Date());
    	//设置排序数
    	switch (productCategory.getLevel()) {
    	//设置一级分类排序数
		case 1:
			if(productCategory.getSort()==null) {
				Integer s=productCategoryMapper.getMaxlevelOneSort();
				productCategory.setSort(s!=null?s+1:1);	
			}
			productCategory.setParentId(0L);
			break;
			//设置二级分类排序数
		case 2:
			if(productCategory.getSort()==null) {
				Integer s=productCategoryMapper.getMaxlevelTwoSort();
				productCategory.setSort(s!=null?s+1:1);	
			}
			break;
		default:
			throw new QmdException("商品分类只存在一级、二级");
		}
        return productCategoryMapper.insert(productCategory);
    }

    /**
     * 修改商品分类
     * @param productCategoryVO
     * @return
     * @throws QmdException 
     */
    @Override
    public int updateProductCategory(ProductCategoryVO productCategoryVO) throws QmdException{
    	ProductCategoryVO productCategoryVOByName = productCategoryMapper.getProductCategoryByName(productCategoryVO.getName());
    	//修改后的分类名不能与其他记录的分类名重复
    	if(productCategoryVOByName!=null&&!productCategoryVO.getId().equals(productCategoryVOByName.getId())) {
    		throw new QmdException("该分类名已存在");
    	}
    	ProductCategory productCategory =
    	CopyBeanUtil.copyBean(productCategoryVO, ProductCategory.class);
    	productCategory.setUpdateDate(new Date());
        return productCategoryMapper.updateById(productCategory);
    }

    /**
     * 根据主键删除商品分类
     * @param id
     * @return
     */
	@Override
	public int deleteProductCategoryById(Long id) {
    	ProductCategory productCategory = new ProductCategory(); 
    	productCategory.setId(id);
    	productCategory.setStatus(ToolUtil.intToByte(0));
		return productCategoryMapper.updateById(productCategory);
	}

	/**
     * 查询一级分类
     * @return
     */
	@Override
	public List<ProductCategoryVO> getLevelOneProductCategory() {
		return productCategoryMapper.getLevelOneProductCategory();
	}

	/**
     * 根据一级分类查询二级分类
     * @param id
     * @return
     */
	@Override
	public List<ProductCategoryVO> getLevelTwoProductCategory(Long id) {
		return productCategoryMapper.getLevelTwoProductCategory(id);
	}

	/**
     * 根据主键查询分类详情
     * @param id
     * @return
     */
	@Override
	public ProductCategoryVO getProductCategoryById(Long id) {
		ProductCategory productCategory = productCategoryMapper.selectById(id);
		ProductCategoryVO productCategoryVO = null;
		if(productCategory!=null) {
			productCategoryVO = CopyBeanUtil.copyBean(productCategory,ProductCategoryVO.class);
		}
		return productCategoryVO;
	}
}
