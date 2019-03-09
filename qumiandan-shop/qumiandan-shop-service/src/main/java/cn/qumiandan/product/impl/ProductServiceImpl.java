package cn.qumiandan.product.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Sets;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.product.api.IProductService;
import cn.qumiandan.product.entity.Product;
import cn.qumiandan.product.entity.ProductAlbum;
import cn.qumiandan.product.entity.ProductCustomCategory;
import cn.qumiandan.product.entity.ProductExtend;
import cn.qumiandan.product.enums.ProoductStatusEnum;
import cn.qumiandan.product.mapper.ProductAlbumMapper;
import cn.qumiandan.product.mapper.ProductCustomCategoryMapper;
import cn.qumiandan.product.mapper.ProductExtendMapper;
import cn.qumiandan.product.mapper.ProductMapper;
import cn.qumiandan.product.vo.CustomCategoryVO;
import cn.qumiandan.product.vo.ProductAlbumVO;
import cn.qumiandan.product.vo.ProductAndShopVO;
import cn.qumiandan.product.vo.ProductBasicInfoVO;
import cn.qumiandan.product.vo.ProductBasicVO;
import cn.qumiandan.product.vo.ProductDetailVO;
import cn.qumiandan.product.vo.ProductUpdateVO;
import cn.qumiandan.product.vo.ProductVO;
import cn.qumiandan.product.vo.ShopProductListVO;
import cn.qumiandan.product.vo.ShopProductQueryVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @description：商品接口实现类
 * @author：zhuyangyong
 * @date: 2018/11/8 10:42
 */
