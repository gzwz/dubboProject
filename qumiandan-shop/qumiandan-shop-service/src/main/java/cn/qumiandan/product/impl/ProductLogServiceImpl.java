package cn.qumiandan.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.product.api.IProductLogService;
import cn.qumiandan.product.entity.ProductLog;
import cn.qumiandan.product.mapper.ProductLogMapper;
import cn.qumiandan.product.vo.ProductLogVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
@Component
@Service(interfaceClass = IProductLogService.class)
public class ProductLogServiceImpl implements IProductLogService {

	@Autowired 
	private ProductLogMapper productLogMapper;
	/**
	 * 添加商品日志信息
	 * @param productLogVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int addProductLog(ProductLogVO productLogVO) throws QmdException{
		ProductLog productLog = 
		CopyBeanUtil.copyBean(productLogVO, ProductLog.class);
		int i = productLogMapper.insert(productLog);
		if(i<1) {
			throw new QmdException("添加失败");
		}
		else {
			return i;
		}
	}

	/**
	 * 根据主键删除商品日志信息
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int deleteProductLog(Long id) throws QmdException{
		int i = productLogMapper.deleteById(id);
		if(i<1) {
			throw new QmdException("删除失败");
		}
		else {
			return i;
		}
	}

	/**
	 * 根据主键查询商品日志信息
	 * @param id
	 * @return
	 */
	@Override
	public ProductLogVO getProductLogById(Long id) {
		ProductLog productLog = productLogMapper.selectById(id);
		ProductLogVO productLogVO = null;
		if(productLog!=null) {
			CopyBeanUtil.copyBean(productLog, ProductLogVO.class);
		}
		return productLogVO;
	}

	/**
	 * 
	 * 根据商品id查询日志信息
	 * @param productId
	 * @return
	 */
	@Override
	public List<ProductLogVO> getProductLogByProductId(Long productId) {
		List<ProductLog> list = productLogMapper.selectList(new QueryWrapper<ProductLog>().eq("product_id", productId));
		if(ObjectUtils.isEmpty(list)) {
			return null;
		}
		return CopyBeanUtil.copyList(list, ProductLogVO.class);
	}

}