@Slf4j
@Component
@Service(interfaceClass = IProductService.class)
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper produtMapper;
	@Autowired
    private ProductExtendMapper productExtendMapper;
    @Autowired
    private ProductAlbumMapper productAlbumMapper;
	@Autowired
	private ProductCustomCategoryMapper productCustomCategoryMapper;
	/*@Autowired
	private ProductAndShopMapper productAndShopMapper;*/

	/**
	 * 添加商品信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addProduct(ProductVO productVO) throws QmdException {
		//保存商品基础信息
		log.info("开始保存商品基础信息");
		productVO.setCreateDate(new Date());
		Product product = new Product();
		BeanUtils.copyProperties(productVO, product);
		product.setUpdateId(productVO.getCreateId());
		product.setUpdateDate(new Date());
		product.setStatus(ToolUtil.intToByte(1));
		int produtRet = produtMapper.insert(product);
		productVO.setProductId(product.getId());
		log.info("保存商品基础信息成功，数量：" + produtRet);

		//保存商品扩展信息
		log.info("开始保存商品扩展信息");
		ProductExtend productExtend = new ProductExtend();
		BeanUtils.copyProperties(productVO, productExtend);
		int produtExtendRet = productExtendMapper.insertSelective(productExtend);
		log.info("保存商品扩展信息成功，数量："+produtExtendRet);

		//保存商品-店铺关联信息
//		log.info("开始保存商品-店铺关联信息");
//		ProductAndShop productAndShop = new ProductAndShop();
//		BeanUtils.copyProperties(productVO, productAndShop);
//		int productShopRet = productAndShopMapper.insertSelective(productAndShop);
//		log.info("保存商品-店铺关联信息成功，数量："+produtExtendRet);

		//保存商品-自定义分类信息
		if(productVO.getCustomCategoryId()!=null) {
			log.info("开始保存商品-自定义分类信息");
			ProductCustomCategory productCustomCategory = new ProductCustomCategory();
			productCustomCategory.setProductId(product.getId());
			productCustomCategory.setCategoryId(productVO.getCustomCategoryId());
			int productCustomCategoryRet =  productCustomCategoryMapper.insertSelective(productCustomCategory);
			log.info("保存商品-自定义分类信息成功，数量："+productCustomCategoryRet);
		}

		//保存商品图片信息
		int productAlbumRet = 0;
		if (productVO.getProductPictureVOList() != null && productVO.getProductPictureVOList().size() > 0) {
			ProductAlbum productAlbum = null;
			for (ProductAlbumVO pic : productVO.getProductPictureVOList()) {
				if (Objects.nonNull(pic)) {
					if (pic.getImageType().equals(ToolUtil.intToByte(1))) {
						// 更新商品主图
						product.setMainImage(pic.getImageUrl());
						produtMapper.updateById(product);//.updateByPrimaryKeySelective(product);
					}
					productAlbum = CopyBeanUtil.copyBean(pic, ProductAlbum.class);
					productAlbum.setProductId(product.getId());
					productAlbum.setCreateDate(new Date());
					productAlbum.setCreateId(productVO.getCreateId());
					productAlbum.setStatus(StatusEnum.normal.getCode());
					productAlbumRet += productAlbumMapper.insert(productAlbum);
				}
			}
			log.info("保存商品图片信息成功，数量：" + productAlbumRet);
		}
		
		if (produtRet > 0 && produtExtendRet > 0  && productAlbumRet > 0) {
			return produtRet;
		} else {
			throw new QmdException("添加失败");
		}
	}

	/**
	 * 更新商品信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateProductById(ProductUpdateVO productUpdateVO) throws QmdException {
		productUpdateVO.setProductId(productUpdateVO.getId());
		//更新商品基础信息
		productUpdateVO.setUpdateDate(new Date());
		Product product = produtMapper.selectById(productUpdateVO.getId());//.selectByPrimaryKey(productUpdateVO.getId());
		if(product != null){
			BeanUtils.copyProperties(productUpdateVO, product);
			product.setStatus(ToolUtil.intToByte(1));
			int proRet = produtMapper.updateById(product);//.updateByPrimaryKeySelective(product);
			if (proRet != 1) {
				log.info("");
				throw new QmdException("更新商品信息失败");
			}
			
			
			
			//更新商品扩展信息
			ProductExtend productExtend = productExtendMapper.selectByProductId(productUpdateVO.getProductId());
			if(productExtend != null){
				BeanUtils.copyProperties(productUpdateVO, productExtend);
				int proExtRet = productExtendMapper.updateByProductIdSelective(productExtend);
				if (proExtRet != 1) {
					throw new QmdException("更新商品信息失败");
				}
			}

			//更新商品-自定义分类信息
			ProductCustomCategory productCustomCategory = productCustomCategoryMapper.selectByProductId(productUpdateVO.getProductId());
			if(productUpdateVO.getCustomCategoryId() != null && productCustomCategory != null){
				productCustomCategory.setCategoryId(productUpdateVO.getCustomCategoryId());
				productCustomCategoryMapper.updateByPrimaryKeySelective(productCustomCategory);
			}

			//更新商品图片信息
			if(!CollectionUtils.isEmpty(productUpdateVO.getProductPictureVOList())) {
				ProductAlbum productAlbum = null;
				Set<Byte> deletedType = Sets.newHashSetWithExpectedSize(10);
				for (ProductAlbumVO pic : productUpdateVO.getProductPictureVOList()) {
					if (!deletedType.contains(pic.getImageType())) {
						//删除相关类型的商品图片
						productAlbumMapper.deleteAlbumsByProductIdAndType(productUpdateVO.getProductId(), pic.getImageType());
					}
					if (pic.getImageType().equals(ToolUtil.intToByte(1))) {
						// 更新商品主图
						product.setMainImage(pic.getImageUrl());
						produtMapper.updateById(product);//.updateByPrimaryKeySelective(product);
					} 
					// 添加新的图片
					productAlbum = CopyBeanUtil.copyBean(pic, ProductAlbum.class);
					productAlbum.setProductId(product.getId());
					productAlbum.setCreateId(productUpdateVO.getUpdateId());
					productAlbum.setCreateDate(new Date());
					productAlbum.setStatus(StatusEnum.normal.getCode());
					productAlbumMapper.insert(productAlbum);
					deletedType.add(pic.getImageType());
				}
			}
		}
	}

	/**
	 * 商品上下架
	 * @param productId
	 * @return
	 * @throws QmdException
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int setIsShelf(Long productId,Long shopId,Byte status) {
		Product product = produtMapper.selectById(productId);
				//.selectByPrimaryKey(productId);
		if(product == null ||! product.getShopId().equals(shopId) ) {
			throw new QmdException("商品不存在");
		}
		int i = 0;
		switch (status) {
		//处于下架状态的商品才可上架
		case 1:
			if(product.getStatus()!= 4){
				throw new QmdException("该商品无法上架");
			}
			i = produtMapper.setIsShelf(productId, ToolUtil.intToByte(3));
			break;
		case 0:
			//处于上架架状态的商品才可下架
			if(product.getStatus()!= 3){
				throw new QmdException("该商品无法下架");
			}
			i = produtMapper.setIsShelf(productId, ToolUtil.intToByte(4));
			break;
		default:
			break;
		}
		if(i<1) {
			throw new QmdException("商品上下架失败");
		}
		return i;
	}

	/**
	 * 根据商品Id获取商品详情
	 * @param id
	 * @return
	 */
	@Override
	public ProductDetailVO getProductDetailById(Long id) {
		//商品信息
		ProductDetailVO productDetailVO = produtMapper.getProductDetailById(id);

		//商品图片信息
		List<ProductAlbumVO> productAlbumVOList = productAlbumMapper.selectByProductId(id);
		if(!CollectionUtils.isEmpty(productAlbumVOList)){
			productDetailVO.setProductPictureVOList(productAlbumVOList);
		}
		return productDetailVO;
	}

	/**
     * 根据店铺id获取商品分页列表
     */
    @Override
    public PageInfo<ShopProductListVO> getProductPageListByShopId(ShopProductQueryVO shopProductQueryVO,int pageNum,int pageSize){
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //条件查询
		List<ShopProductListVO> shopProductListVOList = produtMapper.getProductPageListByShopId(shopProductQueryVO);

        //封装分页结果
        PageInfo<ShopProductListVO> pageInfo = new PageInfo<>(shopProductListVOList);
        pageInfo.setTotal(pageInfo.getTotal());

        //返回分页对象
        return pageInfo;
    }

    @Override
	public List<Map<String,Object>> getCustomProductListByShopId(Long shopId){
    	//商品列表
		List<ProductBasicInfoVO> productBasicInfoList = produtMapper.getCustomProductListByShopId(shopId);
		//自定义分类id列表
		List<CustomCategoryVO> cusCategoryList = produtMapper.getCustomCategoryListByShopId(shopId);
		List<Map<String,Object>> retList = new ArrayList<>();
		List<Long> idList = new ArrayList<>();
		if(cusCategoryList.size() > 0){
			for(CustomCategoryVO cusCategoryVO : cusCategoryList){
				if (Objects.nonNull(cusCategoryVO)) {
					if(!idList.contains(cusCategoryVO.getCustomCategoryId())){
						idList.add(cusCategoryVO.getCustomCategoryId());
						Map<String,Object> firstMap = new LinkedHashMap<>();
						List<ProductBasicInfoVO> secondList = new ArrayList<>();
						firstMap.put("customCategoryId",cusCategoryVO.getCustomCategoryId());
						firstMap.put("customCategoryName",cusCategoryVO.getCustomCategoryName());
						if(productBasicInfoList.size() > 0){
							for(ProductBasicInfoVO basicInfoVO : productBasicInfoList){
								if(basicInfoVO.getCustomCategoryId() != null &&
										basicInfoVO.getCustomCategoryId().equals(cusCategoryVO.getCustomCategoryId())){
									secondList.add(basicInfoVO);
								}
							}
						}
						firstMap.put("children",secondList);
						retList.add(firstMap);
					}
				}
			}
		}
    	return retList;
	}

	@Override
	public ProductBasicVO getProductBasicById(Long id){
		
		Product product = produtMapper.selectById(id);//.selectByPrimaryKey(id);
		if(product == null){
			return null ;
		}
		ProductBasicVO productBasicVO = CopyBeanUtil.copyBean(product,ProductBasicVO.class);
		return productBasicVO;
	}

    /**
     * 商品审核
     * @param id
     * @return
     */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int auditProduct(Long id,Byte status) {
		Product product = produtMapper.selectById(id);//.selectByPrimaryKey(id);
		if(product == null) {
			throw new QmdException("该商品不存在");
		}
		if(!product.getStatus().equals(ToolUtil.intToByte(1))) {
			throw new QmdException("该商品不处于待审核状态");
		}
		switch (status) {
		case 0:
			product.setStatus(ToolUtil.intToByte(2));
			break;
		case 1:
			product.setStatus(ToolUtil.intToByte(3));
			break;
		default:
			throw new QmdException("商品状态设置错误");
		}
		return produtMapper.updateById(product);//.updateByPrimaryKeySelective(product);
	}
	
	
    /**
     * 根据商品id集合查询商品详情
     * @param idSet
     * @return
     */
	@Override
	public List<ProductDetailVO> getProductByProductIdSet(Set<Long> idSet) {
		List<ProductDetailVO> productDetailVOList = produtMapper.getProductByProductIdSet(idSet);
		return productDetailVOList;
	}

	/**
	 * 总后台查询商品
	 */
	@Override
	public PageInfo<ProductAndShopVO> queryProduct(ShopProductQueryVO productQueryVO) {
		PageHelper.startPage(productQueryVO.getPageNum(), productQueryVO.getPageSize());
		List<ProductAndShopVO> productAndShopVOs = produtMapper.queryProduct(productQueryVO);
		PageInfo<ProductAndShopVO> pageInfo = new PageInfo<>(productAndShopVOs);
		return pageInfo;
	}

	/**
	 * 删除商品
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public void deleteProduct(Long productId ) {
		Product product = produtMapper.selectById(productId);
		if(product == null) {
			throw new QmdException("商品不存在");
		}
		product.setStatus(ProoductStatusEnum.Deleted.getCode());
		int i = produtMapper.updateById(product);
		if(i != 1) {
			throw new QmdException("删除失败");
		}
	}
}
